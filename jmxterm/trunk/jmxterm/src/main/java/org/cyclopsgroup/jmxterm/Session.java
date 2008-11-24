package org.cyclopsgroup.jmxterm;

import java.io.IOException;
import java.util.Map;

import javax.management.remote.JMXServiceURL;

import org.apache.commons.lang.Validate;
import org.cyclopsgroup.jmxterm.io.CommandInput;
import org.cyclopsgroup.jmxterm.io.CommandOutput;
import org.cyclopsgroup.jmxterm.io.UnimplementedCommandInput;
import org.cyclopsgroup.jmxterm.io.VerboseCommandOutput;
import org.cyclopsgroup.jmxterm.io.VerboseCommandOutputConfig;

/**
 * JMX communication context. This class exists for the whole lifecycle of a command execution. It is NOT thread safe.
 * The caller(CommandCenter) makes sure all calls are synchronized.
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public abstract class Session
    implements VerboseCommandOutputConfig
{
    private boolean abbreviated;

    private String bean;

    private boolean closed;

    private String domain;

    /**
     * Public output field
     */
    public final CommandOutput output;

    /**
     * Public input field
     */
    public final CommandInput input;

    private boolean verbose;

    /**
     * @param output Output destination
     */
    protected Session( CommandOutput output, CommandInput input )
    {
        Validate.notNull( output, "Output can't be NULL" );
        this.output = new VerboseCommandOutput( output, this );
        this.input = input == null ? new UnimplementedCommandInput() : input;
    }

    /**
     * Close JMX terminal console. Supposedly, process terminates after this call
     */
    public void close()
    {
        if ( closed )
        {
            return;
        }
        closed = true;
    }

    /**
     * Connect to MBean server
     * 
     * @param url URL to connect
     * @param env Environment variables
     * @throws IOException
     */
    public abstract void connect( JMXServiceURL url, Map<String, Object> env )
        throws IOException;

    /**
     * Close JMX connector
     * 
     * @throws IOException Thrown when connection can't be closed
     */
    public abstract void disconnect()
        throws IOException;

    /**
     * @return Current selected bean
     */
    public final String getBean()
    {
        return bean;
    }

    /**
     * @return Current open JMX server connection
     */
    public abstract Connection getConnection();

    /**
     * @return Current domain
     */
    public final String getDomain()
    {
        return domain;
    }

    /**
     * @return True if output is abbreviated
     */
    public final boolean isAbbreviated()
    {
        return abbreviated;
    }

    /**
     * @return True if {@link #close()} has been called
     */
    public final boolean isClosed()
    {
        return closed;
    }

    /**
     * @return True if there's a open connection to JMX server
     */
    public abstract boolean isConnected();

    /**
     * @return True if <code>verbose</code> option is turned on
     */
    public final boolean isVerbose()
    {
        return verbose;
    }

    /**
     * @param abbreviated True if <code>abbreviated</code> option is to be turned on
     */
    public final void setAbbreviated( boolean abbreviated )
    {
        this.abbreviated = abbreviated;
    }

    /**
     * Set current selected bean
     * 
     * @param bean Bean to select
     */
    public final void setBean( String bean )
    {
        this.bean = bean;
    }

    /**
     * Set current selected domain
     * 
     * @param domain Domain to select
     */
    public final void setDomain( String domain )
    {
        Validate.notNull( domain, "domain can't be NULL" );
        this.domain = domain;
    }

    /**
     * Set verbose option
     * 
     * @param verbose Verbose option
     */
    public final void setVerbose( boolean verbose )
    {
        this.verbose = verbose;
    }

    /**
     * Set domain and bean to be NULL
     */
    public void unsetDomain()
    {
        bean = null;
        domain = null;
    }
}
