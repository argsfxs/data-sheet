package elements;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class CellTypeTest
{
    private elements.DataSheet ds;

    @Before
    public void setUp()
    {
        ds = new elements.DataSheet();
    }

    @Test
    public void testString()
    {
        ds.setRow( 0, Arrays.asList( "true", "123", 123, 0b1001, 0xff ) );
        String value1 = ds.getCell( 0, 0 ).getValue();
        String value2 = ds.getCell( 1, 0 ).getValue();
        String value3 = ds.getCell( 2, 0 ).getValue();
        String value4 = ds.getCell( 3, 0 ).getValue();
        String value5 = ds.getCell( 4, 0 ).getValue();
        Assert.assertEquals( "true", value1 );
        Assert.assertEquals( "123", value2 );
        Assert.assertEquals( "123", value3 );
        Assert.assertEquals( "9", value4 );
        Assert.assertEquals( "255", value5 );
    }

    @Test
    public void testBoolean()
    {
        ds.setRow( 1, Arrays.asList( "true", "TRUE", "1", "0", "false", "FALSE", "qwertz", 123 ) );
        elements.Cell cell1 = ds.getCell( 0, 1 );
        elements.Cell cell2 = ds.getCell( 1, 1 );
        elements.Cell cell3 = ds.getCell( 2, 1 );
        elements.Cell cell4 = ds.getCell( 3, 1 );
        elements.Cell cell5 = ds.getCell( 4, 1 );
        elements.Cell cell6 = ds.getCell( 5, 1 );
        elements.Cell cell7 = ds.getCell( 6, 1 );
        Assert.assertTrue( cell1.getValueAsBoolean() );
        Assert.assertTrue( cell2.getValueAsBoolean() );
        Assert.assertTrue( cell3.getValueAsBoolean() );
        Assert.assertFalse( cell4.getValueAsBoolean() );
        Assert.assertFalse( cell5.getValueAsBoolean() );
        Assert.assertFalse( cell6.getValueAsBoolean() );
        Assert.assertFalse( cell7.getValueAsBoolean() );
    }

    @Test
    public void testInt()
    {
        ds.setRow( 2, Arrays.asList( "true", "TRUE", "1", "0", "123", "123.0", "qwertz", 123 ) );
        int value1 = ds.getCell( 0, 2 ).getValueAsInt();
        int value2 = ds.getCell( 1, 2 ).getValueAsInt();
        int value3 = ds.getCell( 2, 2 ).getValueAsInt();
        int value4 = ds.getCell( 3, 2 ).getValueAsInt();
        int value5 = ds.getCell( 4, 2 ).getValueAsInt();
        int value6 = ds.getCell( 5, 2 ).getValueAsInt();
        int value7 = ds.getCell( 6, 2 ).getValueAsInt();
        int value8 = ds.getCell( 7, 2 ).getValueAsInt();
        Assert.assertEquals( 1, value1 );
        Assert.assertEquals( 1, value2 );
        Assert.assertEquals( 1, value3 );
        Assert.assertEquals( 0, value4 );
        Assert.assertEquals( 123, value5 );
        Assert.assertEquals( 0, value6 );
        Assert.assertEquals( 0, value7 );
        Assert.assertEquals( 123, value8 );
    }

    @Test
    public void testDouble()
    {
        ds.setRow( 3, Arrays.asList( "true", "TRUE", "1", "0", "123", "123.0", "qwertz", 123.54 ) );
        double value1 = ds.getCell( 0, 3 ).getValueAsDouble();
        double value2 = ds.getCell( 1, 3 ).getValueAsDouble();
        double value3 = ds.getCell( 2, 3 ).getValueAsDouble();
        double value4 = ds.getCell( 3, 3 ).getValueAsDouble();
        double value5 = ds.getCell( 4, 3 ).getValueAsDouble();
        double value6 = ds.getCell( 5, 3 ).getValueAsDouble();
        double value7 = ds.getCell( 6, 3 ).getValueAsDouble();
        double value8 = ds.getCell( 7, 3 ).getValueAsDouble();
        Assert.assertEquals( 1.0, value1, 0 );
        Assert.assertEquals( 1.0, value2, 0 );
        Assert.assertEquals( 1.0, value3, 0 );
        Assert.assertEquals( 0.0, value4, 0 );
        Assert.assertEquals( 123.0, value5, 0 );
        Assert.assertEquals( 123.0, value6, 0 );
        Assert.assertEquals( 0.0, value7, 0 );
        Assert.assertEquals( 123.54, value8, 0 );
    }
}
