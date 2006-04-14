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
package com.cyclopsgroup.waterview.core;

import java.util.List;

import com.cyclopsgroup.waterview.jelly.JellyEngine;
import com.cyclopsgroup.waterview.spi.PipelineContext;
import com.cyclopsgroup.waterview.spi.RunDataSpi;
import com.cyclopsgroup.waterview.test.MockRunData;
import com.cyclopsgroup.waterview.test.WaterviewTestCase;

/**
 * Test case for ParseURLValve
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo </a>
 */
public class ParseURLValveTest
    extends WaterviewTestCase
{
    /**
     * Test invocation
     *
     * @throws Exception
     */
    public void testInvoke()
        throws Exception
    {
        lookup( JellyEngine.ROLE );
        MockRunData data = createRunData();
        data.setRequestPath( "/!do!/aaa/!do!/bbb/BAction/!do!/ccc/!show!/ddd.jelly" );
        ParseURLValve v = (ParseURLValve) lookup( ParseURLValve.class.getName() );
        v.invoke( data, new PipelineContext()
        {

            public void invokeNextValve( RunDataSpi data )
                throws Exception
            {
                //do nothing
            }
        } );
        //assertEquals("/ddd.jelly", runtime.getPage());
        assertEquals( 3, data.getActions().size() );
        assertEquals( "/aaa", data.getActions().get( 0 ) );
        assertEquals( "/bbb/BAction", data.getActions().get( 1 ) );
        assertEquals( "/ccc", data.getActions().get( 2 ) );
    }

    /**
     * @throws Exception
     */
    public void testParseRequestPath()
        throws Exception
    {
        String path = "/!do!/aaa/!do!/bbb/BAction/!do!/ccc/!show!/ddd.jelly";
        List parts = ParseURLValve.parseRequestPath( path );
        assertEquals( 4, parts.size() );
        assertEquals( "/!do!/aaa", parts.get( 0 ) );
        assertEquals( "/!do!/bbb/BAction", parts.get( 1 ) );
        assertEquals( "/!do!/ccc", parts.get( 2 ) );
        assertEquals( "/!show!/ddd.jelly", parts.get( 3 ) );
    }
}
