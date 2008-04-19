package rutebaga.commons.math;

public class ConditionalValueProvider<T> implements ValueProvider<T>
{
	private ValueProvider<T> condition;
	private ValueProvider<T> trueArg;
	private ValueProvider<T> falseArg;

	public ConditionalValueProvider(ValueProvider<T> condition,
			ValueProvider<T> trueArg, ValueProvider<T> falseArg)
	{
		super();
		this.condition = condition;
		this.trueArg = trueArg;
		this.falseArg = falseArg;
	}

	public double getValue(T t)
	{
		if(condition.getValue(t) > 0)
			return trueArg.getValue(t);
		else 
			return falseArg.getValue(t);
	}
}
