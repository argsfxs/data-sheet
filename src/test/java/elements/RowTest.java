package elements;

import io.CSVReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class RowTest
{
    private elements.DataSheet ds;

    @Before
    public void setUp()
    {
        CSVReader reader = new CSVReader( "src/test/resources/data.csv" );
        ds = new elements.DataSheet( reader );
    }

    @Test
    public void testAdd()
    {
        System.out.println( "TEST 1 - Adding one line at the end with correct length." );
        ds.addRow( Arrays.asList( "TEST1", "TEST1", "TEST1", "TEST1", "TEST1", "TEST1" ) );
        elements.Record row = ds.getRow( ds.rowCount() );
        Assert.assertEquals( "TEST1,TEST1,TEST1,TEST1,TEST1,TEST1", row.toString() );
        Assert.assertEquals( 12, ds.rowCount() );
        Assert.assertEquals( 6, ds.columnCount() );
        Assert.assertEquals( 72, ds.size() );

        System.out.println( "TEST 2 - Adding one line with correct length between Russia and Paraguay." );
        ds.addRow( 5, Arrays.asList( "TEST2", "TEST2", "TEST2", "TEST2", "TEST2", "TEST2" ) );
        elements.Record russia = ds.getRow( 4 );
        elements.Record inserted = ds.getRow( 5 );
        elements.Record paraguay = ds.getRow( 6 );
        Assert.assertEquals( "Russia,Moscow,144526636,0.4976,25763,true", russia.toString() );
        Assert.assertEquals( "TEST2,TEST2,TEST2,TEST2,TEST2,TEST2", inserted.toString() );
        Assert.assertEquals( "Paraguay,Asunción,6943739,0.443,13109,false", paraguay.toString() );
        Assert.assertEquals( 13, ds.rowCount() );
        Assert.assertEquals( 6, ds.columnCount() );
        Assert.assertEquals( 78, ds.size() );

        System.out.println( "TEST 3 - Adding a shorter line at the end (3 elements)." );
        ds.addRow( Arrays.asList( "TEST3", "TEST3", "TEST3" ) );
        row = ds.getRow( ds.rowCount() );
        Assert.assertEquals( "TEST3,TEST3,TEST3", row.toString() );
        Assert.assertEquals( 14, ds.rowCount() );
        Assert.assertEquals( 6, ds.columnCount() );
        Assert.assertEquals( 84, ds.size() );

        System.out.println( "TEST 4 - Adding a longer line at the end (8 elements)." );
        ds.addRow( Arrays.asList( "TEST4", "TEST4", "TEST4", "TEST4", "TEST4", "TEST4", "TEST4", "TEST4" ) );
        row = ds.getRow( ds.rowCount() );
        Assert.assertEquals( "TEST4,TEST4,TEST4,TEST4,TEST4,TEST4,TEST4,TEST4", row.toString() );
        Assert.assertEquals( 15, ds.rowCount() );
        Assert.assertEquals( 8, ds.columnCount() );
        Assert.assertEquals( 120, ds.size() );

        System.out.println( "TEST 5 - Adding a shorter line in the middle (3 elements)." );
        ds.addRow( 4, Arrays.asList( "TEST5", "TEST5", "TEST5" ) );
        elements.Record sweden = ds.getRow( 3 );
        inserted = ds.getRow( 4 );
        russia = ds.getRow( 5 );
        Assert.assertEquals( "Sweden,Stockholm,10327589,0.57,51405,true", sweden.toString() );
        Assert.assertEquals( "TEST5,TEST5,TEST5", inserted.toString() );
        Assert.assertEquals( "Russia,Moscow,144526636,0.4976,25763,true", russia.toString() );
        Assert.assertEquals( 16, ds.rowCount() );
        Assert.assertEquals( 8, ds.columnCount() );
        Assert.assertEquals( 128, ds.size() );

        System.out.println( "TEST 6 - Adding a longer line at the middle (10 elements)." );
        ds.addRow( 9, Arrays
                .asList( "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6" ) );
        elements.Record japan = ds.getRow( 8 );
        inserted = ds.getRow( 9 );
        elements.Record australia = ds.getRow( 10 );
        Assert.assertEquals( "Japan,Tokyo,126860000,0.67,42067,true", japan.toString() );
        Assert.assertEquals( "TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6", inserted
                .toString() );
        Assert.assertEquals( "Australia,Canberra,25324713,0.17,49378,true", australia.toString() );
        Assert.assertEquals( 17, ds.rowCount() );
        Assert.assertEquals( 10, ds.columnCount() );
        Assert.assertEquals( 170, ds.size() );

        System.out.println();
    }

    @Test
    public void testGet()
    {
        elements.Record row = ds.getRow( 0 );
        Assert.assertEquals( "Name,Capital,Population,Forest Area,GDP,Seashore?", row
                .toString() );
    }

    @Test
    public void testSet()
    {
        List<String> elements = Arrays
                .asList( "foo", "bar", "baz", "123", "456", "789", "987" );
        ds.setRow( 0, elements );
        elements.Record row = ds.getRow( 0 );
        Assert.assertEquals( "foo,bar,baz,123,456,789,987", row.toString() );
    }

    @Test
    public void testRemove()
    {
        ds.removeRow( 0 );
        elements.Record row = ds.getRow( 0 );
        Assert.assertEquals( "Germany,Berlin,83149300,0.32,52556,true", row
                .toString() );
    }

    @Test
    public void testRowIndexTooLarge()
    {
        System.out.println( "TEST 1 - Index > rowcount()" );
        elements.Record row = ds.getRow( 100 );
        Assert.assertEquals( "Qatar,Doha,2700000,0,128647,true", row.toString() );

        System.out.println( "TEST 2 - Index < 0" );
        row = ds.getRow( -1 );
        Assert.assertEquals( "Name,Capital,Population,Forest Area,GDP,Seashore?", row.toString() );
    }
}
