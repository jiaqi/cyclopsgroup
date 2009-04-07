package org.cyclopsgroup.waterview.impl.module;

import org.cyclopsgroup.waterview.WebContext;

/**
 * Internally used interface for rendering something
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public interface WebModule
{
    /**
     * @param context Current web context
     */
    void render(WebContext context);
}
