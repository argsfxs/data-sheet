package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.StringJoiner;

public class CSVWriter
{
    private final File file;

    private char separator;

    private String charSet;

    private boolean withRawValues;

    /**
     * Creates a new CSVWriter with the name of the file to write to.
     *
     * @param fileName The name of the file to write to. Can be a path.
     */
    public CSVWriter( String fileName )
    {
        this( new File( fileName ), ',', "UTF-8", false );
    }

    /**
     * Creates a new CSVWriter with the file to write to.
     *
     * @param file The file to write to.
     */
    public CSVWriter( File file )
    {
        this( file, ',', "UTF-8", false );
    }

    private CSVWriter( File file, char separator, String charSet, boolean withRawValues )
    {
        this.file = file;
        this.separator = separator;
        this.charSet = charSet;
        this.withRawValues = withRawValues;
    }

    /**
     * Specifies which character is used to separate the values. Use {@code \t} for tabs of .tsv files.
     *
     * @param separator the separating character
     * @return the CSVWriter object
     */
    public CSVWriter withSeparator( char separator )
    {
        this.separator = separator;
        return this;
    }

    /**
     * Specifies which charset is used in the file.
     *
     * @param charSet the charset of the file
     * @return the CSVWriter object
     */
    public CSVWriter withCharSet( String charSet )
    {
        this.charSet = charSet;
        return this;
    }

    /**
     * Specifies whether the values should be written without data type formatting.
     *
     * @param withRawValues true if raw values should be written, false otherwise
     * @return the CSVWriter object
     */
    public CSVWriter withRawValues( boolean withRawValues )
    {
        this.withRawValues = withRawValues;
        return this;
    }

    /**
     * Checks whether the CSVWriter is defined to write raw values.
     *
     * @return true if raw values should be written, false otherwise
     */
    public boolean isWithRawValues()
    {
        return withRawValues;
    }

    /**
     * Write the values to the file.
     *
     * @param content A nested list of the actual values as String.
     */
    public void writeCSV( List<List<String>> content )
    {
        try ( BufferedWriter bw = new BufferedWriter( new FileWriter( file, Charset.forName( charSet ) ) ) )
        {
            for ( List<String> row : content )
            {
                StringJoiner sj = new StringJoiner( String.valueOf( separator ) );
                row.forEach( sj::add );
                bw.write( sj.toString() );
                bw.newLine();
            }
        }
        catch ( Exception e )
        {
            System.err.println( "ERROR: File could not be written!" );
        }
    }
}
