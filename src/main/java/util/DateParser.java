package util;

import org.apache.commons.lang3.math.NumberUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public final class DateParser
{
    private DateParser()
    {

    }

    public static String parse( String dateString )
    {
        if ( !NumberUtils.isParsable( dateString ) )
        {
            return dateString;
        }
        if ( dateString.length() == 3 || dateString.length() == 5 || dateString.length() == 7 )
        {
            return dateString;
        }
        switch ( dateString.length() )
        {
            case 1:
            case 2:
                return dayInCurrentMonth( dateString );
            case 4:
                if ( dateString.charAt( 0 ) == '0' )
                {
                    return dayAndMonthInCurrentYear( dateString );
                }
                else
                {
                    return fourDigitYear( dateString );
                }
            case 6:
                return dayMonthYear( dateString );
            case 8:
                return fullDate( dateString );
            default:
                return dateString;
        }
    }

    private static String fullDate( String dateString )
    {
        int day = Integer.parseInt( dateString.substring( 0, 2 ) );
        int month = Integer.parseInt( dateString.substring( 2, 4 ) );
        int year = Integer.parseInt( dateString.substring( 4 ) );
        LocalDate date = LocalDate.of( year, month, day );
        return date.toString();
    }

    private static String dayMonthYear( String dateString )
    {
        if ( dateString.charAt( 0 ) == '0' )
        {
            return twoDigitYear( dateString );
        }
        int day = Integer.parseInt( dateString.substring( 0, 1 ) );
        int month = Integer.parseInt( dateString.substring( 1, 2 ) );
        int year = Integer.parseInt( dateString.substring( 2 ) );
        LocalDate date = LocalDate.of( year, month, day );
        return date.toString();
    }

    private static String twoDigitYear( String dateString )
    {
        try
        {
            int day = Integer.parseInt( dateString.substring( 0, 2 ) );
            int month = Integer.parseInt( dateString.substring( 2, 4 ) );
            Date d = new SimpleDateFormat( "yy" ).parse( dateString.substring( 4 ) );
            String yearString = new SimpleDateFormat( "yyyy" ).format( d );
            LocalDate date = LocalDate.of( Integer.parseInt( yearString ), month, day );
            return date.toString();
        }
        catch ( ParseException e )
        {
            return dateString;
        }
    }

    private static String fourDigitYear( String dateString )
    {
        try
        {
            int day = Integer.parseInt( dateString.substring( 0, 1 ) );
            int month = Integer.parseInt( dateString.substring( 1, 2 ) );
            Date d = new SimpleDateFormat( "yy" ).parse( dateString.substring( 2 ) );
            String yearString = new SimpleDateFormat( "yyyy" ).format( d );
            LocalDate date = LocalDate.of( Integer.parseInt( yearString ), month, day );
            return date.toString();
        }
        catch ( ParseException e )
        {
            return dateString;
        }
    }

    private static String dayAndMonthInCurrentYear( String dateString )
    {
        int day = Integer.parseInt( dateString.substring( 0, 2 ) );
        int month = Integer.parseInt( dateString.substring( 2 ) );
        LocalDate currentDate = LocalDate.now();
        LocalDate date = LocalDate.of( currentDate.getYear(), month, day );
        return date.toString();
    }

    private static String dayInCurrentMonth( String dateString )
    {
        int day = Integer.parseInt( dateString );
        LocalDate currentDate = LocalDate.now();
        if ( day <= currentDate.lengthOfMonth() )
        {
            LocalDate date = LocalDate.of( currentDate.getYear(), currentDate.getMonth(), day );
            return date.toString();
        }
        return dateString;
    }
}
