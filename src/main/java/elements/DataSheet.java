package elements;

import io.CSVReader;
import io.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import type.CellType;
import type.Operation;
import type.SortOrder;
import util.StringConversion;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A simple implementation of a data sheet. The values are stored in cells which are logically organized in rows and columns.
 */
public class DataSheet implements IApply<DataSheet>
{
    private final RecordStore content;

    /**
     * Creates a new DataSheet with 10 rows and columns.
     */
    public DataSheet()
    {
        this( 10 );
    }

    /**
     * Creates a new DataSheet.
     *
     * @param initialCapacity the number of initial rows and columns.
     */
    public DataSheet( int initialCapacity )
    {
        content = new RecordStore( initialCapacity );
    }

    /**
     * Creates a new DataSheet.
     *
     * @param reader the CSVReader object which was created with the file to read from.
     */
    public DataSheet( CSVReader reader )
    {
        List<List<String>> elements = reader.readCSV();
        boolean withColumnNames = reader.isWithColumnNames();
        content = new RecordStore( elements, withColumnNames );
    }

    private DataSheet( List<Record> records )
    {
        content = new RecordStore( records );
    }

    /**
     * Get a row.
     *
     * @param index the index of the row
     * @return the row as Record
     */
    public Record getRow( int index )
    {
        int safeIndex = getSafeIndex( rowCount(), index );
        return content.getRecord( safeIndex );
    }

    /**
     * Gets a column.
     *
     * @param index the index of the column
     * @return the column as Record
     */
    public Record getColumn( int index )
    {
        int safeIndex = getSafeIndex( columnCount(), index );
        List<Cell> column = new ArrayList<>();
        content.forEach( row -> column.add( row.get( safeIndex ) ) );
        return content.newRecord( column );
    }

    /**
     * Gets a column.
     *
     * @param columnName the name of the column
     * @return the column as Record or null if the column name is not found.
     */
    public Record getColumn( String columnName )
    {
        int index = ArrayUtils.indexOf( content.getColumnNames().toArray(), columnName );
        return index != ArrayUtils.INDEX_NOT_FOUND ? getColumn( index ) : null;
    }

    /**
     * Adds a row to the end DataSheet. If the row is longer than the number of columns, the DataSheet will be resized.
     *
     * @param elements a list of the elements to add
     * @return the DataSheet object
     */
    public DataSheet addRow( List<?> elements )
    {
        return addRow( rowCount(), elements );
    }

    /**
     * Adds a row to the DataSheet. If the row is longer than the number of columns, the DataSheet will be resized.
     *
     * @param rowIndex the index where the row will be added
     * @param elements a list of the elements to add
     * @return the DataSheet object
     */
    public DataSheet addRow( int rowIndex, List<?> elements )
    {
        content.addRow( rowIndex, elements );
        return this;
    }

    /**
     * Updates a row.
     *
     * @param rowIndex the index of the row to update
     * @param elements a list of the elements to set at the row
     * @return the DataSheet object
     */
    public DataSheet setRow( int rowIndex, List<?> elements )
    {
        content.setRow( rowIndex, elements );
        return this;
    }

    /**
     * Adds a column to the end of DataSheet. If the column is longer than the number of rows, the DataSheet will be resized.
     *
     * @param elements a list of the elements to add
     * @return the DataSheet object
     */
    public DataSheet addColumn( List<?> elements )
    {
        return addColumn( columnCount(), elements );
    }

    /**
     * Adds a column to the DataSheet. If the column is longer than the number of rows, the DataSheet will be resized.
     *
     * @param columnIndex the index where the column will be added
     * @param elements    a list of the elements to add
     * @return the DataSheet object
     */
    public DataSheet addColumn( int columnIndex, List<?> elements )
    {
        content.addColumn( columnIndex, elements );
        return this;
    }

    /**
     * Updates a column.
     *
     * @param columnIndex the index of the column to update
     * @param elements    a list of the elements to set at the column
     * @return the DataSheet object
     */
    public DataSheet setColumn( int columnIndex, List<?> elements )
    {
        content.setColumn( columnIndex, elements );
        return this;
    }

    /**
     * Sets a column name.
     *
     * @param columnIndex the index of the column
     * @param name        the name to set
     * @return the DataSheet object
     */
    public DataSheet setColumnName( int columnIndex, String name )
    {
        if ( isSafeIndex( columnIndex, columnCount() ) )
        {
            content.setColumnName( columnIndex, name );
        }
        return this;
    }

    /**
     * Removes a row.
     *
     * @param index the index of the row to remove
     * @return the DataSheet object
     */
    public DataSheet removeRow( int index )
    {
        if ( index <= content.rowCount() - 1 )
        {
            content.removeRow( index );
        }
        return this;
    }

    /**
     * Removes a column.
     *
     * @param index the index of the column to remove
     * @return the DataSheet object
     */
    public DataSheet removeColumn( int index )
    {
        if ( isSafeIndex( index, columnCount() ) )
        {
            content.removeColumn( index );
        }
        return this;
    }

    /**
     * Removes a column.
     *
     * @param columnName the name of the column to remove
     * @return the DataSheet object
     */
    public DataSheet removeColumn( String columnName )
    {
        if ( !content.hasColumnNames() )
        {
            return this;
        }
        int index = ArrayUtils.indexOf( content.getColumnNames().toArray(), columnName );
        if ( index != ArrayUtils.INDEX_NOT_FOUND )
        {
            content.removeColumn( index );
        }
        return this;
    }

    /**
     * Gets the value of a cell.
     *
     * @param columnIndex the column index of the cell
     * @param rowIndex    the row index of the cell
     * @return the value
     */
    public String getValue( int columnIndex, int rowIndex )
    {
        Cell cell = getCell( columnIndex, rowIndex );
        if ( cell != null )
        {
            return cell.getValue();
        }
        return null;
    }

    /**
     * Sets the value of a cell.
     *
     * @param columnIndex the column index of the cell
     * @param rowIndex    the row index of the cell
     * @param value       the value to set
     * @return the DataSheet object
     */
    public DataSheet setValue( int columnIndex, int rowIndex, Object value )
    {
        Cell cell = getCell( columnIndex, rowIndex );
        if ( cell != null )
        {
            cell.setValue( value );
        }
        return this;
    }

    public DataSheet setValue( int columnIndex, int rowIndex, Operation operation, int fromColumn, int fromRow, int toColumn, int toRow )
    {
        Cell cell = getCell( columnIndex, rowIndex );
        if ( cell != null )
        {
            List<Cell> cells = new ArrayList<>();
            for ( int i = fromRow; i <= toRow; i++ )
            {
                for ( int j = fromColumn; j <= toColumn; j++ )
                {
                    cells.add( getCell( j, i ) );
                }
            }
            cell.setValue( cell.new Expression( operation, cells ) );
        }
        return this;
    }


    Cell getCell( int columnIndex, int rowIndex )
    {
        if ( rowIndex < rowCount() && columnIndex < columnCount() )
        {
            return content.getRecord( rowIndex ).get( columnIndex );
        }
        return null;
    }

    /**
     * Gets the data type of a cell.
     *
     * @param columnIndex the column index of the cell
     * @param rowIndex    the row index of the cell
     * @return the cell type
     */
    public CellType getCellType( int columnIndex, int rowIndex )
    {
        Cell cell = getCell( columnIndex, rowIndex );
        if ( cell != null )
        {
            return cell.getCellType();
        }
        return null;
    }

    /**
     * Sets the data type of a cell.
     *
     * @param columnIndex the column index of the cell
     * @param rowIndex    the row index of the cell
     * @param cellType    the cell type
     * @return the DataSheet object
     */
    public DataSheet setCellType( int columnIndex, int rowIndex, CellType cellType )
    {
        Cell cell = getCell( columnIndex, rowIndex );
        if ( cell != null )
        {
            cell.setCellType( cellType );
        }
        return this;
    }

    /**
     * Sets the data type for a row.
     *
     * @param rowIndex the index of the row
     * @param cellType the cell type
     * @return the DataSheet object
     */
    public DataSheet setCellTypeForRow( int rowIndex, CellType cellType )
    {
        if ( rowIndex < rowCount() )
        {
            content.getRecord( rowIndex ).forEach( cell -> cell.setCellType( cellType ) );
        }
        return this;
    }

    /**
     * Sets the data type of a column.
     *
     * @param columnIndex the index of the column
     * @param cellType    the cell type
     * @return the DataSheet object
     */
    public DataSheet setCellTypeForColumn( int columnIndex, CellType cellType )
    {
        if ( columnIndex < columnCount() )
        {
            content.forEach( row -> row.get( columnIndex ).setCellType( cellType ) );
        }
        return this;
    }

    /**
     * Slices the DataSheet to create a sub-sheet.
     *
     * @param fromColumn the index of the start column
     * @param fromRow    the index of the start row
     * @param toColumn   the index of the end column
     * @param toRow      the index of the end row
     * @return A list of Records representing the slice
     */
    public List<Record> slice( int fromColumn, int fromRow, int toColumn, int toRow )
    {
        if ( toColumn <= fromColumn || toRow <= fromRow )
        {
            return null;
        }
        if ( !isSafeColumn( fromColumn, toColumn ) || !isSafeRow( fromRow, toRow ) )
        {
            return null;
        }
        List<Record> result = new ArrayList<>();
        for ( int i = fromRow; i <= toRow; i++ )
        {
            Record row = content.getRecord( i );
            List<Cell> cells = new ArrayList<>();
            for ( int j = fromColumn; j <= toColumn; j++ )
            {
                cells.add( row.get( j ) );
            }
            result.add( content.newRecord( cells ) );
        }
        return Collections.unmodifiableList( result );
    }

    /**
     * Saves the content of the DataSheet.
     *
     * @param writer the CSVWriter object which was created with the file to write to.
     * @return the DataSheet object
     */
    public DataSheet save( CSVWriter writer )
    {
        writer.writeCSV( content.getPrintableContent( writer.isWithRawValues() ) );
        return this;
    }

    /**
     * Sorts the DataSheet in ascending order.
     *
     * @param columns a number of columns to sort by.
     * @return the DataSheet object
     */
    public DataSheet sortAscending( int... columns )
    {
        return sort( SortOrder.ASC, columns );
    }

    /**
     * Sorts the DataSheet in descending order.
     *
     * @param columns a number of columns to sort by.
     * @return the DataSheet object
     */
    public DataSheet sortDescending( int... columns )
    {
        return sort( SortOrder.DESC, columns );
    }

    /**
     * Sorts the DataSheet object.
     *
     * @param sortMap A LinkedHashMap that can contain multiple entries for sort operations. A sort operation is defined by the column index and the sort order.
     * @return the DataSheet object
     */
    public DataSheet sort( LinkedHashMap<Integer, SortOrder> sortMap )
    {
        Set<Integer> columns = sortMap.keySet();
        for ( int columnIndex : columns )
        {
            if ( !isSafeIndex( columnIndex, columnCount() ) )
            {
                return this;
            }
        }
        content.sort( ( r1, r2 ) ->
        {
            CompareToBuilder builder = new CompareToBuilder();
            sortMap.forEach( ( columnIndex, sortOrder ) ->
            {
                switch ( sortOrder )
                {
                    case ASC:
                        builder.append( r1.get( columnIndex ), r2.get( columnIndex ) );
                        break;
                    case DESC:
                        builder.append( r2.get( columnIndex ), r1.get( columnIndex ) );
                }
            } );
            return builder.toComparison();
        } );
        return this;
    }

    private DataSheet sort( SortOrder order, int... columns )
    {
        for ( int columnIndex : columns )
        {
            if ( !isSafeIndex( columnIndex, columnCount() ) )
            {
                return this;
            }
        }
        content.sort( ( r1, r2 ) ->
        {
            CompareToBuilder builder = new CompareToBuilder();
            for ( int columnIndex : columns )
            {
                switch ( order )
                {
                    case ASC:
                        builder.append( r1.get( columnIndex ), r2.get( columnIndex ) );
                        break;
                    case DESC:
                        builder.append( r2.get( columnIndex ), r1.get( columnIndex ) );
                }
            }
            return builder.toComparison();
        } );
        return this;
    }

    /**
     * Applies a function to each Cell which will update the value of the Cell with the return value of the function.
     *
     * @param function A function which accepts a Cell as input and returns an Object with will be set as the new value of the Cell.
     * @return the DataSheet object
     */
    public DataSheet apply( Function<Cell, Object> function )
    {
        content.forEach( row -> row.forEach( cell -> cell.apply( function ) ) );
        return this;
    }

    /**
     * Applies a function to each Cell that fulfills the Predicate. The value of the Cell will be updated with the return value of the function.
     *
     * @param predicate the Predicate to test
     * @param function  A function which accepts a Cell as input and returns an Object with will be set as the new value of the Cell.
     * @return the DataSheet object
     */
    public DataSheet applyIf( Predicate<Cell> predicate, Function<Cell, Object> function )
    {
        content.forEach( row -> row.cells().stream().filter( predicate ).forEach( cell -> cell.apply( function ) ) );
        return this;
    }

    public List<DataSheet> groupBy( Function<Record, String> function )
    {
        Map<String, List<Record>> groups = content.rows().stream()
                                                  .collect( Collectors.groupingBy( row -> function.apply( row ) ) );
        return groups.values().stream().map( DataSheet::new ).collect( Collectors.toList() );
    }

    public List<DataSheet> partition( Predicate<Record> predicate )
    {
        Map<Boolean, List<Record>> groups = content.rows().stream()
                                                   .collect( Collectors.groupingBy( row -> predicate.test( row ) ) );
        return groups.values().stream().map( DataSheet::new ).collect( Collectors.toList() );
    }

    private int getSafeIndex( int max, int index )
    {
        if ( index < 0 ) return 0;
        if ( index >= max ) return max - 1;
        return index;
    }

    private boolean isSafeIndex( int index, int max )
    {
        return index >= 0 && index < max;
    }

    private boolean isSafeRow( int... rows )
    {
        for ( int row : rows )
        {
            if ( !isSafeIndex( row, rowCount() ) ) return false;
        }
        return true;
    }

    private boolean isSafeColumn( int... columns )
    {
        for ( int column : columns )
        {
            if ( !isSafeIndex( column, columnCount() ) ) return false;
        }
        return true;
    }

    /**
     * Returns the rows of the DataSheet.
     *
     * @return A list of Records representing the rows.
     */
    public List<Record> rows()
    {
        return Collections.unmodifiableList( content.rows() );
    }

    /**
     * Returns the columns of the DataSheet.
     *
     * @return A list of Records representing the columns.
     */
    public List<Record> columns()
    {
        ArrayList<Record> columns = new ArrayList<>();
        for ( int i = 0; i < columnCount(); i++ )
        {
            columns.add( getColumn( i ) );
        }
        return Collections.unmodifiableList( columns );
    }

    /**
     * Returns the column names.
     *
     * @return A list of column names or null if column names are not defined.
     */
    public List<String> columnNames()
    {
        return content.getColumnNames();
    }

    /**
     * Returns the number of rows in the DataSheet.
     *
     * @return the number of rows
     */
    public int rowCount()
    {
        return content.rowCount();
    }

    /**
     * Returns the number of columns in the DataSheet.
     *
     * @return the number of columns
     */
    public int columnCount()
    {
        return content.columnCount();
    }

    /**
     * The number of Cells in this DataSheet.
     *
     * @return the number of Cells
     */
    public int size()
    {
        return rowCount() * columnCount();
    }

    /**
     * The number of non-empty Cells in this DataSheet.
     *
     * @return the number of Cells
     */
    public int count()
    {
        return content.count();
    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }

    @Override
    public String toString()
    {
        return StringConversion.from( this, content.hasColumnNames() );
    }
}
