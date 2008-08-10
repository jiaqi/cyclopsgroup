package org.cyclopsgroup.jcli.jccli;

import java.beans.IntrospectionException;

import org.apache.commons.cli.GnuParser;
import org.cyclopsgroup.jcli.annotation.CliParser;
import org.cyclopsgroup.jcli.spi.CliParserVerifier;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test case of {@link JakartaCommonsCliParser}
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class JakartaCommonsCliParserTest
    extends CliParserVerifier
{
    @Override
    protected CliParser createCliParser()
    {
        return new JakartaCommonsCliParser( new GnuParser() );
    }

    /**
     * @inheritDoc
     */
    @Override
    @Test
    @Ignore( "This test doesn't parse because apache commons-cli 1.1 doesn't know to parse multi value option" )
    public void testParseWithMultiValueOption()
        throws IntrospectionException
    {
        super.testParseWithMultiValueOption();
    }
}
