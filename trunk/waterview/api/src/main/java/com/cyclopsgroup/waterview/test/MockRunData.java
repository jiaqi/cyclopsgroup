package com.cyclopsgroup.waterview.test;

import com.cyclopsgroup.waterview.AbstractRunData;
import com.cyclopsgroup.waterview.spi.Page;
import com.cyclopsgroup.waterview.spi.RunDataSpi;

class MockRunData
    extends AbstractRunData
    implements RunDataSpi
{
    private static final String MIME_TYPE = "text/html";

    private Page page = new Page();

    /**
     * @see com.cyclopsgroup.waterview.RunData#getMimeType(java.lang.String)
     */
    public String getMimeType( String fileName )
    {
        return MIME_TYPE;
    }

    /**
     * @see com.cyclopsgroup.waterview.RunData#setOutputContentType(java.lang.String)
     */
    public void setOutputContentType( String contentType )
    {
    }

    /**
     * @see com.cyclopsgroup.waterview.RunData#setPage(java.lang.String)
     */
    public void setPage( String page )
        throws Exception
    {
    }

    /**
     * @see com.cyclopsgroup.waterview.spi.RunDataSpi#getPageObject()
     */
    public Page getPageObject()
    {
        return page;
    }

    /**
     * @see com.cyclopsgroup.waterview.spi.RunDataSpi#setPageObject(com.cyclopsgroup.waterview.spi.Page)
     */
    public void setPageObject( Page page )
    {
        this.page = page;
    }
}