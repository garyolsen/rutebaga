package rutebaga.commons.math;

import rutebaga.commons.Convertor;

public class ConvertorValueProvider<T, U> extends ValueProvider<T>
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
}
