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
package com.cyclopsgroup.levistone;

/**
 * Query model object
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo </a>
 */
public class Query
{
    private Class entityType;

    /**
     * Constructor for class Query
     *
     * @param entityType Entity class
     */
    public Query(Class entityType)
    {
        this.entityType = entityType;
    }

    /**
     * Get current entity type
     *
     * @return Class of entity
     */
    public Class getEntityType()
    {
        return entityType;
    }
}