package io;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReader
{
    private final List<List<String>> content;

    private final File file;

    private char separator;

    private String charSet;

    private boolean withColumnNames;

    /**
     * Creates a new CSVReader from the file contents.
     *
     * @param fileName The name of the file to read from. Can be a path.
     */
    public CSVReader( String fileName )
    {
        this( new File( fileName ), ',', "UTF-8", false );
    }

    /**
     * Creates a new CSVReader from the file contents.
     *
     * @param file the file to read from
     */
    public CSVReader( File file )
    {
        this( file, ',', "UTF-8", false );
    }

    private CSVReader( File file, char separator, String charSet, boolean withColumnNames )
    {
        content = new ArrayList<>();
        this.file = file;
        this.separator = separator;
        this.charSet = charSet;
        this.withColumnNames = withColumnNames;
    }

    /**
     * Specifies which character is used to separate the values. Use {@code \t} for tabs of .tsv files.
     *
     * @param separator the separating character
     * @return the CSVReader object
     */
    public CSVReader withSeparator( char separator )
    {
        this.separator = separator;
        return this;
    }

    /**
     * Specifies which charset is used in the file.
     *
     * @param charSet the charset of the file
     * @return the CSVReader object
     */
    public CSVReader withCharSet( String charSet )
    {
        this.charSet = charSet;
        return this;
    }

    /**
     * Specifies whether the first row should be treated as column names.
     *
     * @param withColumnNames true if the first row describes column names, false otherwise
     * @return the CSVReader object
     */
    public CSVReader withColumnNames( boolean withColumnNames )
    {
        this.withColumnNames = withColumnNames;
        return this;
    }

    /**
     * Checks whether the CSVReader is defined to treat the first row as column names.
     *
     * @return true if the first row describes column names, false otherwise
     */
    public boolean isWithColumnNames()
    {
        return withColumnNames;
    }

    /**
     * Reads the content of the file.
     *
     * @return A list of lines. Each line is another list containing the elements as String.
     */
    public List<List<String>> readCSV()
    {
        try ( BufferedReader br = new BufferedReader( new FileReader( file, Charset.forName( charSet ) ) ) )
        {
            String line;
            while ( ( line = br.readLine() ) != null )
            {
                if ( !line.equals( "\0" ) )
                {
                    addLine( line.replaceAll( "\0", "" ) );
                }
            }
        }
        catch ( IOException e )
        {
            System.err.println( "ERROR: File could not be read!" );
            content.clear();
        }
        return content;
    }

    private void addLine( String line )
    {
        final List<String> elements = Arrays.stream( StringUtils.splitPreserveAllTokens( line, separator ) )
                                            .collect( Collectors.toList() );
        content.add( elements );
    }

}
