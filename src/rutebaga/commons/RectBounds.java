package rutebaga.commons;

public class RectBounds extends Bounds
{
	private Vector size;
	private Vector lower;

	public RectBounds(Vector lower, Vector size)
	{
		this.size = size;
		this.lower = lower;
	}

	public boolean contains(Vector v)
	{
		boolean contains = true;
		// each element v[idx] must be
		// between lower[idx] and lower[idx]+size[idx]
		for (int idx = 0; contains && idx < v.getDimension(); idx++)
		{
			double value = v.get(idx);
			double lower = this.lower.get(idx);
			double upper = lower + size.get(idx);
			contains = contains && NumberUtils.between(value, lower, upper);
		}
		return contains;
	}

	public Vector getLower()
	{
		return lower;
	}

	public Vector getSize()
	{
		return size;
	}

	public void setLower(Vector lower)
	{
		this.lower = lower;
	}

	public void setSize(Vector size)
	{
		this.size = size;
	}

}
