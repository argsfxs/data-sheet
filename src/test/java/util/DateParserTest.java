package util;

import type.CellType;
import elements.DataSheet;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class DateParserTest
{
    @Test
    public void testDifferentFormats()
    {
        DataSheet ds = new DataSheet( 15 );
        ds.setValue( 0, 0, "1" );
        ds.setValue( 1, 0, "11" );
        ds.setValue( 2, 0, "111" );//111
        ds.setValue( 3, 0, "9999" );//1999-09-09
        ds.setValue( 4, 0, "1111" );//2011-01-01
        ds.setValue( 5, 0, "11117" );//11117
        ds.setValue( 6, 0, "010115" );//2015-01-01
        ds.setValue( 7, 0, "112018" );//2018-01-01
        ds.setValue( 8, 0, "06062008" );//2008-06-06
        ds.setValue( 9, 0, "50" );//50
        ds.setValue( 10, 0, "1112017" );//1112017
        ds.setCellTypeForRow( 0, CellType.DATE );
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        String month = ( ( currentDate.getMonthValue() < 10 ) ? "0" : "" ) + currentDate.getMonthValue();
        String first = String.format( "%s-%s-01", year, month );
        String second = String.format( "%s-%s-11", year, month );
        String expected = first + "," + second + ",111,1999-09-09,2011-01-01,11117,2015-01-01,2018-01-01,2008-06-06,50,1112017";
        Assert.assertEquals( expected, ds.getRow( 0 ).toString() );
    }
}
