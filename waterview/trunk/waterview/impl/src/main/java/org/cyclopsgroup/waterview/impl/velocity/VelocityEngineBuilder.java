package org.cyclopsgroup.waterview.impl.velocity;

import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

/**
 * Class to configure and build {@link VelocityEngine}
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class VelocityEngineBuilder
{
    private static final Log LOG = LogFactory.getLog( VelocityEngineBuilder.class );

    private final ExtendedProperties props;

    private int sequence = 0;

    /**
     * Default constructor
     */
    public VelocityEngineBuilder()
    {
        props = new ExtendedProperties();
        props.setProperty( "input.encoding", "UTF-8" );
    }

    /**
     * @param prefix Resource path prefix
     * @return Builder itself
     */
    public VelocityEngineBuilder addDeepClassPathResourceLoader( String prefix )
    {
        Validate.notNull( prefix, "Prefix can't be NULL" );
        sequence++;
        String loaderId = "loader" + sequence;
        props.addProperty( "resource.loader", loaderId );
        props.addProperty( loaderId + ".resource.loader.class", DeepClassPathResourceLoader.class.getName() );
        props.addProperty( loaderId + ".resource.loader.prefix", prefix );
        return this;
    }

    /**
     * @param prefixNames A list of prefix paths
     * @return Builder itself
     */
    public VelocityEngineBuilder addDeepClassPathResourceLoaders( List<String> prefixNames )
    {
        for ( String prefix : prefixNames )
        {
            addDeepClassPathResourceLoader( prefix );
        }
        return this;
    }

    /**
     * @param properties
     * @return Builder itself
     */
    public VelocityEngineBuilder setProperties( Properties properties )
    {
        for ( Object name : properties.keySet() )
        {
            props.setProperty( (String) name, properties.getProperty( (String) name ) );
        }
        return this;
    }

    /**
     * @param path Template path in filesystem
     * @return Builder itself
     */
    public VelocityEngineBuilder addFileSystemResourceLoader( String path )
    {
        sequence++;
        String loaderId = "loader" + sequence;
        props.addProperty( "resource.loader", loaderId );
        props.addProperty( loaderId + ".resource.loader.class", FileResourceLoader.class.getName() );
        props.addProperty( loaderId + ".resource.loader.path", path );
        return this;
    }

    /**
     * @param name Name of property to add
     * @param value Value of property to add
     * @return Builder itself
     */
    public VelocityEngineBuilder addProperty( String name, String value )
    {
        props.addProperty( name, value );
        return this;
    }

    /**
     * @return New {@link VelocityEngine} instance
     */
    public VelocityEngine newEngine()
    {
        VelocityEngine engine = new VelocityEngine();
        engine.setExtendedProperties( props );
        engine.setProperty( "runtime.log.logsystem", new JclLogChute() );
        LOG.info( "Initializing VelocityEngine with properties: " + props );
        try
        {
            engine.init();
            return engine;
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "Velocity engine intialization failed", e );
        }
    }

    /**
     * @param name Name of property to set
     * @param value Value of property to set
     * @return Builder itself
     */
    public VelocityEngineBuilder setProperty( String name, String value )
    {
        props.setProperty( name, value );
        return this;
    }
}
