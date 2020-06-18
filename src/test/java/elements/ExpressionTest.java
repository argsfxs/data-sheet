package elements;

import org.junit.Assert;
import org.junit.Test;
import type.Operation;

import java.util.Arrays;

public class ExpressionTest
{
    @Test
    public void test()
    {
        DataSheet ds = new DataSheet();
        ds.setRow( 0, Arrays.asList( 123, 456, 789 ) );
        ds.setValue( 3, 0, Operation.SUM, 0, 0, 2, 0 );
        Record row = ds.getRow( 0 );
        Assert.assertEquals( "123,456,789,1368.0", row.toString() );
        ds.setValue( 0, 0, 1000 );
        Assert.assertEquals( "1000,456,789,2245.0", row.toString() );
        ds.setValue( 3, 0, Operation.AVG, 0, 0, 2, 0 );
        Assert.assertEquals( "1000,456,789,748.3333333333334", row.toString() );
        ds.setValue( 3, 0, Operation.COUNT, 0, 0, 2, 0 );
        Assert.assertEquals( "1000,456,789,3", row.toString() );
        ds.setValue( 3, 0, "foo" );
        Assert.assertEquals( "1000,456,789,foo", row.toString() );
    }
}
