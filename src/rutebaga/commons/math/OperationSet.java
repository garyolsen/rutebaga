package rutebaga.commons.math;

import java.util.Random;

public class OperationSet
{
	public static class ACosOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.acos(a);
		}

		@Override
		public String getDefaultString()
		{
			return "cos";
		}
	}

	public static class AddOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return a + b;
		}

		@Override
		public String getDefaultString()
		{
			return "+";
		}
	}

	public static class ASinOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.asin(a);
		}

		@Override
		public String getDefaultString()
		{
			return "sin";
		}
	}

	public static class ATanOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.atan(a);
		}

		@Override
		public String getDefaultString()
		{
			return "tan";
		}
	}

	public static class CeilingOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.ceil(a);
		}

		@Override
		public String getDefaultString()
		{
			return "ceil";
		}
	}

	public static class CosOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.cos(a);
		}

		@Override
		public String getDefaultString()
		{
			return "cos";
		}
	}

	public static class DivideOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return a / b;
		}

		@Override
		public String getDefaultString()
		{
			return "/";
		}
	}

	public static class FloorOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.floor(a);
		}

		@Override
		public String getDefaultString()
		{
			return "floor";
		}
	}

	public static class GateOperation extends BinaryOperation
	{

		@Override
		public double calculate(double a, double b)
		{
			if (a < b)
				return 1;
			else
				return 0;
		}

		@Override
		public String getDefaultString()
		{
			return "gate";
		}

	}

	public static class MaxOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return Math.max(a, b);
		}

		@Override
		public String getDefaultString()
		{
			return "max";
		}
	}

	public static class MinOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return Math.min(a, b);
		}

		@Override
		public String getDefaultString()
		{
			return "min";
		}
	}

	public static class ModOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return a % b;
		}

		@Override
		public String getDefaultString()
		{
			return "%";
		}
	}

	public static class MultiplyOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return a * b;
		}

		@Override
		public String getDefaultString()
		{
			return "*";
		}
	}

	public static class NegateOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return -a;
		}

		@Override
		public String getDefaultString()
		{
			return "-";
		}
	}

	public static class PowOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return Math.pow(a, b);
		}

		@Override
		public String getDefaultString()
		{
			return "^";
		}
	}

	public static class RandomOperation extends BinaryOperation
	{
		private static Random random = new Random();

		@Override
		public double calculate(double a, double b)
		{
			return random.nextDouble() * (b - a) + a;
		}

		@Override
		public String getDefaultString()
		{
			return "rand";
		}

	}

	public static class RoundOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.round(a);
		}

		@Override
		public String getDefaultString()
		{
			return "round";
		}
	}

	public static class SignumOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.signum(a);
		}

		@Override
		public String getDefaultString()
		{
			return "signum";
		}
	}

	public static class SinOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.sin(a);
		}

		@Override
		public String getDefaultString()
		{
			return "sin";
		}
	}

	public static class SquareRoot extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.sqrt(a);
		}

		@Override
		public String getDefaultString()
		{
			return "sqrt";
		}
	}

	public static class SubtractOperation extends BinaryOperation
	{
		@Override
		public double calculate(double a, double b)
		{
			return a - b;
		}

		@Override
		public String getDefaultString()
		{
			return "-";
		}
	}

	public static class TanOperation extends UnaryOperation
	{
		@Override
		public double calculate(double a)
		{
			return Math.tan(a);
		}

		@Override
		public String getDefaultString()
		{
			return "tan";
		}
	}
}
