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
package com.cyclopsgroup.waterview.core.valves;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.avalon.framework.logger.AbstractLogEnabled;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;

import com.cyclopsgroup.waterview.ActionResolver;
import com.cyclopsgroup.waterview.ModuleManager;
import com.cyclopsgroup.waterview.PageRuntime;
import com.cyclopsgroup.waterview.PipelineContext;
import com.cyclopsgroup.waterview.Valve;

/**
 * Valve to run modules
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo </a>
 */
public class ResolveActionsValve extends AbstractLogEnabled implements Valve
{
    /**
     * Role of this component
     */
    public static final String ROLE = ResolveActionsValve.class.getName();

    private Map actionResolvers = ListOrderedMap.decorate(new Hashtable());

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.Valve#invoke(com.cyclopsgroup.waterview.PageRuntime, com.cyclopsgroup.waterview.PipelineContext)
     */
    public void invoke(PageRuntime runtime, PipelineContext context)
            throws Exception
    {
        ModuleManager mm = (ModuleManager) runtime.getServiceManager().lookup(
                ModuleManager.ROLE);
        for (Iterator i = runtime.getActions().iterator(); i.hasNext();)
        {
            String actionName = (String) i.next();
            String packageName = runtime.getPackage();
            if (actionName.indexOf(':') >= 0)
            {
                String[] parts = StringUtils.split(actionName, ':');
                actionName = parts[1];
                packageName = parts[0];
                packageName = mm.getPackageName(packageName);
            }
            for (Iterator j = actionResolvers.keySet().iterator(); j.hasNext();)
            {
                String pattern = (String) j.next();
                if (Pattern.matches('^' + pattern + '$', actionName))
                {
                    ActionResolver resolver = (ActionResolver) actionResolvers
                            .get(pattern);
                    resolver.resolveAction(packageName, actionName, runtime);
                    break;
                }
            }
        }
        context.invokeNextValve(runtime);
    }

    /**
     * Register an action resolver
     *
     * @param pattern Action pattern
     * @param resolver Resolver object
     */
    public void registerActionResolver(String pattern, ActionResolver resolver)
    {
        actionResolvers.put(pattern, resolver);
    }
}
