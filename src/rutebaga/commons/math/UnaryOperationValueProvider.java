package rutebaga.commons.math;

public class UnaryOperationValueProvider<T> extends ValueProvider<T>
{
	private UnaryOperation operation;

	private ValueProvider<T> argument;

	@Override
	public double getValue(T t)
	{
		return operation.calculate(argument.getValue(t));
	}

	private UnaryOperationValueProvider(UnaryOperation operation,
			ValueProvider<T> argument)
	{
		super();
		this.operation = operation;
		this.argument = argument;
	}
}
