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
package com.cyclopsgroup.waterview.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang.ArrayUtils;

/**
 * TODO Add javadoc for class
 * 
 * @author <a href="mailto:jiiaqi@yahoo.com">Jiaqi Guo </a>
 */
public class ResolverManager
{

    private HashMap actionResolvers = new HashMap();

    private HashSet extensions = new HashSet();

    private ArrayList packages = new ArrayList();

    private HashMap rendererResolvers = new HashMap();

    public ActionResolver getActonResolver(String extension)
    {
        return (ActionResolver) actionResolvers.get(extension);
    }

    /**
     * Get all recognizable extensions
     * 
     * @return Array of extensions
     */
    public String[] getExtensions()
    {
        return (String[]) extensions.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public RendererResolver getRendererResolver(String extension)
    {
        return (RendererResolver) rendererResolvers.get(extension);
    }

    public void registerActionResolver(String extension,
            ActionResolver actionResolver)
    {
        extensions.add(extension);
        actionResolvers.put(extension, actionResolver);
    }

    public void registerPackage(String packageName)
    {
        packages.add(packageName);
    }

    public void registerRendererResolver(String extension,
            RendererResolver rendererResolver)
    {
        extensions.add(extension);
        rendererResolvers.put(extension, rendererResolver);
    }
}