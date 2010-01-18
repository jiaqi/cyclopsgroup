package org.cyclopsgroup.jcli.impl;

import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.ArgumentProcessorFactory;
import org.cyclopsgroup.jcli.spi.CommandLineParser;

public class DefaultArgumentProcessorFactory
    extends ArgumentProcessorFactory
{
    /**
     * @inheritDoc
     */
    @Override
    protected <T> ArgumentProcessor<T> newProcessor( Class<T> beanType, CommandLineParser parser )
    {
        return new DefaultArgumentProcessor<T>( beanType, parser );
    }
}
