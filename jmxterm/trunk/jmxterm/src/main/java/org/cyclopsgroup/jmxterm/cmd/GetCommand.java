package org.cyclopsgroup.jmxterm.cmd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.JMException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.Validate;
import org.cyclopsgroup.jcli.AutoCompletable;
import org.cyclopsgroup.jcli.annotation.Argument;
import org.cyclopsgroup.jcli.annotation.Cli;
import org.cyclopsgroup.jcli.annotation.Option;
import org.cyclopsgroup.jmxterm.Command;
import org.cyclopsgroup.jmxterm.Session;
import org.cyclopsgroup.jmxterm.SyntaxUtils;

/**
 * Get value of MBean attribute(s)
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
@Cli( name = "get", description = "Get value of MBean attribute(s)", note = "* stands for all attributes. eg. get Attribute1 Attribute2 or get *" )
public class GetCommand
    extends Command
    implements AutoCompletable
{
    private List<String> attributes = new ArrayList<String>();

    private String bean;

    private String domain;

    private boolean showDescription;

    @SuppressWarnings( "unchecked" )
    private void displayAttributes()
        throws IOException, JMException
    {
        Session session = getSession();
        String beanName = BeanCommand.getBeanName( bean, domain, session );
        ObjectName name = new ObjectName( beanName );
        session.msg( "mbean = " + beanName + ":" );
        MBeanServerConnection con = session.getConnection().getServerConnection();
        MBeanAttributeInfo[] ais = con.getMBeanInfo( name ).getAttributes();
        Map<String, MBeanAttributeInfo> attributeNames =
            (Map<String, MBeanAttributeInfo>) ListOrderedMap.decorate( new HashMap<String, MBeanAttributeInfo>() );
        if ( attributes.contains( "*" ) )
        {
            for ( MBeanAttributeInfo ai : ais )
            {
                attributeNames.put( ai.getName(), ai );
            }
        }
        else
        {
            for ( String arg : attributes )
            {
                for ( MBeanAttributeInfo ai : ais )
                {
                    if ( ai.getName().equals( arg ) )
                    {
                        attributeNames.put( arg, ai );
                        break;
                    }
                }
            }
        }
        for ( Map.Entry<String, MBeanAttributeInfo> entry : attributeNames.entrySet() )
        {
            String attributeName = entry.getKey();
            MBeanAttributeInfo i = entry.getValue();
            if ( i.isReadable() )
            {
                Object result = con.getAttribute( name, attributeName );
                if ( session.isAbbreviated() )
                {
                    SyntaxUtils.printValue( session.output, result, 0, false );
                    session.output.println();
                }
                else
                {
                    SyntaxUtils.printExpression( session.output, attributeName, result, i.getDescription(), 2,
                                                 showDescription );
                }
            }
            else
            {
                session.msg( "  " + i.getName() + " is not readable", i.getName() + "=?" );
            }
        }
    }

    /**
     * @inheritDoc
     */
    public List<String> doSuggestArgument()
        throws IOException, JMException
    {
        if ( getSession().getBean() != null )
        {
            MBeanServerConnection con = getSession().getConnection().getServerConnection();
            MBeanAttributeInfo[] ais = con.getMBeanInfo( new ObjectName( getSession().getBean() ) ).getAttributes();
            List<String> results = new ArrayList<String>( ais.length );
            for ( MBeanAttributeInfo ai : ais )
            {
                results.add( ai.getName() );
            }
            return results;
        }
        return null;
    }

    @Override
    protected List<String> doSuggestOption( String optionName )
        throws Exception
    {
        if ( optionName.equals( "d" ) )
        {
            return DomainsCommand.getCandidateDomains( getSession() );
        }
        else if ( optionName.equals( "b" ) )
        {
            return BeanCommand.getCandidateBeanNames( getSession() );
        }
        else
        {
            return null;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void execute()
        throws JMException, IOException
    {
        if ( attributes.isEmpty() )
        {
            throw new IllegalArgumentException( "Please specify at least one attribute" );
        }
        displayAttributes();
    }

    /**
     * @param attributes List of attribute names
     */
    @Argument( description = "Name of attributes to select" )
    public final void setAttributes( List<String> attributes )
    {
        Validate.notNull( attributes, "Attributes can't be NULL" );
        this.attributes = attributes;
    }

    /**
     * @param bean Bean under which attribute is get
     */
    @Option( name = "b", longName = "bean", description = "MBean name where the attribute is. Optional if bean has been set" )
    public final void setBean( String bean )
    {
        this.bean = bean;
    }

    /**
     * @param domain Domain under which bean is selected
     */
    @Option( name = "d", longName = "domain", description = "Domain of bean, optional" )
    public final void setDomain( String domain )
    {
        this.domain = domain;
    }

    /**
     * @param showDescription True to show detail description
     */
    @Option( name = "i", longName = "info", description = "Show detail information of each attribute" )
    public final void setShowDescription( boolean showDescription )
    {
        this.showDescription = showDescription;
    }
}
