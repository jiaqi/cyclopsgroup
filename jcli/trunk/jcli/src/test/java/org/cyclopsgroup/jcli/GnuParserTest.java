package org.cyclopsgroup.jcli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Verify the correctness of GNU argument parser
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class GnuParserTest
{
    /**
     * Verify use cases with all possible types of arguments
     */
    @Test
    public void testCombination()
    {
        ArgumentProcessor<ExampleNormalBean> p =
            ArgumentProcessor.newInstance( ExampleNormalBean.class, new GnuParser() );
        ExampleNormalBean b = new ExampleNormalBean();
        p.process( new String[] { "-i", "123", "a", "-f", "abc", "b" }, b );

        assertEquals( 123, b.getIntField() );
        assertEquals( "abc", b.getStringField1() );
        assertEquals( Arrays.asList( "a", "b" ), b.getValues() );
        assertFalse( b.isBooleanField() );
    }

    /**
     * Verify empty arguments doesn't reset default arguments
     */
    @Test
    public void testNormalBeanWithoutValues()
    {
        ArgumentProcessor<ExampleNormalBean> p =
            ArgumentProcessor.newInstance( ExampleNormalBean.class, new GnuParser() );
        ExampleNormalBean b = new ExampleNormalBean();
        p.process( new String[] { "-i", "123", "--field1", "abc" }, b );

        assertEquals( 123, b.getIntField() );
        assertEquals( "abc", b.getStringField1() );
        assertEquals( new ArrayList<String>(), b.getValues() );
        assertFalse( b.isBooleanField() );
    }

    /**
     * Verify simple argument can be handled correctly
     */
    @Test
    public void testSimpleArgumentWithMultiValues()
    {
        ArgumentProcessor<ExampleBeanWithSimpleArgument> p =
            ArgumentProcessor.newInstance( ExampleBeanWithSimpleArgument.class, new GnuParser() );
        ExampleBeanWithSimpleArgument b = new ExampleBeanWithSimpleArgument();
        p.process( new String[] { "a", "b" }, b );
        assertEquals( "a", b.getArg() );
    }

    /**
     * Verify simple argument without value can be handled correctly
     */
    @Test
    public void testSimpleArgumentWithoutValue()
    {
        ArgumentProcessor<ExampleBeanWithSimpleArgument> p =
            ArgumentProcessor.newInstance( ExampleBeanWithSimpleArgument.class, new GnuParser() );
        ExampleBeanWithSimpleArgument b = new ExampleBeanWithSimpleArgument();
        p.process( new String[] {}, b );
        assertNull( b.getArg() );
    }

    /**
     * Verify boolean is handled correctly
     */
    @Test
    public void testWithFlag()
    {
        ArgumentProcessor<ExampleNormalBean> p =
            ArgumentProcessor.newInstance( ExampleNormalBean.class, new GnuParser() );
        ExampleNormalBean b = new ExampleNormalBean();
        p.process( new String[] { "-b", "123" }, b );
        assertTrue( b.isBooleanField() );
    }

    /**
     * Verify multi value option is handled correctly
     */
    @Test
    public void testWithMultiValueOption()
    {
        ArgumentProcessor<ExampleBeanWithMultiValueOption> p =
            ArgumentProcessor.newInstance( ExampleBeanWithMultiValueOption.class, new GnuParser() );
        ExampleBeanWithMultiValueOption b = new ExampleBeanWithMultiValueOption();
        p.process( new String[] { "a", "-o", "b", "-o", "c", "d", "--option", "e" }, b );
        assertEquals( Arrays.asList( "b", "c", "e" ), b.getOptions() );
    }

    /**
     * Verify negative numbers are accepted
     */
    @Test
    public void testWithNegativeNumbers()
    {
        ArgumentProcessor<ExampleNormalBean> p =
            ArgumentProcessor.newInstance( ExampleNormalBean.class, new GnuParser() );
        ExampleNormalBean b = new ExampleNormalBean();
        p.process( new String[] { "-1", "--1", "-2", "-3", "-4" }, b );
        assertEquals( "-3", b.getStringFIeld2() );
        assertEquals( Arrays.asList( "-1", "--1", "-4" ), b.getValues() );
    }
}
