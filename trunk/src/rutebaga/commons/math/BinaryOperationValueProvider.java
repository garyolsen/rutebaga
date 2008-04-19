package rutebaga.commons.math;

public class BinaryOperationValueProvider<T> implements ValueProvider<T>
{
	private BinaryOperation operation;
	private ValueProvider<T> arg1;
	private ValueProvider<T> arg2;

	public BinaryOperationValueProvider(BinaryOperation operation,
			ValueProvider<T> arg1, ValueProvider<T> arg2)
	{
		super();
		this.operation = operation;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	public double getValue(T t)
	{
		return operation.calculate(arg1.getValue(t), arg2.getValue(t));
	}

}
