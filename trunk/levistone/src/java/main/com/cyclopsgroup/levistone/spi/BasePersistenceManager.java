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
package com.cyclopsgroup.levistone.spi;

import com.cyclopsgroup.levistone.PersistenceException;
import com.cyclopsgroup.levistone.PersistenceManager;
import com.cyclopsgroup.levistone.Query;
import com.cyclopsgroup.levistone.Session;

/**
 * Base implementation of persistence manager
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo </a>
 */
public abstract class BasePersistenceManager implements PersistenceManager
{
    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.levistone.PersistenceManager#createQuery(java.lang.Class)
     */
    public Query createQuery(Class entityType)
    {
        return new Query(entityType);
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.levistone.PersistenceManager#openSession()
     */
    public Session openSession() throws PersistenceException
    {
        return openSession(Session.DEFAULT_NAME);
    }
}