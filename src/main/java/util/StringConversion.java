package util;

import elements.DataSheet;
import elements.Record;
import org.apache.commons.lang3.StringUtils;

import java.util.StringJoiner;
import java.util.function.Predicate;

public final class StringConversion
{
    private static final String LINE_SEPARATOR = System.getProperty( "line.separator" );

    private StringConversion()
    {

    }

    public static String from( DataSheet dataFrame, boolean withColumnNames )
    {
        StringBuilder sb = new StringBuilder();
        if ( withColumnNames )
        {
            StringJoiner sj = new StringJoiner( " | " );
            dataFrame.columnNames().forEach( value -> sj
                    .add( String.format( "[%s]", formatForFrame( value, 8 ) ) ) );
            sb.append( sj.toString() );
            sb.append( LINE_SEPARATOR );
        }
        dataFrame.rows().forEach( row ->
        {
            StringJoiner sj = new StringJoiner( " | " );
            row.forEach( cell -> sj.add( formatForFrame( cell.toString(), 10 ) ) );
            sb.append( sj.toString() );
            sb.append( LINE_SEPARATOR );
        } );
        return sb.toString();
    }

    /*
    public static String from(DataFrame dataFrame, boolean withColumnNames)
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "+" );
        StringJoiner sj = new StringJoiner( "+" );
        int i = 0;
        while (i < dataFrame.columnCount()) {
            sj.add( "-".repeat( 9 ) );
            i++;
        }
        sb.append( sj.toString() );
        sb.append( "+" );
        sb.append( LINE_SEPARATOR );
        if ( withColumnNames )
        {
            StringJoiner sj_ = new StringJoiner( "|" );
            dataFrame.columnNames().forEach( value -> sj_
                    .add( String.format( "[%s]", formatForFrame( value, 8 ) ) ) );
            sb.append( sj_.toString() );
            sb.append( LINE_SEPARATOR );
        }
        dataFrame.rows().forEach( row ->
        {
            StringJoiner sj_ = new StringJoiner( "|" );
            row.forEach( cell -> sj_.add( formatForFrame( cell.toString(), 10 ) ) );
            sb.append( sj_.toString() );
            sb.append( LINE_SEPARATOR );
        } );
        return sb.toString();
    }

     */

    public static String from( Record record )
    {
        StringJoiner sj = new StringJoiner( "," );
        record.cells().stream().filter( Predicate.not( cell -> cell.toString().isBlank() ) )
              .map( cell -> quoteCommaValue( cell.toString() ) )
              .forEach( sj::add );
        return sj.toString();
    }

    private static String formatForFrame( String raw, int maxWidth )
    {
        return String.format( "%1$" + maxWidth + "s", StringUtils.abbreviate( raw, maxWidth ) );
    }

    public static String quoteCommaValue( String value )
    {
        return value.contains( "," ) && !( value.startsWith( "\"" ) && value.endsWith( "\"" ) ) ? String
                .format( "\"%s\"", value ) : value;
    }
}
