package rutebaga.commons.math;

/**
 * A rectangular bounds.
 * 
 * @author Gary LosHuertos
 * 
 */
@SuppressWarnings("unchecked")
public class RectBounds2D extends Bounds2D
{
	private Vector2D size;

	private Vector2D lower;// lower left corner

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
	public RectBounds2D(Vector2D lower, Vector2D size)
	{
		this.size = size;
		this.lower = lower;
	}


	public RectBounds2D(Vector2D size)
	{
		this.lower = new Vector2D(size.getX()/-2, size.getY()/-2);
		this.size = new Vector2D(size.getX(), size.getY());
	}


	/**
	 * Returns the {@link Vector} representing this RectBounds' lower-left
	 * corner.
	 * 
	 * @return The {@link Vector} representing this RectBounds' lower-left
	 *         corner.
	 * @see Vector
	 */
	public Vector2D getLower()
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
	public Vector2D getSize()
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
	public void setLower(Vector2D lower)
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
	public void setSize(Vector2D size)
	{
		this.size = size;
	}

	@Override
	public Vector2DRectangle getBoundingBox()
	{
		return new Vector2DRectangle(lower, size);
	}

	@Override
	public <T extends Number, U extends GenericVector2D<T, U>> boolean contains(U v)
	{
		double x = v.getX().doubleValue();
		double y = v.getY().doubleValue();
		//TODO cache these for performance
		double xMax = lower.getX() + size.getX();
		double yMax = lower.getY() + size.getY();
		return x >= lower.getX() && x <= xMax && y >= lower.getY() && y <= yMax; 
	}

	@Override
	public <T extends Number, U extends GenericVector2D<T, U>> boolean contains(U v, GenericVector2D offset)
	{
		double x = v.getX().doubleValue() - offset.getX().doubleValue();
		double y = v.getY().doubleValue() - offset.getY().doubleValue();
		//TODO cache these for performance
		double xMax = lower.getX() + size.getX();
		double yMax = lower.getY() + size.getY();
		return x >= lower.getX() && x <= xMax && y >= lower.getY() && y <= yMax;
	}

}
