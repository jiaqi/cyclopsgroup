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
package com.cyclopsgroup.waterview.jsp;

import java.io.File;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.avalon.framework.logger.AbstractLogEnabled;
import org.apache.avalon.framework.service.ServiceException;
import org.apache.avalon.framework.service.ServiceManager;
import org.apache.avalon.framework.service.Serviceable;
import org.apache.commons.lang.StringUtils;

import com.cyclopsgroup.waterview.Context;
import com.cyclopsgroup.waterview.Path;
import com.cyclopsgroup.waterview.RunData;
import com.cyclopsgroup.waterview.spi.DynaViewFactory;
import com.cyclopsgroup.waterview.spi.ModuleService;
import com.cyclopsgroup.waterview.spi.View;

/**
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 *
 * JSP engine
 */
public class JspEngine
    extends AbstractLogEnabled
    implements Serviceable, DynaViewFactory
{
    /** Role name of this component */
    public static final String ROLE = JspEngine.class.getName();

    /**
     * Overwrite or implement method createView()
     *
     * @see com.cyclopsgroup.waterview.spi.DynaViewFactory#createView(com.cyclopsgroup.waterview.Path)
     */
    public View createView( Path path )
        throws Exception
    {
        String p = path.getPath();
        if ( StringUtils.isNotEmpty( path.getPackage() ) )
        {
            p = '/' + StringUtils.replace( path.getPackage(), ".", "/" ) + p;
        }
        p = "/templates" + p;
        return new JspView( p, path.getFullPath() );
    }

    /**
     * @param path Absolute JSP path
     * @param data Runtime data
     * @param viewContext View context
     * @throws Exception Throw it out
     */
    public void renderJsp( String path, RunData data, Context viewContext )
        throws Exception
    {
        HttpServletRequest request = (HttpServletRequest) data.getRequestContext().get( "request" );
        HttpServletResponse response = (HttpServletResponse) data.getRequestContext().get( "response" );
        ServletContext servletContext = (ServletContext) data.getRequestContext().get( "servletContext" );
        if ( request == null || response == null || servletContext == null )
        {
            data.getOutput().println(
                                      "Jsp " + path + " is not rendered correctly with request " + request
                                          + ", response " + response + ", context " + servletContext );
        }
        request.setAttribute( "viewContext", viewContext );
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher( path );
        File jspFile = new File( servletContext.getRealPath( path ) );
        if ( dispatcher == null || !jspFile.isFile() )
        {
            data.getOutput().println( "Jsp " + path + " doesn't exist" );
        }
        else
        {
            dispatcher.include( request, response );
            response.getWriter().flush();
        }
        request.removeAttribute( "viewContext" );
    }

    /**
     * Overwrite or implement method service()
     * @see org.apache.avalon.framework.service.Serviceable#service(org.apache.avalon.framework.service.ServiceManager)
     */
    public void service( ServiceManager serviceManager )
        throws ServiceException
    {
        ModuleService moduleManager = (ModuleService) serviceManager.lookup( ModuleService.ROLE );
        moduleManager.registerDynaViewFactory( ".+\\.jsp", this );
    }
}
