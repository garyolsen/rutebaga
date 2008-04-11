package rutebaga.commons;

public class NumberUtils
{
	public static boolean between(double value, double min, double max)
	{
		return value < max && value >= min;
	}
}
