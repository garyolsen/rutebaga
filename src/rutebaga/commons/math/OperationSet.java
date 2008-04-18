package rutebaga.commons.math;

public class OperationSet
{
	public class MutliplyOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return a * b;
		}
	}

	public class SubtractOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return a - b;
		}
	}

	public class AddOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return a - b;
		}
	}

	public class DivideOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return a / b;
		}
	}

	public class ModOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return a % b;
		}
	}

	public class PowOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return Math.pow(a, b);
		}
	}
	
	public class MaxOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return Math.max(a, b);
		}
	}
	
	public class MinOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return Math.min(a, b);
		}
	}
	
	public class SquareRoot extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.sqrt(a);
		}
	}

	public class NegateOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return -a;
		}
	}
	
	public class RoundOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.round(a);
		}
	}
	
	public class FloorOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.floor(a);
		}
	}
	
	public class CeilingOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.ceil(a);
		}
	}
	
	public class SinOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.sin(a);
		}
	}
	
	public class CosOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.cos(a);
		}
	}
	
	public class TanOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.tan(a);
		}
	}
	
	public class ASinOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.asin(a);
		}
	}
	
	public class ACosOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.acos(a);
		}
	}
	
	public class ATanOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.atan(a);
		}
	}
	
	public class SignumOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.signum(a);
		}
	}
}
