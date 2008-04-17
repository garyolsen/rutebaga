package rutebaga.commons.math;

public class MutableIntVector2D extends IntVector2D implements GenericMutableVector2D<Integer, IntVector2D, MutableIntVector2D>
{

	public MutableIntVector2D()
	{
		super();
	}

	public MutableIntVector2D(int x, int y)
	{
		super(x, y);
	}

	@Override
	public MutableIntVector2D accumulate(GenericVector2D other)
	{
		return (MutableIntVector2D) super.accumulate(other);
	}

	@Override
	public MutableIntVector2D detract(GenericVector2D other)
	{
		return (MutableIntVector2D) super.detract(other);
	}

	@Override
	public MutableIntVector2D negate()
	{
		return (MutableIntVector2D) super.negate();
	}

	@Override
	public MutableIntVector2D divideBy(double div)
	{
		return (MutableIntVector2D) super.divideBy(div);
	}

	@Override
	public MutableIntVector2D multiplyBy(double f)
	{
		return (MutableIntVector2D) super.multiplyBy(f);
	}

	@Override
	public void setX(Integer x)
	{
		super.setX(x);
	}

	@Override
	public void setY(Integer y)
	{
		super.setY(y);
	}

}
