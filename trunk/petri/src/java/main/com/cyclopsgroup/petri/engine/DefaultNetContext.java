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
package com.cyclopsgroup.petri.engine;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import com.cyclopsgroup.petri.definition.NetContext;

/**
 * Default implementation of NetContext
 * 
 * @author <a href="mailto:jiiaqi@yahoo.com">Jiaqi Guo </a>
 */
public class DefaultNetContext implements NetContext
{

    private HashMap content = new HashMap();

    private NetContext parent;

    /**
     * Constructor for class DefaultNetContext
     */
    public DefaultNetContext()
    {

    }

    /**
     * Constructor for class DefaultNetContext
     *
     * @param parent Parent context
     */
    public DefaultNetContext(NetContext parent)
    {
        this.parent = parent;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.petri.definition.NetContext#get(java.lang.String)
     */
    public Object get(String variableName)
    {
        if (content.containsKey(variableName))
        {
            return content.get(variableName);
        }
        if (parent != null)
        {
            return parent.get(variableName);
        }
        return null;
    }

    /**
     * Get hash map content
     *
     * @return Content hash map
     */
    public HashMap getContent()
    {
        return content;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.petri.definition.NetContext#getNames()
     */
    public String[] getNames()
    {
        if (parent == null)
        {
            return (String[]) content.keySet().toArray(
                    ArrayUtils.EMPTY_STRING_ARRAY);
        }
        HashSet names = new HashSet(content.keySet());
        CollectionUtils.addAll(names, parent.getNames());
        return (String[]) names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.petri.definition.NetContext#put(java.lang.String, java.lang.Object)
     */
    public void put(String variableName, Object variable)
    {
        if (variable == null)
        {
            content.remove(variableName);
        }
        else
        {
            content.put(variableName, variable);
        }
    }
}