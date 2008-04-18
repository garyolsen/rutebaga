package rutebaga.commons.math;

@SuppressWarnings("unchecked")
public class MutableVector2D extends Vector2D implements
		GenericMutableVector2D<Double, Vector2D, MutableVector2D>
{

	public MutableVector2D()
	{
		super();
	}

	public MutableVector2D(double[] comp)
	{
		super(comp);
	}

	public MutableVector2D(GenericVector2D vector)
	{
		super(vector);
	}

	public MutableVector2D(double x, double y)
	{
		super(x, y);
	}

	@Override
	public MutableVector2D accumulate(GenericVector2D other)
	{
		return (MutableVector2D) super.accumulate(other);
	}

	@Override
	public MutableVector2D detract(GenericVector2D other)
	{
		return (MutableVector2D) super.detract(other);
	}

	@Override
	public MutableVector2D negate()
	{
		return (MutableVector2D) super.negate();
	}

	@Override
	public MutableVector2D divideBy(double div)
	{
		return (MutableVector2D) super.divideBy(div);
	}

	@Override
	public MutableVector2D multiplyBy(double f)
	{
		return (MutableVector2D) super.multiplyBy(f);
	}

	public void setX(Double x)
	{
		super.setX(x);
	}

	public void setY(Double y)
	{
		super.setY(y);
	}
	
	public MutableVector2D becomeUnitVector()
	{
		return divideBy(this.getMagnitude());
	}

}
