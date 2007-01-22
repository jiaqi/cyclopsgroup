package com.cyclopsgroup.waterview.impl.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;

import com.cyclopsgroup.waterview.spi.AbstractRunData;
import com.cyclopsgroup.waterview.spi.Waterview;

public class ServletRunData
    extends AbstractRunData
{
    private final ServletContext servletContext;

    private final HttpServletRequest servletRequest;

    private final HttpServletResponse servletResponse;

    public ServletRunData( Waterview waterview, ServletContext ctx, HttpServletRequest request,
                           HttpServletResponse response )
        throws FileUploadException, IOException
    {
        servletRequest = request;
        servletResponse = response;
        servletContext = ctx;

        setQueryString( request.getQueryString() );
        setRefererUrl( request.getHeader( "referer" ) );

        //Session Context
        setSessionContext( new HttpSessionContext( request.getSession() ) );
        setSessionId( request.getSession().getId() );

        setRequestContext( new ServletRequestContext( request ) );

        //Request path
        String requestPath = request.getPathInfo();
        setRequestPath( requestPath == null ? StringUtils.EMPTY : requestPath );

        if ( FileUploadBase.isMultipartContent( servletRequest ) )
        {
            setParams( new MultipartServletRequestParameters( servletRequest, waterview.getFileUpload() ) );
        }
        else
        {
            setParams( new ServletRequestParameters( request ) );
        }

        OutputStream outputStream = response.getOutputStream();
        setOutputStream( outputStream );
        setOutput( new PrintWriter( outputStream ) );
    }

    public String getMimeType( String fileName )
    {
        return servletContext.getMimeType( fileName );
    }

    public void setOutputContentType( String contentType )
    {
        servletResponse.setContentType( contentType );
    }
}
