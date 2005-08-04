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
package com.cyclopsgroup.waterview;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 *
 * Redirect to a page
 */
public class AppendablePageRedirector extends PageRedirector
{
    private String page;

    private StringBuffer queryString = new StringBuffer();

    /**
     * Page path
     * @param page page
     */
    public AppendablePageRedirector(String page)
    {
        this.page = page;
    }

    /**
     * @param name
     * @param number
     * @return Itself
     */
    public AppendablePageRedirector addQueryData(String name, int number)
    {
        return addQueryData(name, String.valueOf(number));
    }

    /**
     * @param name
     * @param number
     * @return Itself
     */
    public AppendablePageRedirector addQueryData(String name, long number)
    {
        return addQueryData(name, String.valueOf(number));
    }

    /**
     * @param name
     * @param object
     * @return Itself
     */
    public AppendablePageRedirector addQueryData(String name, Object object)
    {
        if (queryString.length() > 0)
        {
            queryString.append('&');
        }
        String value = StringUtils.EMPTY;
        if (object != null)
        {
            value = object.toString();
        }
        try
        {
            value = URLEncoder.encode(value, "UTF-8");
        }
        catch (Exception e)
        {
            //Can not happen
        }

        queryString.append(name).append('=').append(value);
        return this;
    }

    /**
     * Overwrite or implement method getPage()
     * @see com.cyclopsgroup.waterview.PageRedirector#getPage()
     */
    public String getPage()
    {
        return page;
    }

    /**
     * Overwrite or implement method getQueryString()
     * @see com.cyclopsgroup.waterview.PageRedirector#getQueryString()
     */
    public String getQueryString()
    {
        return queryString.toString();
    }
}
