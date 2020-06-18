package elements;

import io.CSVReader;
import org.junit.Assert;
import org.junit.Test;
import type.SortOrder;

import java.util.LinkedHashMap;

public class SortingTest
{
    @Test
    public void testSameOrderForAllColumns()
    {
        CSVReader reader = new CSVReader( "src/test/resources/data.csv" );
        DataSheet ds = new DataSheet( reader );
        System.out.println( "TEST 1 - Sort ascending by seashore and country name." );
        ds.sortAscending( 5, 0 );
        Record header = ds.getRow( 0 );
        Record austria = ds.getRow( 1 );
        Record paraguay = ds.getRow( 2 );
        Record australia = ds.getRow( 3 );
        Record sweden = ds.getRow( 10 );
        Assert.assertEquals( "Name", header.get( 0 ).toString() );
        Assert.assertEquals( "Austria", austria.get( 0 ).toString() );
        Assert.assertEquals( "Paraguay", paraguay.get( 0 ).toString() );
        Assert.assertEquals( "Australia", australia.get( 0 ).toString() );
        Assert.assertEquals( "Sweden", sweden.get( 0 ).toString() );

        System.out.println( "TEST 2 - Integers are sorted by numeric value." );
        ds.sortAscending( 5, 4 );
        Record russia = ds.getRow( 4 );
        Assert.assertEquals( "Russia", russia.get( 0 ).toString() );

        System.out.println( "TEST 3 - Sort descending by seashore and forest area." );
        ds.sortDescending( 5, 3 );
        Record japan = ds.getRow( 0 );
        sweden = ds.getRow( 3 );
        austria = ds.getRow( 8 );
        paraguay = ds.getRow( 9 );
        header = ds.getRow( 10 );
        Assert.assertEquals( "Japan", japan.get( 0 ).toString() );
        Assert.assertEquals( "Sweden", sweden.get( 0 ).toString() );
        Assert.assertEquals( "Austria", austria.get( 0 ).toString() );
        Assert.assertEquals( "Paraguay", paraguay.get( 0 ).toString() );
        Assert.assertEquals( "Name", header.get( 0 ).toString() );
    }

    @Test
    public void testDifferentOrder()
    {
        CSVReader reader = new CSVReader( "src/test/resources/data.csv" );
        DataSheet ds = new DataSheet( reader );
        LinkedHashMap<Integer, SortOrder> map = new LinkedHashMap<>();
        System.out.println( "TEST 1 - Sorting by seashore DESC and name ASC." );
        map.put( 5, SortOrder.DESC );
        map.put( 0, SortOrder.ASC );
        ds.sort( map );
        Record row = ds.getRow( 0 );
        Record header = ds.getRow( 10 );
        Assert.assertEquals( "Australia", row.get( 0 ).toString() );
        Assert.assertEquals( "Name", header.get( 0 ).toString() );

        System.out.println( "TEST 2 - Sorting by seashore DESC and capital ASC." );
        map.clear();
        map.put( 5, SortOrder.DESC );
        map.put( 1, SortOrder.ASC );
        ds.sort( map );
        row = ds.getRow( 0 );
        header = ds.getRow( 10 );
        Assert.assertEquals( "Berlin", row.get( 1 ).toString() );
        Assert.assertEquals( "Name", header.get( 0 ).toString() );

        System.out.println( "TEST 2 - Sorting by seashore ASC and capital DESC." );
        map.clear();
        map.put( 5, SortOrder.ASC );
        map.put( 0, SortOrder.DESC );
        ds.sort( map );
        row = ds.getRow( 1 );
        header = ds.getRow( 0 );
        Assert.assertEquals( "Paraguay", row.get( 0 ).toString() );
        Assert.assertEquals( "Name", header.get( 0 ).toString() );

        System.out.println( "TEST 3 - Sorting by seashore ASC and GDP DESC." );
        map.clear();
        map.put( 5, SortOrder.ASC );
        map.put( 4, SortOrder.DESC );
        ds.sort( map );
        row = ds.getRow( 1 );
        Record row2 = ds.getRow( 3 );
        header = ds.getRow( 0 );
        Assert.assertEquals( "Austria", row.get( 0 ).toString() );
        Assert.assertEquals( "Qatar", row2.get( 0 ).toString() );
        Assert.assertEquals( "Name", header.get( 0 ).toString() );
    }

    @Test
    public void testSingleSortAndMultiSortHaveSameResult()
    {
        CSVReader reader = new CSVReader( "src/test/resources/data.csv" );
        DataSheet ds = new DataSheet( reader );
        ds.sortDescending( 5, 3 );
        String name1 = ds.getRow( 0 ).get( 0 ).toString();

        ds = new DataSheet( reader );
        LinkedHashMap<Integer, SortOrder> map = new LinkedHashMap<>();
        map.put( 5, SortOrder.DESC );
        map.put( 3, SortOrder.DESC );
        ds.sort( map );
        String name2 = ds.getRow( 0 ).get( 0 ).toString();

        Assert.assertEquals( name1, name2 );
        Assert.assertEquals( "Japan", name1 );
    }

    @Test
    public void testIndexOutOfBoundsWillNotSort()
    {
        CSVReader reader = new CSVReader( "src/test/resources/data.csv" );
        DataSheet ds = new DataSheet( reader );
        String expected =
                "      Name |    Capital | Population | Forest ... |        GDP |  Seashore?\n" +
                        "   Germany |     Berlin |   83149300 |       0.32 |      52556 |       true\n" +
                        "   Austria |     Vienna |    8858775 |      0.462 |      53879 |      false\n" +
                        "    Sweden |  Stockholm |   10327589 |       0.57 |      51405 |       true\n" +
                        "    Russia |     Moscow |  144526636 |     0.4976 |      25763 |       true\n" +
                        "  Paraguay |   Asunción |    6943739 |      0.443 |      13109 |      false\n" +
                        "     Japan |      Tokyo |  126860000 |       0.67 |      42067 |       true\n" +
                        " Australia |   Canberra |   25324713 |       0.17 |      49378 |       true\n" +
                        "    Brazil |   Brasília |  208360000 |      0.624 |      15553 |       true\n" +
                        "South K... |      Seoul |   51629512 |     0.6335 |      38824 |       true\n" +
                        "     Qatar |       Doha |    2700000 |          0 |     128647 |       true\n";
        ds.sortAscending( 100 );
        Assert.assertEquals( expected, ds.toString() );
        ds.sortAscending( 0, 100 );
        Assert.assertEquals( expected, ds.toString() );
        ds.sortAscending( 100, 0 );
        Assert.assertEquals( expected, ds.toString() );
        ds.sortAscending( 0, -1 );
        Assert.assertEquals( expected, ds.toString() );
        ds.sortDescending( 100 );
        Assert.assertEquals( expected, ds.toString() );
        ds.sortDescending( 0, 100 );
        Assert.assertEquals( expected, ds.toString() );
        ds.sortDescending( 100, 0 );
        Assert.assertEquals( expected, ds.toString() );
        ds.sortDescending( 0, -1 );
        Assert.assertEquals( expected, ds.toString() );
    }

}
