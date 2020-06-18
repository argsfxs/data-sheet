package elements;

import io.CSVReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class RecordTest
{
    private DataSheet ds;

    @Before
    public void setUp()
    {
        CSVReader reader = new CSVReader( "src/test/resources/data.csv" );
        ds = new DataSheet( reader );
    }

    @Test
    public void testHashCode()
    {
        System.out.println( "TEST 1 - Getting the same row twice and comparing it." );
        Record row1 = ds.getRow( 1 );
        Record row2 = ds.getRow( 1 );
        Assert.assertEquals( row1.hashCode(), row2.hashCode() );

        System.out.println( "TEST 2 - Create a new row with the same content and comparing it." );
        Record row3 = new Record( Arrays.asList( "Germany", "Berlin", 83149300, 0.32, 52556, true ) );
        Assert.assertEquals( row1.hashCode(), row3.hashCode() );

        System.out.println( "TEST 3 - Create a new row with same data in different order." );
        Record row4 = new Record( Arrays.asList( "Berlin", "Germany", 83149300, 0.32, 52556, true ) );
        Assert.assertTrue( row1.hashCode() != row4.hashCode() );
        Assert.assertTrue( row3.hashCode() != row4.hashCode() );

    }
}
