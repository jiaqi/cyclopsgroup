package org.cyclopsgroup.cym2.awss3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.maven.wagon.ConnectionException;
import org.apache.maven.wagon.InputData;
import org.apache.maven.wagon.OutputData;
import org.apache.maven.wagon.ResourceDoesNotExistException;
import org.apache.maven.wagon.StreamWagon;
import org.apache.maven.wagon.TransferFailedException;
import org.apache.maven.wagon.authentication.AuthenticationException;
import org.apache.maven.wagon.authentication.AuthenticationInfo;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.apache.maven.wagon.proxy.ProxyInfo;
import org.apache.maven.wagon.resource.Resource;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * Amazon S3 wagon provider implementation
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class S3Wagon
    extends StreamWagon
{
    private final MimetypesFileTypeMap typeMap = new MimetypesFileTypeMap();

    private String bucketName;

    private String keyPrefix;

    private AmazonS3 s3;

    private final Properties mimeTypes;

    /**
     * @throws IOException Allows IO errors
     */
    public S3Wagon()
        throws IOException
    {
        Properties props = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream( "mimetypes.properties" );
        try
        {
            props.load( in );
            this.mimeTypes = props;
        }
        finally
        {
            IOUtils.closeQuietly( in );
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void closeConnection()
        throws ConnectionException
    {
    }

    private void doPutFromStream( InputStream in, File inFile, String destination, long contentLength, long lastModified )
    {
        Resource resource = new Resource( destination );
        firePutInitiated( resource, inFile );
        String key = keyPrefix + destination;
        ObjectMetadata meta = new ObjectMetadata();
        if ( contentLength != -1 )
        {
            meta.setContentLength( contentLength );
        }
        meta.setLastModified( new Date( lastModified ) );

        int lastDot = destination.lastIndexOf( '.' );
        String mimeType = null;
        if ( lastDot != -1 )
        {
            String ext = destination.substring( lastDot + 1, destination.length() );
            mimeType = mimeTypes.getProperty( ext );
        }
        if ( mimeType == null )
        {
            mimeType = typeMap.getContentType( destination );
        }
        else
        {
            fireTransferDebug( "Mime type of " + destination + " is " + mimeType + " according to build-in types" );
        }
        if ( mimeType != null )
        {
            meta.setContentType( mimeType );
        }

        try
        {
            fireTransferDebug( "Uploading file " + inFile + " to  key " + key + " in S3 bucket " + bucketName );
            firePutStarted( resource, inFile );
            s3.putObject( bucketName, key, in, meta );
            s3.setObjectAcl( bucketName, key, CannedAccessControlList.PublicRead );
            firePutCompleted( resource, inFile );
        }
        finally
        {
            IOUtils.closeQuietly( in );
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void fillInputData( InputData in )
        throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException
    {
        fireTransferDebug( "Filling input data" );
        String key = keyPrefix + in.getResource().getName();
        S3Object object;
        try
        {
            object = s3.getObject( bucketName, key );
        }
        catch ( AmazonServiceException e )
        {
            if ( e.getStatusCode() == 404 )
            {
                throw new ResourceDoesNotExistException( "Key " + key + " does not exist in S3 bucket " + bucketName );
            }
            throw new TransferFailedException( "Can't get object " + key + " from S4 bucket " + bucketName, e );
        }
        in.getResource().setContentLength( object.getObjectMetadata().getContentLength() );
        in.getResource().setLastModified( object.getObjectMetadata().getLastModified().getTime() );
        in.setInputStream( object.getObjectContent() );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void fillOutputData( OutputData out )
        throws TransferFailedException
    {
        throw new UnsupportedOperationException( "This call is not supported" );
    }

    /**
     * @inheritDoc
     */
    public void get( String resourceName, File destination )
        throws ResourceDoesNotExistException, TransferFailedException
    {
        Resource resource = new Resource( resourceName );
        fireGetInitiated( resource, destination );
        String key = keyPrefix + resourceName;
        try
        {
            fireGetStarted( resource, destination );
            s3.getObject( new GetObjectRequest( bucketName, key ), destination );
            fireGetCompleted( resource, destination );
        }
        catch ( AmazonServiceException e )
        {
            if ( e.getStatusCode() == 404 )
            {
                throw new ResourceDoesNotExistException( "Key " + key + " does not exist in bucket " + bucketName, e );
            }
            throw new TransferFailedException( "Getting metadata of key " + key + "failed", e );
        }
    }

    /**
     * @inheritDoc
     */
    @SuppressWarnings( "rawtypes" )
    public List getFileList( String destinationDirectory )
        throws TransferFailedException, ResourceDoesNotExistException
    {
        String path = keyPrefix + destinationDirectory;
        if ( !path.endsWith( "/" ) )
        {
            path += "/";
        }
        fireSessionDebug( "Listing objects with prefix " + path + " under bucket " + bucketName );
        ObjectListing result =
            s3.listObjects( new ListObjectsRequest().withBucketName( bucketName ).withPrefix( path ).withDelimiter( "/" ) );
        if ( result.getObjectSummaries().isEmpty() )
        {
            throw new ResourceDoesNotExistException( "No keys exist with prefix " + path );
        }
        Set<String> results = new HashSet<String>();
        for ( S3ObjectSummary summary : result.getObjectSummaries() )
        {
            String name = StringUtils.removeStart( summary.getKey(), path );
            if ( name.indexOf( '/' ) == -1 )
            {
                results.add( name );
            }
            else
            {
                results.add( name.substring( 0, name.indexOf( '/' ) ) );
            }
        }
        fireSessionDebug( "Returning result " + results );
        return new ArrayList<String>( results );
    }

    /**
     * @inheritDoc
     */
    public boolean getIfNewer( String resourceName, File destination, long timestamp )
        throws ResourceDoesNotExistException, TransferFailedException
    {
        ObjectMetadata meta = getRequiredMetadata( resourceName );
        if ( meta == null )
        {
            return false;
        }
        get( resourceName, destination );
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean getIfNewerToStream( String resourceName, OutputStream out, long timestamp )
        throws ResourceDoesNotExistException, TransferFailedException
    {
        ObjectMetadata meta = getRequiredMetadata( resourceName );
        if ( meta == null )
        {
            return false;
        }
        Resource resource = new Resource( resourceName );
        fireGetInitiated( resource, null );
        InputStream in = s3.getObject( bucketName, keyPrefix ).getObjectContent();
        try
        {
            fireGetStarted( resource, null );
            IOUtils.copy( in, out );
            out.flush();
            out.close();
            fireGetCompleted( resource, null );
            return true;
        }
        catch ( IOException e )
        {
            throw new TransferFailedException( "Stream copy failed", e );
        }
        finally
        {
            IOUtils.closeQuietly( in );
        }
    }

    private ObjectMetadata getRequiredMetadata( String resourceName )
        throws ResourceDoesNotExistException, TransferFailedException
    {
        String key = keyPrefix + resourceName;
        try
        {
            return s3.getObjectMetadata( bucketName, key );
        }
        catch ( AmazonServiceException e )
        {
            if ( e.getStatusCode() == 404 )
            {
                throw new ResourceDoesNotExistException( "Key " + key + " does not exist in bucket " + bucketName, e );
            }
            throw new TransferFailedException( "Getting metadata of key " + key + "failed", e );
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void openConnectionInternal()
        throws ConnectionException, AuthenticationException
    {
        AuthenticationInfo auth = getAuthenticationInfo();
        if ( auth == null )
        {
            throw new AuthenticationException( "S3 access requires authentication information" );
        }
        ProxyInfo proxy = getProxyInfo();
        fireSessionDebug( "Setting up AWS S3 client with source "
            + ToStringBuilder.reflectionToString( getRepository() ) + ", authentication information and proxy "
            + ToStringBuilder.reflectionToString( proxy ) );
        AWSCredentials credentials = new BasicAWSCredentials( auth.getUserName(), auth.getPassword() );
        ClientConfiguration config = new ClientConfiguration();
        config.setConnectionTimeout( getTimeout() );
        config.setSocketTimeout( getTimeout() );
        fireSessionDebug( "Connect timeout and socket timeout is set to " + getTimeout() + " ms" );

        if ( proxy != null )
        {
            config.setProxyDomain( proxy.getNtlmDomain() );
            config.setProxyHost( proxy.getHost() );
            config.setProxyPassword( proxy.getPassword() );
            config.setProxyPort( proxy.getPort() );
            config.setProxyUsername( proxy.getUserName() );
            config.setProxyWorkstation( proxy.getNtlmHost() );
        }
        fireSessionDebug( "AWS Client config is " + ToStringBuilder.reflectionToString( config ) );

        s3 = new AmazonS3Client( credentials, config );
        bucketName = getRepository().getHost();
        fireSessionDebug( "Bucket name is " + bucketName );

        String prefix = StringUtils.trimToEmpty( getRepository().getBasedir() );
        if ( !prefix.endsWith( "/" ) )
        {
            prefix = prefix + "/";
        }
        prefix = StringUtils.removeStart( prefix, "/" );
        keyPrefix = prefix;
        fireSessionDebug( "Key prefix " + keyPrefix );
    }

    /**
     * @inheritDoc
     */
    public void put( File source, String destination )
        throws TransferFailedException, ResourceDoesNotExistException
    {
        try
        {
            doPutFromStream( new FileInputStream( source ), source, destination, source.length(), source.lastModified() );
        }
        catch ( FileNotFoundException e )
        {
            throw new ResourceDoesNotExistException( "Source file " + source + " does not exist", e );
        }
    }

    /**
     * @inheritDoc
     */
    public void putDirectory( File sourceDirectory, String destinationDirectory )
        throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException
    {
        if ( destinationDirectory.equals( "." ) )
        {
            destinationDirectory = "";
        }
        fireTransferDebug( "Putting " + sourceDirectory + " to " + destinationDirectory + " which is noop" );
        for ( File file : sourceDirectory.listFiles() )
        {
            String dest =
                StringUtils.isBlank( destinationDirectory ) ? file.getName()
                                : ( destinationDirectory + "/" + file.getName() );
            fireTransferDebug( "Putting child element " + file + " to " + dest );
            if ( file.isDirectory() )
            {
                putDirectory( file, dest );
            }
            else
            {
                put( file, dest );
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void putFromStream( InputStream in, String destination )
        throws TransferFailedException, ResourceDoesNotExistException
    {
        doPutFromStream( in, null, destination, -1, System.currentTimeMillis() );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void putFromStream( InputStream in, String destination, long contentLength, long lastModified )
        throws TransferFailedException, ResourceDoesNotExistException
    {
        doPutFromStream( in, null, destination, contentLength, lastModified );
    }

    /**
     * @inheritDoc
     */
    public boolean resourceExists( String resourceName )
        throws TransferFailedException, AuthorizationException
    {
        String key = keyPrefix + resourceName;
        try
        {
            s3.getObjectMetadata( bucketName, key );
            return true;
        }
        catch ( AmazonServiceException e )
        {
            if ( e.getStatusCode() == 404 )
            {
                return false;
            }
            throw new TransferFailedException( "Can't verify if resource key " + key + " exist or not", e );
        }
    }

    /**
     * @inheritDoc
     */
    public boolean supportsDirectoryCopy()
    {
        return true;
    }
}
