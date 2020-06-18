package elements;

import java.util.function.Function;
import java.util.function.Predicate;

public interface IApply<T>
{
    T apply( Function<Cell, Object> function );

    T applyIf( Predicate<Cell> predicate, Function<Cell, Object> function );
}
