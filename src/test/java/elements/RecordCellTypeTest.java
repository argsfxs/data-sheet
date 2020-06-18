package elements;

import io.CSVReader;
import org.junit.Assert;
import org.junit.Test;
import type.CellType;

import java.util.Arrays;

public class RecordCellTypeTest
{
    @Test
    public void testColumnType()
    {
        CSVReader reader = new CSVReader( "src/test/resources/data.csv" );
        DataSheet ds = new DataSheet( reader );
        Assert.assertEquals( CellType.TEXT, ds.getColumn( 2 ).getCellType() );
        Assert.assertEquals( CellType.TEXT, ds.getColumn( 3 ).getCellType() );
        Assert.assertEquals( CellType.TEXT, ds.getColumn( 4 ).getCellType() );
        ds.setCellTypeForColumn( 2, CellType.NUMBER );
        ds.setCellTypeForColumn( 3, CellType.PERCENTAGE );
        ds.setCellTypeForColumn( 4, CellType.CURRENCY );
        Assert.assertEquals( "Population,\"83149300,00\",\"8858775,00\",\"10327589,00\",\"144526636,00\",\"6943739,00\",\"126860000,00\",\"25324713,00\",\"208360000,00\",\"51629512,00\",\"2700000,00\"", ds
                .getColumn( 2 ).toString() );
        Assert.assertEquals( "Forest Area,\"32,00 %\",\"46,20 %\",\"57,00 %\",\"49,76 %\",\"44,30 %\",\"67,00 %\",\"17,00 %\",\"62,40 %\",\"63,35 %\",\"0,00 %\"", ds
                .getColumn( 3 ).toString() );
        Assert.assertEquals( "GDP,\"52556,00 €\",\"53879,00 €\",\"51405,00 €\",\"25763,00 €\",\"13109,00 €\",\"42067,00 €\",\"49378,00 €\",\"15553,00 €\",\"38824,00 €\",\"128647,00 €\"", ds
                .getColumn( 4 ).toString() );
        Assert.assertEquals( CellType.NUMBER, ds.getColumn( 2 ).getCellType() );
        Assert.assertEquals( CellType.PERCENTAGE, ds.getColumn( 3 ).getCellType() );
        Assert.assertEquals( CellType.CURRENCY, ds.getColumn( 4 ).getCellType() );
        ;
    }

    @Test
    public void testRowType()
    {
        DataSheet ds = new DataSheet();
        ds.addRow( 0, Arrays.asList( 123, 456, 789, 987, 654, 321, 123, 456, 789, 565 ) );
        ds.addRow( 0, Arrays.asList( 123, 456, 789, 987, 654, 321, 123, 456, 789, 565 ) );
        ds.addRow( 0, Arrays.asList( 0.123, 0.456, 0.789, 0.987, 0.654, 0.321, 0.123, 0.456, 0.789, 0.565 ) );
        Assert.assertEquals( "0.123,0.456,0.789,0.987,0.654,0.321,0.123,0.456,0.789,0.565", ds.getRow( 0 ).toString() );
        Assert.assertEquals( "123,456,789,987,654,321,123,456,789,565", ds.getRow( 1 ).toString() );
        Assert.assertEquals( "123,456,789,987,654,321,123,456,789,565", ds.getRow( 2 ).toString() );
        Assert.assertEquals( CellType.TEXT, ds.getRow( 0 ).getCellType() );
        Assert.assertEquals( CellType.TEXT, ds.getRow( 1 ).getCellType() );
        Assert.assertEquals( CellType.TEXT, ds.getRow( 2 ).getCellType() );
        ds.setCellTypeForRow( 0, CellType.PERCENTAGE );
        ds.setCellTypeForRow( 1, CellType.NUMBER );
        ds.setCellTypeForRow( 2, CellType.CURRENCY );
        Assert.assertEquals( "\"12,30 %\",\"45,60 %\",\"78,90 %\",\"98,70 %\",\"65,40 %\",\"32,10 %\",\"12,30 %\",\"45,60 %\",\"78,90 %\",\"56,50 %\"", ds
                .getRow( 0 ).toString() );
        Assert.assertEquals( "\"123,00\",\"456,00\",\"789,00\",\"987,00\",\"654,00\",\"321,00\",\"123,00\",\"456,00\",\"789,00\",\"565,00\"", ds.getRow( 1 )
                                                                                                        .toString() );
        Assert.assertEquals( "\"123,00 €\",\"456,00 €\",\"789,00 €\",\"987,00 €\",\"654,00 €\",\"321,00 €\",\"123,00 €\",\"456,00 €\",\"789,00 €\",\"565,00 €\"", ds
                .getRow( 2 ).toString() );
        Assert.assertEquals( CellType.PERCENTAGE, ds.getRow( 0 ).getCellType() );
        Assert.assertEquals( CellType.NUMBER, ds.getRow( 1 ).getCellType() );
        Assert.assertEquals( CellType.CURRENCY, ds.getRow( 2 ).getCellType() );
    }
}
