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
package com.cyclopsgroup.waterview;

import java.util.Properties;

import junit.framework.TestCase;

import org.apache.commons.collections.MultiHashMap;

/**
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 *
 */
public class DummyTest
    extends TestCase
{
    /**
     * Dummy  test case
     */
    public void testDummy()
    {
        MultiHashMap map = new MultiHashMap();
        map.put( "a", "a1" );
        map.put( "a", "a2" );
        Properties p = new Properties();
        p.putAll( map );
        System.out.println( p.getProperty( "a" ) );
    }
}
