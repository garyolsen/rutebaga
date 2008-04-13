package rutebaga.commons;

/**
 * Simple numeric methods.
 * 
 * @author Gary
 * 
 */
public class NumberUtils
{
	/**
	 * Returns true if the value given falls between the maximum and minimum
	 * values provided.  The test is lower-bound inclusive, and upper-bound exclusive:
	 * 
	 * [ min, max )
	 * 
	 * @param value
	 *            The number to test.
	 * @param min
	 *            The minimum value.
	 * @param max
	 *            The maximum value.
	 * @return The boolean that is true if the value is greater than or equal to
	 *         min and less than max.
	 */
	public static boolean between(double value, double min, double max)
	{
		return value < max && value >= min;
	}
}
