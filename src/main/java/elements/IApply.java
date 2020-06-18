package elements;

import java.util.function.Function;
import java.util.function.Predicate;

public interface IApply<T>
{
    T apply( Function<elements.Cell, Object> function );

    T applyIf( Predicate<elements.Cell> predicate, Function<elements.Cell, Object> function );
}
