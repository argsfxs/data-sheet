package elements;

import type.CellType;
import util.StringConversion;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A record can represent a row or a column. It basically is a collection of cells.
 */
public class Record implements Iterable<Cell>, IApply<Record>
{
    private final List<Cell> cells;

    Record( List<?> elements )
    {
        cells = new ArrayList<>( elements.size() );
        elements.stream().map( element -> element instanceof Cell ? ( Cell ) element : new Cell( element ) )
                .forEach( cells::add );
    }

    Cell get( int index )
    {
        return cells.get( index );
    }

    void add( Cell cell )
    {
        cells.add( cell );
    }

    void add( int index, Cell cell )
    {
        cells.add( index, cell );
    }

    void set( int index, Cell cell )
    {
        cells.set( index, cell );
    }

    void remove( int index )
    {
        cells.remove( index );
    }

    CellType getCellType()
    {
        CellType firstCellType = cells.get( 0 ).getCellType();
        for ( Cell cell : cells )
        {
            if ( cell.getCellType() == firstCellType )
            {
                continue;
            }
            return CellType.TEXT;
        }
        return firstCellType;
    }

    boolean isEmpty()
    {
        for ( Cell cell : cells )
        {
            if ( !cell.isEmpty() )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a list of all Cells.
     *
     * @return a list of Cells
     */
    public List<Cell> cells()
    {
        return Collections.unmodifiableList( cells );
    }

    /**
     * Applies a function to each Cell which will update the value of the Cell with the return value of the function.
     *
     * @param function A function which accepts a Cell as input and returns an Object with will be set as the new value of the Cell.
     * @return the Record
     */
    public Record apply( Function<Cell, Object> function )
    {
        cells.stream().forEach( cell -> cell.apply( function ) );
        return this;
    }

    /**
     * Applies a function to each Cell that fulfills the Predicate. The value of the Cell will be updated with the return value of the function.
     *
     * @param predicate the Predicate to test
     * @param function  A function which accepts a Cell as input and returns an Object with will be set as the new value of the Cell.
     * @return the Record
     */
    public Record applyIf( Predicate<Cell> predicate, Function<Cell, Object> function )
    {
        cells.stream().filter( predicate ).forEach( cell -> cell.apply( function ) );
        return this;
    }

    /**
     * The number of Cells in this Record.
     *
     * @return the number of Cells
     */
    public int size()
    {
        return cells.size();
    }

    /**
     * The number of non-empty Cells in this Record.
     *
     * @return the number of Cells
     */
    public int count()
    {
        return ( int ) cells.stream().filter( Predicate.not( Cell::isEmpty ) ).count();
    }

    /**
     * Describes the structure of this Record.
     *
     * @return a String which describes the data type of all Cells.
     */
    public String structure()
    {
        StringJoiner sj = new StringJoiner( " | " );
        for ( int i = 0; i < size(); i++ )
        {
            sj.add( String.format( "idx %s: %s", i, cells.get( i ).getCellType() ) );
        }
        return sj.toString();
    }

    @Override
    public Iterator<Cell> iterator()
    {
        return cells.iterator();
    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }

    @Override
    public String toString()
    {
        return StringConversion.from( this );
    }
}
