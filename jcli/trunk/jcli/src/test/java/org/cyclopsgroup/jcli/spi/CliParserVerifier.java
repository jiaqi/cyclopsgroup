package org.cyclopsgroup.jcli.spi;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.cyclopsgroup.jcli.CliParser;
import org.cyclopsgroup.jcli.ExampleBeanWithMultiValueOption;
import org.cyclopsgroup.jcli.ExampleBeanWithSimpleArgument;
import org.cyclopsgroup.jcli.ExampleNormalBean;
import org.junit.Test;

/**
 * Base class to test all CliParser implementations. Extending classes automatically inherits all test cases.
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public abstract class CliParserVerifier
{
    /**
     * @return New instance of a CliParser
     */
    protected abstract CliParser createCliParser();

    /**
     * Normal test case
     * 
     * @throws IntrospectionException
     */
    @Test
    public void testParseNormally()
        throws IntrospectionException
    {
        CliParser parser = createCliParser();
        ExampleNormalBean b = new ExampleNormalBean();
        parser.parse( StringUtils.split( "--tint 34123 -f 00907 -b 3453 34 52345", ' ' ), b );
        assertEquals( "00907", b.getStringField1() );
        assertEquals( 34123, b.getIntField() );
        assertTrue( b.isBooleanField() );
        assertArrayEquals( new String[] { "3453", "34", "52345" }, b.getValues().toArray() );
    }

    /**
     * Test case with multi value option
     * 
     * @throws IntrospectionException
     */
    @Test
    public void testParseWithMultiValueOption()
        throws IntrospectionException
    {
        CliParser parser = createCliParser();
        ExampleBeanWithMultiValueOption bean = new ExampleBeanWithMultiValueOption();
        parser.parse( StringUtils.split( "-o aaa -o bbb -o ccc" ), bean );
        assertEquals( 3, bean.getOptions().size() );
    }

    /**
     * Test case with single value argument
     * 
     * @throws IntrospectionException
     */
    @Test
    public void testParseWithSingleArgument()
        throws IntrospectionException
    {
        CliParser parser = createCliParser();
        ExampleBeanWithSimpleArgument bean = new ExampleBeanWithSimpleArgument();
        parser.parse( StringUtils.split( "12345 23456", ' ' ), bean );
        assertEquals( "12345", bean.getArg() );
    }

    /**
     * Test case that prints out usage
     * 
     * @throws IntrospectionException
     */
    @Test
    public void testPrintHelp()
        throws IntrospectionException
    {
        CliParser parser = createCliParser();
        StringWriter stringOutput = new StringWriter();
        PrintWriter output = new PrintWriter( stringOutput );
        parser.printUsage( ExampleNormalBean.class, output );
        output.flush();
        String result = stringOutput.toString();
        assertTrue( result.indexOf( "sample [-2 <val>] [-b] -f <val> [-i <val>]" ) != -1 );
    }
}
