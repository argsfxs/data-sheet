package elements;

import io.CSVReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

public class ColumnNamesTest
{
    @Test
    public void test() {
        DataSheet ds = new DataSheet(new CSVReader( "src/test/resources/simple.csv" ).withColumnNames( true ));

        System.out.println( "TEST 1 - Add columns/rows." );
        String expected =
                "[    Name] | [ Capital] | [Popul...] | [Seash...] | [    test] | [  COLUMN] | [  COLUMN] | [  COLUMN] | [   first]\n" +
                        "   Germany |     Berlin |   83149300 |       true |        123 |            |            |            |        987\n" +
                        "   Austria |     Vienna |    8858775 |      false |        456 |            |            |            |        654\n" +
                        "           |            |            |            |        789 |            |            |            |        321\n" +
                        "           |            |            |            |        666 |            |            |            |        123\n" +
                        "       foo |        bar |        baz |       foo1 |       bar2 |       baz2 |       foo3 |       bar3 |        456\n" +
                        "           |            |            |            |            |            |            |            |        789\n" +
                        "           |            |            |            |            |            |            |            |        987\n";
        ds.addColumn( new LinkedList<>( Arrays.asList( "Seashore?", true, false )) );
        ds.addColumn( Arrays.asList( "test", 123,456,789,666 ) );
        ds.addRow( Arrays.asList( "foo", "bar", "baz", "foo1", "bar2", "baz2", "foo3", "bar3" ) );
        ds.addColumn( Arrays.asList( "first",987,654,321,123,456,789,987 ) );
        Assert.assertEquals( expected, ds.toString() );

        System.out.println( "TEST 2 - Set column names." );
        expected =
                "[    Name] | [ Capital] | [Popul...] | [Seash...] | [    test] | [   test1] | [   test2] | [   test3] | [   first]\n" +
                "   Germany |     Berlin |   83149300 |       true |        123 |            |            |            |        987\n" +
                "   Austria |     Vienna |    8858775 |      false |        456 |            |            |            |        654\n" +
                "           |            |            |            |        789 |            |            |            |        321\n" +
                "           |            |            |            |        666 |            |            |            |        123\n" +
                "       foo |        bar |        baz |       foo1 |       bar2 |       baz2 |       foo3 |       bar3 |        456\n" +
                "           |            |            |            |            |            |            |            |        789\n" +
                "           |            |            |            |            |            |            |            |        987\n";
        ds.setColumnName( 5, "test1" );
        ds.setColumnName( 6, "test2" );
        ds.setColumnName( 7, "test3" );
        Assert.assertEquals( expected, ds.toString() );

        System.out.println( "TEST 3 - Get column by name." );
        Record name = ds.getColumn( "Name" );
        Record sea = ds.getColumn( "Seashore?" );
        Record first = ds.getColumn( "first" );
        Assert.assertEquals( "Germany,Austria,foo", name.toString() );
        Assert.assertEquals( "true,false,foo1", sea.toString() );
        Assert.assertEquals( "987,654,321,123,456,789,987", first.toString() );

        System.out.println( "TEST 4 - Unknown column name returns null." );
        Assert.assertNull( ds.getColumn( "foo" ) );
    }
}
