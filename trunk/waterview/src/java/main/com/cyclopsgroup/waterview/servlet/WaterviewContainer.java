/* ==========================================================================
 * Copyright 2002-2004 Cyclops Group Community
 * 
 * Licensed under the Open Software License, Version 2.1 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://opensource.org/licenses/osl-2.1.php
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * =========================================================================
 */
package com.cyclopsgroup.waterview.servlet;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.configuration.PlexusConfigurationMerger;
import org.codehaus.plexus.configuration.xml.xstream.PlexusTools;
import org.codehaus.plexus.util.FileUtils;

/**
 * Waterview specific plexus container
 * 
 * @task Once the bug is fixed, this class will not be necessary
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo </a>
 */
public class WaterviewContainer extends DefaultPlexusContainer
{

    /**
     * Override or implement method of parent class or interface
     *
     * @see org.codehaus.plexus.DefaultPlexusContainer#initializeConfiguration()
     */
    protected void initializeConfiguration() throws Exception
    {
        super.initializeConfiguration();
        processConfigurationsDirectory();
    }

    /**
     * Process any additional component configuration files that have been
     * specified. The specified directory is scanned recursively so configurations
     * can be within nested directories to help with component organization.
     * 
     * @task Rewrite the one in DefaultPlexusContainer since loaded configurations are not added into main configuration
     * 
     * @throws Exception Throw it out
     */
    private void processConfigurationsDirectory() throws Exception
    {
        String s = configuration.getChild("configurations-directory").getValue(
                null);

        if (s != null)
        {
            File configurationsDirectory = new File(s);

            if (configurationsDirectory.exists()
                    && configurationsDirectory.isDirectory())
            {
                List componentConfigurationFiles = FileUtils.getFiles(
                        configurationsDirectory, "**/*.xml", "");

                for (Iterator i = componentConfigurationFiles.iterator(); i
                        .hasNext();)
                {
                    File componentConfigurationFile = (File) i.next();

                    PlexusConfiguration componentConfiguration = PlexusTools
                            .buildConfiguration(getInterpolationConfigurationReader(new FileReader(
                                    componentConfigurationFile)));
                    configuration = PlexusConfigurationMerger.merge(
                            componentConfiguration, configuration);
                }
            }
        }
    }
}