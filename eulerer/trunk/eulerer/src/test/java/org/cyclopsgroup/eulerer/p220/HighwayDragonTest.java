package org.cyclopsgroup.eulerer.p220;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class HighwayDragonTest
{
    private void traverseAndVerify( int level, long steps, String expected )
    {
        HighwayDragon d = new HighwayDragon( level );
        d.traverseFor( steps );
        assertEquals( expected, d.position().toString() );
    }

    @Test
    @Ignore
    public void testWith10Levels()
    {
        traverseAndVerify( 10, 500, "18:16" );
    }

    @Test
    public void testWith1Level()
    {
        traverseAndVerify( 0, 1, "0:1" );
    }

    @Test
    public void testWithLevel2()
    {
        traverseAndVerify( 1, 2, "1:1" );
    }
}
