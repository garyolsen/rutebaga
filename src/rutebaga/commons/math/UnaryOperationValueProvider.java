package rutebaga.commons.math;

public class UnaryOperationValueProvider<T> implements ValueProvider<T>
{
	private UnaryOperation operation;

	private ValueProvider<T> argument;

	public double getValue(T t)
	{
		return operation.calculate(argument.getValue(t));
	}

	public UnaryOperationValueProvider(UnaryOperation operation,
			ValueProvider<T> argument)
	{
		super();
		this.operation = operation;
		this.argument = argument;
	}
}
