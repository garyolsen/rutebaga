package rutebaga.commons.math;

import rutebaga.commons.Convertor;

public class ConvertorValueProvider<T, U> extends BidirectionalValueProvider<T>
{
	private ValueProvider<U> backing;
	private Convertor<U, T> convertor;

	public ConvertorValueProvider(ValueProvider<U> backing,
			Convertor<U, T> convertor)
	{
		super();
		this.backing = backing;
		this.convertor = convertor;
	}

	@Override
	public double getValue(T t)
	{
		return backing.getValue(convertor.convert(t));
	}

	@Override
	public double addTo(T t, double value)
	{
		try
		{
			BidirectionalValueProvider<U> bidir = (BidirectionalValueProvider<U>) backing;
			return bidir.addTo(convertor.convert(t), value);
		}
		catch(Exception e)
		{
			throw new UnsupportedOperationException("Backing value provider is not bidirectional");
		}
	}
}
