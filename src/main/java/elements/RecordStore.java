package elements;

import type.RecordType;
import util.CellFormatter;
import util.StringConversion;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class RecordStore implements Iterable<Record>
{
    private final ArrayList<Record> records;

    private ArrayList<String> columnNames;

    RecordStore( List<Record> records )
    {
        this.records = new ArrayList<>( records );
    }

    RecordStore( int initialCapacity )
    {
        records = new ArrayList<>( initialCapacity );
        fillRecords( initialCapacity );
    }

    RecordStore( List<List<String>> elements, boolean withColumnNames )
    {
        if ( elements.isEmpty() )
        {
            records = new ArrayList<>( 10 );
            fillRecords( 10 );
        }
        else
        {
            records = new ArrayList<>( elements.size() );
            fillRecords( elements, withColumnNames );
        }
    }

    List<List<String>> getPrintableContent( boolean withRawValues )
    {
        List<List<String>> rows = new ArrayList<>();
        if ( hasColumnNames() )
        {
            rows.add( getColumnNames() );
        }
        records.stream().filter( Predicate.not( Record::isEmpty ) ).forEach( row ->
                rows.add( row.cells().stream().map( cell -> StringConversion
                        .quoteCommaValue( withRawValues ? cell.getRawValue().toString() : CellFormatter
                                .format( cell.getValue(), cell.getCellType() ) ) ).collect( Collectors.toList() ) ) );
        return rows;
    }

    List<String> getColumnNames()
    {
        return hasColumnNames() ? columnNames : null;
    }

    boolean hasColumnNames()
    {
        return columnNames != null && !columnNames.isEmpty();
    }

    void addColumnName( String name )
    {
        columnNames.add( name );
    }

    void setColumnName( int columnIndex, String name )
    {
        columnNames.set( columnIndex, name );
    }

    void removeColumnName( int index )
    {
        columnNames.remove( index );
    }

    Record getRecord( int index )
    {
        return records.get( index );
    }

    void addRow( int rowIndex, List<?> elements )
    {
        records.add( rowIndex, createRecord( elements, columnCount(), RecordType.ROW ) );
    }

    void setRow( int rowIndex, List<?> elements )
    {
        records.set( rowIndex, createRecord( elements, columnCount(), RecordType.ROW ) );
    }

    void addColumn( int columnIndex, List<?> elements )
    {
        if ( hasColumnNames() && !elements.isEmpty() )
        {
            if ( !( elements instanceof ArrayList || elements instanceof LinkedList ) )
            {
                elements = new ArrayList<>( elements );
            }
            addColumnName( elements.get( 0 ).toString() );
            elements.remove( 0 );
        }
        Record newColumn = createRecord( elements, rowCount(), RecordType.COLUMN );
        Iterator<Record> rowIterator = records.iterator();
        Iterator<Cell> cellIterator = newColumn.iterator();
        while ( rowIterator.hasNext() )
        {
            Record row = rowIterator.next();
            row.add( columnIndex, cellIterator.next() );
        }
    }

    void setColumn( int columnIndex, List<?> elements )
    {
        Record newColumn = createRecord( elements, rowCount(), RecordType.COLUMN );
        Iterator<Record> rowIterator = records.iterator();
        Iterator<Cell> cellIterator = newColumn.iterator();
        while ( rowIterator.hasNext() )
        {
            Record row = rowIterator.next();
            row.set( columnIndex, cellIterator.next() );
        }
    }

    void removeRow( int index )
    {
        records.remove( index );
    }

    void removeColumn( int index )
    {
        records.forEach( row -> row.remove( index ) );
        if ( hasColumnNames() )
        {
            removeColumnName( index );
        }
    }

    void sort( Comparator<? super Record> comparator )
    {
        records.sort( comparator );
    }

    int rowCount()
    {
        return records.size();
    }

    int columnCount()
    {
        return records.get( 0 ).size();
    }

    int count()
    {
        return records.stream().mapToInt( Record::count ).sum();
    }

    ArrayList<Record> rows()
    {
        return records;
    }

    Record newRecord( List<Cell> elements )
    {
        return new Record( elements );
    }

    private void fillRecords( List<List<String>> elements, boolean withColumnNames )
    {
        if ( withColumnNames )
        {
            elements = new ArrayList<>( elements );
            this.columnNames = new ArrayList<>( elements.get( 0 ) );
            elements.remove( 0 );
        }
        elements.stream().map( Record::new ).forEach( records::add );
    }

    private void fillRecords( int capacity )
    {
        for ( int i = 0; i < capacity; i++ )
        {
            List<Cell> cells = new ArrayList<>();
            for ( int j = 0; j < capacity; j++ )
            {
                cells.add( new Cell() );
            }
            records.add( new Record( cells ) );
        }
    }

    private Record createRecord( List<?> elements, int currentSize, RecordType type )
    {
        Record newRecord;
        if ( elements.size() <= currentSize )
        {
            List<Cell> cells = new ArrayList<>( currentSize );
            elements.stream().map( Cell::new ).forEach( cells::add );
            int remaining = currentSize - cells.size();
            for ( int i = 1; i <= remaining; i++ )
            {
                cells.add( new Cell() );
            }
            newRecord = new Record( cells );
        }
        else
        {
            newRecord = new Record( elements.stream().map( Cell::new ).collect( Collectors.toList() ) );
            resizeStore( newRecord.size(), type );
        }
        return newRecord;
    }

    private void resizeStore( int size, RecordType type )
    {
        switch ( type )
        {
            case ROW:
                resizeRows( size );
                break;
            case COLUMN:
                resizeColumns( size );
        }
    }

    private void resizeRows( int rowSize )
    {
        int initialColumnSize = columnCount();
        if ( rowSize > initialColumnSize )
        {
            if ( hasColumnNames() )
            {
                for ( int i = initialColumnSize; i < rowSize; i++ )
                {
                    addColumnName( "COLUMN" );
                }
            }
            records.forEach( row ->
            {
                for ( int i = initialColumnSize; i < rowSize; i++ )
                {
                    row.add( new Cell() );
                }
            } );
        }
    }

    private void resizeColumns( int columnSize )
    {
        int remaining = columnSize - rowCount();
        for ( int i = 1; i <= remaining; i++ )
        {
            List<Cell> cells = new ArrayList<>();
            for ( int j = 0; j < columnCount(); j++ )
            {
                cells.add( new Cell() );
            }
            records.add( new Record( cells ) );
        }
    }

    @Override
    public Iterator<Record> iterator()
    {
        return records.iterator();
    }
}
