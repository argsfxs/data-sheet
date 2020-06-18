package util;

import org.apache.commons.lang3.StringUtils;
import type.CellType;

import java.util.Currency;
import java.util.Locale;

public final class CellFormatter
{
    private CellFormatter()
    {

    }

    public static String format( String value, CellType cellType )
    {
        try
        {
            switch ( cellType )
            {
                case NUMBER:
                    return String.format( "%.2f", Double.valueOf( value ) );
                case PERCENTAGE:
                    return String.format( "%.2f", ( Double.parseDouble( value ) * 100 ) ) + " %";
                case DATE:
                    return DateParser.parse( value );
                case CURRENCY:
                    return String.format( "%.2f", Double.valueOf( value ) ) + StringUtils.SPACE + Currency
                            .getInstance( Locale.getDefault() ).getSymbol();
                default:
                    return value;
            }
        }
        catch ( Exception e )
        {
            return value;
        }
    }
}
