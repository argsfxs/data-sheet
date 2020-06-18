package elements;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class FunctionTest
{

    @Test
    public void testCount()
    {
        DataSheet ds = new DataSheet( 5 );
        ds.setRow( 0, Arrays.asList( 1, 2, "", "   ", null ) );
        ds.setRow( 1, Arrays.asList( null, "", "   ", "", "" ) );
        ds.setRow( 2, Arrays.asList( 1, 2, 3, 4, 5 ) );

        Assert.assertEquals( 7, ds.count() );
        Assert.assertEquals( 25, ds.size() );

        Assert.assertEquals( 2, ds.getRow( 0 ).count() );
        Assert.assertEquals( 5, ds.getRow( 0 ).size() );

        Assert.assertEquals( 0, ds.getRow( 1 ).count() );
        Assert.assertEquals( 5, ds.getRow( 1 ).size() );

        Assert.assertEquals( 5, ds.getRow( 2 ).count() );
        Assert.assertEquals( 5, ds.getRow( 2 ).size() );
    }
}
