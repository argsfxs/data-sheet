package elements;

import java.util.List;

final class ExpressionEvaluator
{
    private static final String EXPR_ERROR = "EXPR_ERROR";

    private ExpressionEvaluator()
    {

    }

    static String evaluate( Cell.Expression expression )
    {
        switch ( expression.getOperation() )
        {
            case SUM:
                return sum( expression.getOperands() );
            case AVG:
                return avg( expression.getOperands() );
            case COUNT:
                return count( expression.getOperands() );
            default:
                return EXPR_ERROR;
        }
    }

    private static String count( List<Cell> operands )
    {
        return String.valueOf( operands.size() );
    }

    private static String avg( List<Cell> operands )
    {
        return String.valueOf( sumValues( operands ) / operands.size() );
    }

    private static String sum( List<Cell> operands )
    {
        return String.valueOf( sumValues( operands ) );
    }

    private static double sumValues( List<Cell> operands )
    {
        double result = 0;
        for ( Cell cell : operands )
        {
            try
            {
                double value = Double.parseDouble( cell.getValue() );
                result += value;
            }
            catch ( NumberFormatException e )
            {
                return 0;
            }
        }
        return result;
    }
}
