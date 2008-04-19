package rutebaga.commons.math;

import static rutebaga.commons.math.OperationSet.*;

import rutebaga.commons.math.OperationSet.AddOperation;

public abstract class ValueProvider<T>
{
	public abstract double getValue(T t);

	public ValueProvider<T> minus(Number other)
	{
		return binaryOp(new SubtractOperation(), other);
	}

	public ValueProvider<T> minus(ValueProvider<? super T> other)
	{
		return binaryOp(new SubtractOperation(), other);
	}

	public ValueProvider<T> negate()
	{
		return unaryOp(new NegateOperation());
	}

	public ValueProvider<T> over(Number other)
	{
		return binaryOp(new DivideOperation(), other);
	}

	public ValueProvider<T> over(ValueProvider<? super T> other)
	{
		return binaryOp(new DivideOperation(), other);
	}

	public ValueProvider<T> plus(Number other)
	{
		return binaryOp(new AddOperation(), other);
	}

	public ValueProvider<T> plus(ValueProvider<? super T> other)
	{
		return binaryOp(new AddOperation(), other);
	}

	public ValueProvider<T> times(Number other)
	{
		return binaryOp(new MultiplyOperation(), other);
	}

	public ValueProvider<T> times(ValueProvider<? super T> other)
	{
		return binaryOp(new MultiplyOperation(), other);
	}

	private ValueProvider<T> binaryOp(BinaryOperation op, Number other)
	{
		return new BinaryOperationValueProvider<T>(op, this,
				new ConstantValueProvider<T>(other.doubleValue()));
	}

	private ValueProvider<T> binaryOp(BinaryOperation op,
			ValueProvider<? super T> other)
	{
		return new BinaryOperationValueProvider<T>(op, this, other);
	}

	private ValueProvider<T> unaryOp(UnaryOperation op)
	{
		return new UnaryOperationValueProvider<T>(op, this);
	}
}
