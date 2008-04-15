package rutebaga.commons.math;


/**
 * A ellipsoid bounds.
 * 
 * @author Gary LosHuertos
 * 
 */
public class EllipseBounds extends Bounds
{
	private Vector center;

	private Vector radii;

	/**
	 * Constructs a new ElipseBounds using the specified center and radius.
	 * 
	 * @param center
	 *            {@link Vector} representing the the circle's center.
	 * @param size
	 *            {@link Vector} representing the radius of the ElipseBounds
	 *            such that if all the components are equal the Elipse will be a
	 *            spheroid. Otherwise the radii of the elipse will each be
	 *            represented by the corresponding component of this
	 *            {@link Vector}.
	 * @see Vector
	 */
	public EllipseBounds(Vector center, Vector size)
	{
		this.center = center;
		this.radii = size;
	}

	/**
	 * Constructs an ellipsoid bounds centered at the origin.
	 * 
	 * @param radii
	 *            the radii of the ellipsoid
	 */
	public EllipseBounds(Vector radii)
	{

		double arr[] = new double[radii.getDimension()];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = 0;
		}

		center = new Vector(arr);
		this.radii = radii;

	}

	/**
	 * Returns true if this {@link Vector} falls into these EllipseBounds.
	 * 
	 * @return The boolean corresponding to whether this {@link Vector} is
	 *         within the ElipseBounds.
	 * @param v
	 *            Whether or not this {@link Vector} is contained in the
	 *            ElipseBounds
	 */
	@Override
	public boolean contains(Vector v)
	{
		if (v == null)
			return false;
		double value = 0;
		for (int idx = 0; value <= 1 && idx < v.getDimension(); idx++)
		{
			double numerator = (v.get(idx) - center.get(idx));
			numerator *= numerator;
			value += numerator / (radii.get(idx) * radii.get(idx));
		}
		return value <= 1;
	}

	@Override
	public VectorRectangle getBoundingBox()
	{
		return new VectorRectangle(center.minus(radii), radii.times(2));
	}

	/**
	 * Returns the center of this EllipseBounds.
	 * 
	 * @return A {@link Vector} representing the center of this EllipseBounds.
	 * @see Vector
	 */
	public Vector getCenter()
	{
		return center;
	}

	/**
	 * Returns the radii of this ElipseBounds.
	 * 
	 * @return A {@link Vector} representing the radii of this ElipseBounds.
	 *         Each component of the {@link Vector} represents the elipse's
	 *         radius in the corresponding direction.
	 * @see Vector
	 */
	public Vector getRadii()
	{
		return radii;
	}

	/**
	 * Sets the center of this EllipseBounds to the specified {@link Vector}.
	 * 
	 * @param center
	 *            The {@link Vector} representing the new center of the
	 *            ElipseBounds.
	 * @see Vector
	 */
	public void setCenter(Vector center)
	{
		this.center = center;
	}

	/**
	 * Sets the radii of this EllipseBounds to the radii corresponding to the
	 * components of the specified {@link Vector}.
	 * 
	 * @see Vector
	 * @param size
	 *            The {@link Vector} with components corresponding to the
	 *            EllipseBound's new radii.
	 */
	public void setRadii(Vector size)
	{
		this.radii = size;
	}
}
