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
package com.cyclopsgroup.waterview;

/**
 * interface to resolver action
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo </a>
 */
public interface ModuleResolver
{
    /** Role name of this component in container */
    String ROLE = ModuleResolver.class.getName();

    /**
     * Get package names
     *
     * @return Package names
     */
    String[] getModulePackages();

    /**
     * Resolve module with given module name
     *
     * @param runtime UIRuntime object
     * @param module Module name
     * @throws Exception Throw it out
     */
    void resolve(UIRuntime runtime, String module) throws Exception;
}