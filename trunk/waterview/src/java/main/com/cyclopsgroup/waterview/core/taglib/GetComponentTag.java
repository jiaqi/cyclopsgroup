/* ==========================================================================
 * Copyright 2002-2005 Cyclops Group Community
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
package com.cyclopsgroup.waterview.core.taglib;

import org.apache.avalon.framework.service.ServiceManager;
import org.apache.commons.jelly.XMLOutput;

import com.cyclopsgroup.waterview.spi.taglib.BaseTag;

/**
 * Tag to lookup component
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo </a>
 */
public class GetComponentTag extends BaseTag
{
    private String role;

    private String var;

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.spi.taglib.BaseTag#doTag(org.apache.avalon.framework.service.ServiceManager, org.apache.commons.jelly.XMLOutput)
     */
    protected void doTag(ServiceManager serviceManager, XMLOutput output)
            throws Exception
    {
        requireAttribute("role");
        requireAttribute("var");
        Object component = serviceManager.lookup(getRole());
        getContext().setVariable(getVar(), component);
    }

    /**
     * Getter method for role
     *
     * @return Returns the role.
     */
    public String getRole()
    {
        return role;
    }

    /**
     * Getter method for var
     *
     * @return Returns the var.
     */
    public String getVar()
    {
        return var;
    }

    /**
     * Setter method for role
     *
     * @param role The role to set.
     */
    public void setRole(String role)
    {
        this.role = role;
    }

    /**
     * Setter method for var
     *
     * @param var The var to set.
     */
    public void setVar(String var)
    {
        this.var = var;
    }
}
