package rutebaga.commons;

/**
 * A rectangular bounds.
 * 
 * @author Gary LosHuertos
 * 
 */
public class RectBounds extends Bounds
{
	private Vector size;

	private Vector lower;// lower left corner

	/**
	 * Constructs a RectBounds based on the specified lower-left corner and
	 * size.
	 * 
	 * @param lower
	 *            {@link Vector} representing the location of the lower-left
	 *            corner of this RectBounds.
	 * @param size
	 *            {@link Vector} representing the size of this RectBounds. This
	 *            {@link Vector} should point to the upper-right corner of the
	 *            RectBounds.
	 * @see Vector
	 */
	public RectBounds(Vector lower, Vector size)
	{
		this.size = size;
		this.lower = lower;
	}

	/**
	 * Returns true if this RectBounds contains the specified {@link Vector}.
	 * 
	 * @param v
	 *            {@link Vector} to be checked.
	 * @return A boolean representing {@link Vector} v's containment in this
	 *         RectBounds.
	 * @see Vector
	 */
	@Override
	public boolean contains(Vector v)
	{
		if (v == null)
			return false;
		boolean contains = true;
		// each element v[idx] must be
		// between lower[idx] and lower[idx]+size[idx]
		for (int idx = 0; contains && idx < v.getDimension(); idx++)
		{
			double value = v.get(idx);
			double lower = this.lower.get(idx);
			double upper = lower + size.get(idx);
			contains = contains && NumberUtils.between(value, lower, upper);
			contains = contains
					|| Double.doubleToLongBits(value) == Double
							.doubleToLongBits(upper);
		}
		return contains;
	}

	/**
	 * Returns the {@link Vector} representing this RectBounds' lower-left
	 * corner.
	 * 
	 * @return The {@link Vector} representing this RectBounds' lower-left
	 *         corner.
	 * @see Vector
	 */
	public Vector getLower()
	{
		return lower;
	}

	/**
	 * Returns the {@link Vector} representing this RectBounds' upper-right
	 * corner, or size.
	 * 
	 * @return The {@link Vector} representing this RectBounds' lower-left
	 *         corner.
	 * @see Vector
	 */
	public Vector getSize()
	{
		return size;
	}

	/**
	 * Sets the lower-left corner of this RectBounds to the specified
	 * {@link Vector}.
	 * 
	 * @param lower
	 *            The {@link Vector} representing the RectBounds' new lower-left
	 *            corner.
	 * @see Vector
	 */
	public void setLower(Vector lower)
	{
		this.lower = lower;
	}

	/**
	 * Sets the size, or upper-right corner of this RectBounds to the specified
	 * {@link Vector}.
	 * 
	 * @param size
	 *            The {@link Vector} representing the RectBounds' new
	 *            upper-right corner.
	 * @see Vector
	 */
	public void setSize(Vector size)
	{
		this.size = size;
	}

	@Override
	public VectorRectangle getBoundingBox()
	{
		return new VectorRectangle(lower, size);
	}

}
