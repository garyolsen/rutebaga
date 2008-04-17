package rutebaga.commons.math;


/**
 * A ellipse bounds.
 * 
 * @author Gary LosHuertos
 * 
 */
public class EllipseBounds2D extends Bounds2D
{
	private Vector2D center;

	private Vector2D radii;

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
	public EllipseBounds2D(Vector2D center, Vector2D size)
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
	public EllipseBounds2D(Vector2D radii)
	{
		center = new Vector2D(0, 0);
		this.radii = radii;

	}

	@Override
	public Vector2DRectangle getBoundingBox()
	{
		//TODO cache this operation
		return new Vector2DRectangle(center.minus(radii), radii.times(2));
	}

	/**
	 * Returns the center of this EllipseBounds.
	 * 
	 * @return A {@link Vector} representing the center of this EllipseBounds.
	 * @see Vector
	 */
	public Vector2D getCenter()
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
	public Vector2D getRadii()
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
	public void setCenter(Vector2D center)
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
	public void setRadii(Vector2D size)
	{
		this.radii = size;
	}

	@Override
	public <T extends Number, U extends GenericVector2D<T, U>> boolean contains(U v)
	{
		if (v == null)
			return false;
		double x = v.getX().doubleValue();
		double y = v.getY().doubleValue();
		double t1 = (x-center.getX())/radii.getX();
		double t2 = (y-center.getY())/radii.getY();
		return (t1*t1 + t2*t2) <= 1;
	}

	@Override
	public <T extends Number, U extends GenericVector2D<T, U>> boolean contains(U v, GenericVector2D offset)
	{
		if (v == null)
			return false;
		double x = v.getX().doubleValue()+offset.getX().doubleValue();
		double y = v.getY().doubleValue()+offset.getY().doubleValue();
		double t1 = (x-center.getX())/radii.getX();
		double t2 = (y-center.getY())/radii.getY();
		return (t1*t1 + t2*t2) <= 1;
	}
}
