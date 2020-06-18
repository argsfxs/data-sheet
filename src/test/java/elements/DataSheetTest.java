package elements;

import io.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DataSheetTest
{
    @Test
    public void testDefaultConstructor()
    {
        elements.DataSheet ds = new elements.DataSheet();
        Assert.assertEquals( 10, ds.rowCount() );
        Assert.assertEquals( 10, ds.columnCount() );
        Assert.assertEquals( 100, ds.size() );
        Assert.assertEquals( StringUtils.EMPTY, ds.getRow( 0 ).toString() );
    }

    @Test
    public void testSetValue()
    {
        System.out.println( "TEST 1 - Set different cell types." );
        elements.DataSheet ds = new elements.DataSheet();
        ds.setValue( 0, 0, "Germany" );
        ds.setValue( 1, 0, "Berlin" );
        ds.setValue( 2, 0, 83149300 );
        ds.setValue( 3, 0, 0.32 );
        ds.setValue( 4, 0, 52.556 );
        ds.setValue( 5, 0, 0b1001 );
        ds.setValue( 6, 0, 0xaf );
        ds.setValue( 7, 0, 100L );
        ds.setValue( 8, 0, 'X' );
        ds.setValue( 9, 0, true );
        elements.Record row = ds.getRow( 0 );
        Assert.assertEquals( "Germany,Berlin,83149300,0.32,52.556,9,175,100,X,true", row.toString() );
        int hashCode = ds.hashCode();

        System.out.println( "TEST 2 - Trying to update cell with index out of bounds." );
        ds.setValue( 1000, 0, "test" );
        Assert.assertEquals( hashCode, ds.hashCode() );
        ds.setValue( 0, 1000, "test" );
        Assert.assertEquals( hashCode, ds.hashCode() );
        ds.setValue( 1000, 1000, "test" );
        Assert.assertEquals( hashCode, ds.hashCode() );
        ds.setValue( 0, 0, "test" );
        Assert.assertTrue( ds.hashCode() != hashCode );
    }

    @Test
    public void testSubframeSuccess()
    {
        elements.DataSheet ds = new elements.DataSheet( new CSVReader( "src/test/resources/data.csv" ) );
        System.out.println( "TEST 1 - Frame in the middle." );
        List<elements.Record> sub = ds.slice( 2, 2, 4, 4 );
        Assert.assertEquals( "[8858775,0.462,53879, 10327589,0.57,51405, 144526636,0.4976,25763]", sub.toString() );
        System.out.println( "TEST 2 - Frame from the beginning." );
        sub = ds.slice( 0, 0, 2, 2 );
        Assert.assertEquals( "[Name,Capital,Population, Germany,Berlin,83149300, Austria,Vienna,8858775]", sub
                .toString() );
    }

    @Test
    public void testSubframeError()
    {
        elements.DataSheet ds = new elements.DataSheet( new CSVReader( "src/test/resources/data.csv" ) );
        System.out.println( "TEST 1 - Column index out of bounds." );
        Assert.assertNull( ds.slice( 24, 2, 4, 4 ) );
        System.out.println( "TEST 2 - Row index out of bounds." );
        Assert.assertNull( ds.slice( 2, 2, 4, 42 ) );
        System.out.println( "TEST 3 - Column negative." );
        Assert.assertNull( ds.slice( -2, 2, 4, 4 ) );
        System.out.println( "TEST 4 - Row negative." );
        Assert.assertNull( ds.slice( 2, 2, 4, -4 ) );
        System.out.println( "TEST 5 - Columns equal." );
        Assert.assertNull( ds.slice( 2, 2, 2, 4 ) );
        System.out.println( "TEST 6 - Rows equal." );
        Assert.assertNull( ds.slice( 2, 4, 4, 4 ) );
    }
}
