package com.cyclopsgroup.waterview.spi;

import com.cyclopsgroup.waterview.RunData;

/**
 * Implementation side RunData interface
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public interface RunDataSpi
    extends RunData
{
    /**
     * Get page object
     *
     * @return Page object
     */
    Page getPageObject();

    /**
     * Set page object
     *
     * @param page Page object
     */
    void setPageObject( Page page );
}
