package org.cyclopsgroup.waterview.impl.valve;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyclopsgroup.waterview.impl.module.ModuleResolver;
import org.cyclopsgroup.waterview.impl.module.PageModule;
import org.cyclopsgroup.waterview.impl.module.WebModule;
import org.cyclopsgroup.waterview.impl.render.RuntimePage;
import org.cyclopsgroup.waterview.impl.render.RuntimeRenderer;
import org.cyclopsgroup.waterview.spi.Renderer;
import org.cyclopsgroup.waterview.spi.Valve;
import org.cyclopsgroup.waterview.spi.ValveContext;
import org.cyclopsgroup.waterview.spi.WebContext;

/**
 * Valve to render page
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class RenderPageValve
    implements Valve

{
    private static final Log LOG = LogFactory.getLog( RenderPageValve.class );

    private final String defaultLayout;

    private final ModuleResolver moduleResolver;

    private final Renderer renderer;

    private final boolean useLayout;

    /**
     * Constructor without layout
     * 
     * @param moduleResolver Module resolver
     * @param renderer Renderer to render page
     */
    public RenderPageValve( ModuleResolver moduleResolver, Renderer renderer )
    {
        this( moduleResolver, renderer, false, null );
    }

    /**
     * @param moduleResolver Module resolver
     * @param renderer Template renderer
     * @param useLayout True if use layout
     * @param defaultLayout Default template to render
     */
    public RenderPageValve( ModuleResolver moduleResolver, Renderer renderer, boolean useLayout, String defaultLayout )
    {
        Validate.notNull( moduleResolver, "Module resolver can't be NULL" );
        Validate.notNull( renderer, "Renderer can't be NULL" );
        this.moduleResolver = moduleResolver;
        this.renderer = renderer;
        this.useLayout = useLayout;
        this.defaultLayout = defaultLayout;
    }

    /**
     * Constructor with layout
     * 
     * @param moduleResolver Module resolver
     * @param renderer Renderer to render page
     * @param defaultLayout Default layout path
     */
    public RenderPageValve( ModuleResolver moduleResolver, Renderer renderer, String defaultLayout )
    {
        this( moduleResolver, renderer, true, defaultLayout );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void invoke( ValveContext context )
        throws IOException
    {
        WebContext wc = context.getWebContext();
        List<String> actions = context.getActions();

        String page = null;

        for ( Iterator<String> i = actions.iterator(); i.hasNext(); )
        {
            String action = i.next();
            if ( renderer.acceptTemplate( action ) )
            {
                if ( page == null )
                {
                    page = action;
                }
                else if ( LOG.isDebugEnabled() )
                {
                    LOG.debug( "Ignore action " + action );
                }
                i.remove();
            }
        }
        if ( page == null )
        {
            LOG.info( "No page to render, exit" );
            context.invokeNext();
            return;
        }
        if ( LOG.isDebugEnabled() )
        {
            LOG.debug( "Page is determined: " + page );
        }
        WebModule webModule = moduleResolver.findModule( page );
        String templatePath;
        if ( useLayout )
        {
            String pageSpecificLayout = null;
            if ( webModule != null )
            {
                if ( webModule instanceof PageModule )
                {
                    pageSpecificLayout = ( (PageModule) webModule ).getLayout();
                }
            }
            templatePath = StringUtils.isEmpty( pageSpecificLayout ) ? defaultLayout : pageSpecificLayout;
        }
        else
        {
            templatePath = page;
        }
        if ( LOG.isDebugEnabled() )
        {
            LOG.debug( "Rending template " + templatePath + " for page " + page );
        }
        wc.setVariable( RuntimePage.PAGE_NAME, new RuntimePage( page, webModule ) );
        RuntimeRenderer r = new RuntimeRenderer( renderer, moduleResolver, wc );
        PrintWriter output = wc.getServletResponse().getWriter();
        try
        {
            r.render( templatePath );
            context.invokeNext();
        }
        finally
        {
            output.flush();
        }
    }
}
