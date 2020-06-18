package elements;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import type.CellType;
import type.Operation;
import util.CellFormatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import static type.CellType.TEXT;

/**
 * A cell is the basic element of this table. It can hold scalar values or expressions.
 */
public class Cell implements Comparable<Cell>
{
    private Object value;

    private CellType cellType;

    Cell()
    {
        this( StringUtils.EMPTY );
    }

    Cell( Object value )
    {
        this.value = value;
        this.cellType = TEXT;
    }

    boolean isEmpty()
    {
        return ( value == null || value.toString().isBlank() );
    }

    void setCellType( CellType cellType )
    {
        this.cellType = cellType;
    }

    void setValue( Object value )
    {
        if ( !( value instanceof Cell ) )
        {
            this.value = value;
        }
    }

    void apply( Function<Cell, Object> function )
    {
        setValue( function.apply( this ) );
    }

    /**
     * Returns the type of this Cell.
     *
     * @return the cell type
     */
    public CellType getCellType()
    {
        return cellType;
    }

    /**
     * Returns the value of the Cell. The value is formatted according to the cell type.
     *
     * @return A String representation of the value
     */
    public String getValue()
    {
        return CellFormatter.format( value instanceof Expression ? ( ( Expression ) value ).getResult() : value
                .toString(), cellType );
    }

    /**
     * Returns the unformatted value of the Cell.
     *
     * @return The value object
     */
    public Object getRawValue()
    {
        return value;
    }

    /**
     * Returns the value as boolean.
     *
     * @return A boolean representation of the value
     */
    public boolean getValueAsBoolean()
    {
        switch ( getRawValue().toString() )
        {
            case "true":
            case "TRUE":
            case "1":
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns the value as int.
     *
     * @return An int representation of the value
     */
    public int getValueAsInt()
    {
        String value = getValue();
        switch ( value )
        {
            case "true":
            case "TRUE":
            case "1":
                return 1;
            default:
                try
                {
                    return Integer.parseInt( value );
                }
                catch ( NumberFormatException e )
                {
                    return 0;
                }
        }
    }

    /**
     * Returns the value as double.
     *
     * @return A double representation of the value
     */
    public double getValueAsDouble()
    {
        String value = getValue();
        switch ( value )
        {
            case "true":
            case "TRUE":
            case "1":
                return 1;
            default:
                try
                {
                    return Double.parseDouble( value );
                }
                catch ( NumberFormatException e )
                {
                    return 0;
                }
        }
    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }

    @Override
    public String toString()
    {
        return value != null ? getValue() : StringUtils.EMPTY;
    }

    @Override
    public int compareTo( Cell cell )
    {
        String comparing = toString();
        String compareTo = cell.toString();
        if ( NumberUtils.isParsable( comparing ) && NumberUtils.isParsable( compareTo ) )
        {
            return new BigDecimal( comparing ).compareTo( new BigDecimal( compareTo ) );
        }
        return comparing.compareTo( compareTo );
    }

    class Expression
    {
        private final Operation operation;

        private final List<Cell> operands;

        private String result = StringUtils.EMPTY;

        Expression( Operation operation, List<Cell> operands )
        {
            this.operation = operation;
            this.operands = operands;
        }

        Operation getOperation()
        {
            return operation;
        }

        List<Cell> getOperands()
        {
            return operands;
        }

        String getResult()
        {
            result = ExpressionEvaluator.evaluate( this );
            return result;
        }

        @Override
        public String toString()
        {
            return result;
        }

    }
}
