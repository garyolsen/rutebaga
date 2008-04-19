package rutebaga.commons.math;

public class ConstantValueProvider<T> implements ValueProvider<T>
{
	private double value;

	public ConstantValueProvider(double value)
	{
		super();
		this.value = value;
	}

	public double getValue(T t)
	{
		return value;
	}

}
