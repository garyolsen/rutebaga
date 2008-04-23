package rutebaga.commons.math;

@SuppressWarnings("unchecked")
public class IntVector2D implements GenericVector2D<Integer, IntVector2D>
{
	@Override
	public String toString()
	{
		return "<" + this.getX() + ", " + this.getY() + ">";
	}

	private static void minus(int[] a, GenericVector2D b, int[] dest)
	{
		dest[0] = a[0] - b.getX().intValue();
		dest[1] = a[1] - b.getY().intValue();
	}

	private static void opposite(int[] a, int[] dest)
	{
		dest[0] = -a[0];
		dest[1] = -a[1];
	}

	private static void over(int[] a, double f, int[] dest)
	{
		dest[0] = (int) (a[0] / f);
		dest[1] = (int) (a[1] / f);
	}

	private static void plus(int[] a, GenericVector2D b, int[] dest)
	{
		dest[0] = a[0] + b.getX().intValue();
		dest[1] = a[1] + b.getY().intValue();
	}
	
	private static void times(int[] a, double f, int[] dest)
	{
		dest[0] = (int) (a[0] * f);
		dest[1] = (int) (a[1] * f);
	}
	
	private int comp[] = new int[2];
	
	private double magnitude;
	private boolean mag_dirty = true;

	IntVector2D()
	{
		
	}
	IntVector2D(int comp[])
	{
		this.comp = comp;
	}
	
	public IntVector2D(int x, int y)
	{
		comp[0] = x;
		comp[1] = y;
	}
	
	IntVector2D accumulate(GenericVector2D other)
	{
		dirty();
		plus(this.comp, other, this.comp);
		return this;
	}

	IntVector2D detract(GenericVector2D other)
	{
		dirty();
		minus(this.comp, other, this.comp);
		return this;
	}
	
	void dirty()
	{
		mag_dirty = true;
	}
	
	IntVector2D divideBy(double div)
	{
		dirty();
		over(this.comp, div, this.comp);
		return this;
	}
	
	public double dot(GenericVector2D other)
	{
		return comp[0]*other.getX().intValue() + comp[1]*other.getY().intValue();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final IntVector2D other = (IntVector2D) obj;
		if (comp[0] != other.comp[0])
			return false;
		if (comp[1] != other.comp[1])
			return false;
		return true;
	}
	
	public Integer get(int idx)
	{
		return comp[idx];
	}
	
	public Vector2D getDirection()
	{
		getMagnitude();
		return new Vector2D(getX()/magnitude, getY()/magnitude);
	}
	
	public double getMagnitude()
	{
		if(mag_dirty)
		{
			magnitude = Math.sqrt(comp[0]*comp[0] + comp[1]*comp[1]);
			mag_dirty = false;
		}
		return magnitude;
	}
	
	public Integer getX()
	{
		return comp[0];
	}
	
	public Integer getY()
	{
		return comp[1];
	}
	
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + comp[0];
		result = PRIME * result + comp[1];
		return result;
	}
	
	public IntVector2D minus(GenericVector2D other)
	{
		IntVector2D rval = new IntVector2D();
		minus(this.comp, other, rval.comp);
		return rval;
	}
	
	IntVector2D multiplyBy(double f)
	{
		dirty();
		times(this.comp, f, this.comp);
		return this;
	}
	
	IntVector2D negate()
	{
		dirty();
		opposite(this.comp, this.comp);
		return this;
	}
	
	public IntVector2D opposite()
	{
		IntVector2D rval = new IntVector2D();
		opposite(this.comp, rval.comp);
		return rval;
	}
	
	public IntVector2D over(double div)
	{
		IntVector2D rval = new IntVector2D();
		over(this.comp, div, rval.comp);
		return rval;
	}
	
	public IntVector2D plus(GenericVector2D other)
	{
		IntVector2D rval = new IntVector2D();
		plus(this.comp, other, rval.comp);
		return rval;
	}
	
	void setX(Integer x)
	{
		dirty();
		comp[0] = x;
	}

	void setY(Integer y)
	{
		dirty();
		comp[1] = y;
	}
	
	public IntVector2D times(double f)
	{
		IntVector2D rval = new IntVector2D();
		times(this.comp, f, rval.comp);
		return rval;
	}
}
