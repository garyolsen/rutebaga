package rutebaga.commons.math;

public class ValueProviderPointer<T> extends BidirectionalValueProvider<T>
{
	private ValueProvider<T> ref;

	public ValueProviderPointer()
	{
		super();
	}

	public ValueProviderPointer(ValueProvider<T> ref)
	{
		super();
		this.ref = ref;
	}

	@Override
	public double getValue(T t)
	{
		return ref.getValue(t);
	}

	public ValueProvider<T> getRef()
	{
		return ref;
	}

	public void setRef(ValueProvider<T> ref)
	{
		this.ref = ref;
	}

	@Override
	public double addTo(T t, double value)
	{
		try
		{
			return ((BidirectionalValueProvider<T>) ref).addTo(t, value);
		}
		catch (Exception e)
		{
			throw new UnsupportedOperationException();
		}
	}
}
