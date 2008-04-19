package rutebaga.commons.math;

public interface BidirectionalValueProvider<T> extends ValueProvider<T>
{
	double addTo(T t, double value);
}
