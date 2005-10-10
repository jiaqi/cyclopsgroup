/* ==========================================================================
 * Copyright 2002-2005 Cyclops Group Community
 *
 * Licensed under the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE
 * (CDDL) Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/cddl1.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * =========================================================================
 */
package com.cyclopsgroup.waterview.jelly;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.XMLOutput;

import com.cyclopsgroup.waterview.Waterview;
import com.cyclopsgroup.waterview.utils.WaterviewPlexusContainer;

/**
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 *
 */
public class JellyScriptsRunner
{
    /**
     * Main entry to run a script
     *
     * @param args Script paths
     * @throws Exception Throw it out
     */
    public static final void main( String[] args )
        throws Exception
    {
        List scripts = new ArrayList();
        for ( int i = 0; i < args.length; i++ )
        {
            String path = args[i];
            File file = new File( path );
            if ( file.isFile() )
            {
                scripts.add( file.toURL() );
            }
            else
            {
                Enumeration enu = JellyScriptsRunner.class.getClassLoader().getResources( path );
                CollectionUtils.addAll( scripts, enu );
            }
        }
        if ( scripts.isEmpty() )
        {
            System.out.println( "No script to run, return!" );
            return;
        }

        String basedir = new File( "" ).getAbsolutePath();
        Properties initProperties = new Properties( System.getProperties() );
        initProperties.setProperty( "basedir", basedir );
        initProperties.setProperty( "plexus.home", basedir );

        WaterviewPlexusContainer container = new WaterviewPlexusContainer();
        for ( Iterator j = initProperties.keySet().iterator(); j.hasNext(); )
        {
            String initPropertyName = (String) j.next();
            container.addContextValue( initPropertyName, initProperties.get( initPropertyName ) );
        }

        container.addContextValue( Waterview.INIT_PROPERTIES, initProperties );
        container.initialize();
        container.start();

        JellyEngine je = (JellyEngine) container.lookup( JellyEngine.ROLE );
        JellyContext jc = new JellyContext( je.getGlobalContext() );
        XMLOutput output = XMLOutput.createXMLOutput( System.out );
        for ( Iterator i = scripts.iterator(); i.hasNext(); )
        {
            URL script = (URL) i.next();
            System.out.print( "Running script " + script );
            ExtendedProperties ep = new ExtendedProperties();
            ep.putAll( initProperties );
            ep.load( script.openStream() );
            for ( Iterator j = ep.keySet().iterator(); i.hasNext(); )
            {
                String name = (String) j.next();
                if ( !name.startsWith( "script." ) )
                {
                    continue;
                }
                if ( name.endsWith( ".file" ) )
                {
                    File file = new File( ep.getString( name ) );
                    if ( file.exists() )
                    {
                        System.out.println( "Runner jelly file " + file );
                        jc.runScript( file, output );
                    }
                }
                else if ( name.endsWith( ".resource" ) )
                {
                    Enumeration k = JellyScriptsRunner.class.getClassLoader().getResources( ep.getString( name ) );
                    while ( j != null && k.hasMoreElements() )
                    {
                        URL s = (URL) k.nextElement();
                        System.out.println( "Running jelly script " + s );
                        jc.runScript( s, output );
                    }
                }
            }
            //jc.runScript( script, XMLOutput.createDummyXMLOutput() );
            System.out.println( "... Done!" );
        }
        container.dispose();
    }
}
