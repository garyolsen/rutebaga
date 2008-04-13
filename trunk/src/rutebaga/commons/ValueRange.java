package rutebaga.commons;

/**
 * Defines a range of real numbers.
 * 
 * A null bound signifies a bound of infinity.
 * 
 * The range is bound-inclusive (unless, of course, the bound is infinity).
 * 
 * @author Gary LosHuertos
 * 
 */
public class ValueRange
{
	private Double min;
	private Double max;

	public ValueRange(Double min, Double max)
	{
		super();
		this.min = min;
		this.max = max;
	}

	public Double getMin()
	{
		return min;
	}

	public void setMin(Double min)
	{
		this.min = min;
	}

	public Double getMax()
	{
		return max;
	}

	public void setMax(Double max)
	{
		this.max = max;
	}

	/**
	 * @param value
	 *            the value to test
	 * @return whether or not the value is within this range
	 */
	public boolean contains(double value)
	{
		if (min != null)
			if (value < min)
				return false;
		if (max != null)
			if (value > max)
				return false;
		return true;
	}
}
