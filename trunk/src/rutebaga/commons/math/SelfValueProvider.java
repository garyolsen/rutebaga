package rutebaga.commons.math;

public class SelfValueProvider extends ValueProvider<Number>
{
	@Override
	public double getValue(Number t)
	{
		return t.doubleValue();
	}

}
