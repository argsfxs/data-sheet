package elements;

import io.CSVReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ColumnTest
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
        System.out.println( "TEST 1 - Adding one column to the right with correct length." );
        ds.addColumn( Arrays
                .asList( "TEST1", "TEST1", "TEST1", "TEST1", "TEST1", "TEST1", "TEST1", "TEST1", "TEST1", "TEST1", "TEST1" ) );
        elements.Record column = ds.getColumn( ds.columnCount() );
        Assert.assertEquals( "TEST1,TEST1,TEST1,TEST1,TEST1,TEST1,TEST1,TEST1,TEST1,TEST1,TEST1", column
                .toString() );
        Assert.assertEquals( 11, ds.rowCount() );
        Assert.assertEquals( 7, ds.columnCount() );
        Assert.assertEquals( 77, ds.size() );

        System.out.println( "TEST 2 - Adding one column between Capital and Population with correct length." );
        ds.addColumn( 2, Arrays
                .asList( "TEST2", "TEST2", "TEST2", "TEST2", "TEST2", "TEST2", "TEST2", "TEST2", "TEST2", "TEST2", "TEST2" ) );
        elements.Record capitals = ds.getColumn( 1 );
        elements.Record inserted = ds.getColumn( 2 );
        elements.Record population = ds.getColumn( 3 );
        Assert.assertEquals( "Capital,Berlin,Vienna,Stockholm,Moscow,Asunción,Tokyo,Canberra,Brasília,Seoul,Doha", capitals
                .toString() );
        Assert.assertEquals( "TEST2,TEST2,TEST2,TEST2,TEST2,TEST2,TEST2,TEST2,TEST2,TEST2,TEST2", inserted.toString() );
        Assert.assertEquals( "Population,83149300,8858775,10327589,144526636,6943739,126860000,25324713,208360000,51629512,2700000", population
                .toString() );
        Assert.assertEquals( 11, ds.rowCount() );
        Assert.assertEquals( 8, ds.columnCount() );
        Assert.assertEquals( 88, ds.size() );

        System.out.println( "TEST 3 - Adding a shorter column at the end (3 elements)." );
        ds.addColumn( Arrays.asList( "TEST3", "TEST3", "TEST3" ) );
        column = ds.getColumn( ds.columnCount() );
        Assert.assertEquals( "TEST3,TEST3,TEST3", column
                .toString() );
        Assert.assertEquals( 11, ds.rowCount() );
        Assert.assertEquals( 9, ds.columnCount() );
        Assert.assertEquals( 99, ds.size() );

        System.out.println( "TEST 4 - Adding a longer column at the end (12 elements)." );
        ds.addColumn( Arrays
                .asList( "TEST4", "TEST4", "TEST4", "TEST4", "TEST4", "TEST4", "TEST4", "TEST4", "TEST4", "TEST4", "TEST4", "TEST4" ) );
        column = ds.getColumn( ds.columnCount() );
        Assert.assertEquals( "TEST4,TEST4,TEST4,TEST4,TEST4,TEST4,TEST4,TEST4,TEST4,TEST4,TEST4,TEST4", column
                .toString() );
        Assert.assertEquals( 12, ds.rowCount() );
        Assert.assertEquals( 10, ds.columnCount() );
        Assert.assertEquals( 120, ds.size() );

        System.out.println( "TEST 5 - Adding a shorter column in the middle (3 elements)." );
        ds.addColumn( 4, Arrays.asList( "TEST5", "TEST5", "TEST5" ) );
        population = ds.getColumn( 3 );
        inserted = ds.getColumn( 4 );
        elements.Record forest = ds.getColumn( 5 );
        Assert.assertEquals( "Population,83149300,8858775,10327589,144526636,6943739,126860000,25324713,208360000,51629512,2700000", population
                .toString() );
        Assert.assertEquals( "TEST5,TEST5,TEST5", inserted.toString() );
        Assert.assertEquals( "Forest Area,0.32,0.462,0.57,0.4976,0.443,0.67,0.17,0.624,0.6335,0", forest.toString() );
        Assert.assertEquals( 12, ds.rowCount() );
        Assert.assertEquals( 11, ds.columnCount() );
        Assert.assertEquals( 132, ds.size() );

        System.out.println( "TEST 6 - Adding a longer column at the middle (15 elements)." );
        ds.addColumn( 4, Arrays
                .asList( "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6", "TEST6" ) );
        population = ds.getColumn( 3 );
        elements.Record justInserted = ds.getColumn( 4 );
        inserted = ds.getColumn( 5 );
        Assert.assertEquals( "Population,83149300,8858775,10327589,144526636,6943739,126860000,25324713,208360000,51629512,2700000", population
                .toString() );
        Assert.assertEquals( "TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6,TEST6", justInserted
                .toString() );
        Assert.assertEquals( "TEST5,TEST5,TEST5", inserted.toString() );
        Assert.assertEquals( 15, ds.rowCount() );
        Assert.assertEquals( 12, ds.columnCount() );
        Assert.assertEquals( 180, ds.size() );
    }

    @Test
    public void testGet()
    {
        elements.Record column = ds.getColumn( 0 );
        Assert.assertEquals( "Name,Germany,Austria,Sweden,Russia,Paraguay,Japan,Australia,Brazil,South Korea,Qatar", column
                .toString() );
    }

    @Test
    public void testSet()
    {
        List<String> elements = Arrays
                .asList( "foo", "bar", "baz", "123", "456", "789", "987", "654", "321", "foo", "bar" );
        ds.setColumn( 0, elements );
        elements.Record column = ds.getColumn( 0 );
        Assert.assertEquals( "foo,bar,baz,123,456,789,987,654,321,foo,bar", column.toString() );
    }

    @Test
    public void testRemove()
    {
        ds.removeColumn( 0 );
        elements.Record column = ds.getColumn( 0 );
        Assert.assertEquals( "Capital,Berlin,Vienna,Stockholm,Moscow,Asunción,Tokyo,Canberra,Brasília,Seoul,Doha", column
                .toString() );
    }

    @Test
    public void testColumnIndexTooLarge()
    {
        System.out.println( "TEST 2 - Index > columncount()" );
        elements.Record column = ds.getColumn( 100 );
        Assert.assertEquals( "Seashore?,true,false,true,true,false,true,true,true,true,true", column.toString() );

        System.out.println( "TEST 2 - Index < 0" );
        column = ds.getColumn( -1 );
        Assert.assertEquals( "Name,Germany,Austria,Sweden,Russia,Paraguay,Japan,Australia,Brazil,South Korea,Qatar", column
                .toString() );
    }
}
