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
package com.cyclopsgroup.waterview.velocity;

import java.util.Properties;

import org.apache.avalon.framework.activity.Initializable;
import org.apache.avalon.framework.configuration.Configurable;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.logger.AbstractLogEnabled;
import org.apache.commons.collections.LRUMap;
import org.apache.velocity.app.VelocityEngine;

import com.cyclopsgroup.waterview.Resolver;
import com.cyclopsgroup.waterview.UIRuntime;

/**
 * TODO Add javadoc for class
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo </a>
 */
public class VelocityPageRenderer extends AbstractLogEnabled implements
        Resolver, Initializable, Configurable
{

    private LRUMap templateCache;

    private VelocityEngine velocityEngine;

    private Properties velocityProps;

    /**
     * Override method configure in super class of VelocityPageRenderer
     * 
     * @see org.apache.avalon.framework.configuration.Configurable#configure(org.apache.avalon.framework.configuration.Configuration)
     */
    public void configure(Configuration conf) throws ConfigurationException
    {
        velocityProps = new Properties();
        Configuration[] velocityConfs = conf.getChild("velocity-properties")
                .getChildren("property");
        for (int i = 0; i < velocityConfs.length; i++)
        {
            Configuration velocityConf = velocityConfs[i];
            velocityProps.setProperty(velocityConf.getAttribute("name"),
                    velocityConf.getValue());
        }
    }

    /**
     * Override method initialize in super class of VelocityPageRenderer
     * 
     * @see org.apache.avalon.framework.activity.Initializable#initialize()
     */
    public void initialize() throws Exception
    {
        velocityEngine = new VelocityEngine();
        velocityEngine.init(velocityProps);
    }

    /**
     * Override method resolve in super class of VelocityPageRenderer
     * 
     * @see com.cyclopsgroup.waterview.Resolver#resolve(java.lang.String, com.cyclopsgroup.waterview.UIRuntime)
     */
    public void resolve(String path, UIRuntime runtime) throws Exception
    {
        RuntimeRenderer renderer = new RuntimeRenderer(runtime, velocityEngine,
                templateCache);
        runtime.getUIContext().put("renderer", renderer);
        renderer.render("layout", path);
    }
}