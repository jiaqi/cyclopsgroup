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

import org.apache.commons.lang.exception.NestableException;

/**
 * Exception to show that page is not found
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo </a>
 */
public class PageNotFoundException extends NestableException
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3256999947650939193L;

    /**
     * 
     * @uml.property name="page" 
     */
    private String page;

    /**
     * Constructor of PageNotFoundException
     * 
     * @param page Page string
     */
    public PageNotFoundException(String page)
    {
        super("Page [" + page + "] is not found");
        this.page = page;
    }

    /**
     * Method getPage() in class PageNotFoundException
     * 
     * @return Page path
     * 
     * @uml.property name="page"
     */
    public String getPage()
    {
        return page;
    }
}