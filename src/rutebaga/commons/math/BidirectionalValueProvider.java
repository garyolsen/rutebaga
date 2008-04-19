package rutebaga.commons.math;

public abstract class BidirectionalValueProvider<T> extends ValueProvider<T>
{
	public abstract double addTo(T t, double value);
}
