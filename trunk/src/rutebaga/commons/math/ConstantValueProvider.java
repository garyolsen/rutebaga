package rutebaga.commons.math;

public class ConstantValueProvider<T> extends ValueProvider<T>
{
	private double value;

	public ConstantValueProvider(double value)
	{
		super();
		this.value = value;
	}

	@Override
	public double getValue(T t)
	{
		return value;
	}

}
