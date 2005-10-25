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

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.avalon.framework.service.ServiceManager;
import org.apache.commons.lang.ArrayUtils;

/**
 * Abstract base runtime class
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo </a>
 */
public abstract class AbstractRuntimeData
    implements RuntimeData
{
    private List actions = new LinkedList();

    private String applicationBaseUrl;

    private String inputContentType;

    private Locale locale = Locale.getDefault();

    private PrintWriter output;

    private OutputStream outputStream;

    private Path page;

    private String pageBaseUrl;

    private String queryString;

    private String redirectUrl;

    private String refererUrl;

    private Context requestContext;

    private Parameters requestParameters;

    private String requestPath;

    private ServiceManager serviceManager;

    private Context sessionContext;

    private String sessionId;

    private boolean stopped;

    private String themeName;

    private TimeZone timeZone = TimeZone.getDefault();

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getActions()
     */
    public List getActions()
    {
        return actions;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getApplicationBaseUrl()
     */
    public String getApplicationBaseUrl()
    {
        return applicationBaseUrl;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getInputContentType()
     */
    public String getInputContentType()
    {
        return inputContentType;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getLocale()
     */
    public Locale getLocale()
    {
        return locale;
    }

    /**
     * Override method getMessages in class AbstractRuntimeData
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getMessages()
     */
    public String[] getMessages()
    {
        List messages = (List) getSessionContext().get( MESSAGES_NAME );
        if ( messages == null )
        {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        String[] ret = (String[]) messages.toArray( ArrayUtils.EMPTY_STRING_ARRAY );
        messages.clear();
        return ret;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getOutput()
     */
    public PrintWriter getOutput()
    {
        return output;
    }

    /**
     * Overwrite or implement method getOutputStream()
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getOutputStream()
     */
    public OutputStream getOutputStream()
    {
        return outputStream;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getPage()
     */
    public Path getPage()
    {
        return page;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getPageBaseUrl()
     */
    public String getPageBaseUrl()
    {
        return pageBaseUrl;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getParameters()
     */
    public Parameters getParameters()
    {
        return requestParameters;
    }

    /**
     * Getter method for property queryString
     *
     * @return Returns the queryString.
     */
    public String getQueryString()
    {
        return queryString;
    }

    /**
     * Overwrite or implement method getRedirectUrl()
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getRedirectUrl()
     */
    public String getRedirectUrl()
    {
        return redirectUrl;
    }

    /**
     * Getter method for property refererUrl
     *
     * @return Returns the refererUrl.
     */
    public String getRefererUrl()
    {
        return refererUrl;
    }

    /**
     * Overwrite or implement method getRequestContext()
     * @see com.cyclopsgroup.waterview.RuntimeData#getRequestContext()
     */
    public Context getRequestContext()
    {
        return requestContext;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getRequestPath()
     */
    public String getRequestPath()
    {
        return requestPath;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getServiceManager()
     */
    public ServiceManager getServiceManager()
    {
        return serviceManager;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getSessionContext()
     */
    public Context getSessionContext()
    {
        return sessionContext;
    }

    /**
     * Overwrite or implement method getSessionId()
     * @see com.cyclopsgroup.waterview.RuntimeData#getSessionId()
     */
    public String getSessionId()
    {
        return sessionId;
    }

    /**
     * Overwrite or implement method getThemeName()
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getThemeName()
     */
    public String getThemeName()
    {
        return themeName;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#getTimeZone()
     */
    public TimeZone getTimeZone()
    {
        return timeZone;
    }

    /**
     * Overwrite or implement method isStopped()
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#isStopped()
     */
    public boolean isStopped()
    {
        return stopped;
    }

    /**
     * Setter method for applicationBaseUrl
     *
     * @param applicationBaseUrl The applicationBaseUrl to set.
     */
    public void setApplicationBaseUrl( String applicationBaseUrl )
    {
        this.applicationBaseUrl = applicationBaseUrl;
        if ( getRequestContext() != null )
        {
            getRequestContext().put( "applicationBase", applicationBaseUrl );
        }
    }

    /**
     * Setter method for inputContentType
     *
     * @param inputContentType The inputContentType to set.
     */
    public void setInputContentType( String inputContentType )
    {
        this.inputContentType = inputContentType;
    }

    /**
     * Overwrite or implement method setLocale()
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#setLocale(java.util.Locale)
     */
    public void setLocale( Locale locale )
    {
        this.locale = locale;
    }

    /**
     * Setter method for output
     *
     * @param output The output to set.
     */
    public void setOutput( PrintWriter output )
    {
        this.output = output;
    }

    /**
     * Setter method for property outputStream
     *
     * @param outputStream The outputStream to set.
     */
    public void setOutputStream( OutputStream outputStream )
    {
        this.outputStream = outputStream;
    }

    /**
     * Overwrite or implement method setPage()
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#setPage(com.cyclopsgroup.waterview.Path)
     */
    public void setPage( Path page )
    {
        getRequestContext().put( PAGE_NAME, page );
        this.page = page;
    }

    /**
     * Setter method for pageBaseUrl
     *
     * @param pageBaseUrl The pageBaseUrl to set.
     */
    public void setPageBaseUrl( String pageBaseUrl )
    {
        this.pageBaseUrl = pageBaseUrl;
        if ( getRequestContext() != null )
        {
            getRequestContext().put( "pageBase", pageBaseUrl );
        }
    }

    /**
     * Setter method for requestParameters
     *
     * @param requestParameters The requestParameters to set.
     */
    public void setParams( Parameters requestParameters )
    {
        this.requestParameters = requestParameters;
    }

    /**
     * Setter method for property queryString
     *
     * @param queryString The queryString to set.
     */
    public void setQueryString( String queryString )
    {
        this.queryString = queryString;
    }

    /**
     * Overwrite or implement method setRedirectUrl()
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#setRedirectUrl(java.lang.String)
     */
    public void setRedirectUrl( String url )
    {
        redirectUrl = url;
    }

    /**
     * Setter method for property refererUrl
     *
     * @param refererUrl The refererUrl to set.
     */
    public void setRefererUrl( String refererUrl )
    {
        this.refererUrl = refererUrl;
    }

    /**
     * Set request context
     * @param pageContext Context object
     */
    public void setRequestContext( Context pageContext )
    {
        this.requestContext = pageContext;
    }

    /**
     * Setter method for requestPath
     *
     * @param requestPath The requestPath to set.
     */
    public void setRequestPath( String requestPath )
    {
        this.requestPath = requestPath;
    }

    /**
     * Setter method for serviceManager
     *
     * @param serviceManager The serviceManager to set.
     */
    public void setServiceManager( ServiceManager serviceManager )
    {
        this.serviceManager = serviceManager;
    }

    /**
     * Setter method for sessionContext
     *
     * @param sessionContext The sessionContext to set.
     */
    public void setSessionContext( Context sessionContext )
    {
        this.sessionContext = sessionContext;
    }

    /**
     * Set session ID
     * @param sessionId
     */
    public void setSessionId( String sessionId )
    {
        this.sessionId = sessionId;
    }

    /**
     * Setter method for field themeName
     *
     * @param themeName The themeName to set.
     */
    public void setThemeName( String themeName )
    {
        this.themeName = themeName;
    }

    /**
     * Setter method for timeZone
     *
     * @param timeZone The timeZone to set.
     */
    public void setTimeZone( TimeZone timeZone )
    {
        this.timeZone = timeZone;
    }

    /**
     * Overwrite or implement method stop()
     *
     * @see com.cyclopsgroup.waterview.RuntimeData#stop()
     */
    public void stop()
    {
        stopped = true;
    }
}
