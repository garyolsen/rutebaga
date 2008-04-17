package rutebaga.commons.math;

public class Vector2D implements GenericVector2D<Double, Vector2D>
{
	private static void minus(double[] a, GenericVector2D b, double[] dest)
	{
		dest[0] = a[0] - b.getX().doubleValue();
		dest[1] = a[1] - b.getY().doubleValue();
	}

	private static void opposite(double[] a, double[] dest)
	{
		dest[0] = -a[0];
		dest[1] = -a[1];
	}

	private static void over(double[] a, double f, double[] dest)
	{
		dest[0] = a[0] / f;
		dest[1] = a[1] / f;
	}

	private static void plus(double[] a, GenericVector2D b, double[] dest)
	{
		dest[0] = a[0] + b.getX().doubleValue();
		dest[1] = a[1] + b.getY().doubleValue();
	}
	
	private static void times(double[] a, double f, double[] dest)
	{
		dest[0] = a[0] * f;
		dest[1] = a[1] * f;
	}
	
	private double comp[] = new double[2];
	
	private double magnitude;
	private boolean mag_dirty = true;

	Vector2D()
	{
		
	}
	Vector2D(double comp[])
	{
		this.comp = comp;
	}
	
	public Vector2D(double x, double y)
	{
		comp[0] = x;
		comp[1] = y;
	}
	
	Vector2D accumulate(GenericVector2D other)
	{
		dirty();
		plus(this.comp, other, this.comp);
		return this;
	}

	Vector2D detract(GenericVector2D other)
	{
		dirty();
		minus(this.comp, other, this.comp);
		return this;
	}
	
	void dirty()
	{
		mag_dirty = true;
	}
	
	Vector2D divideBy(double div)
	{
		dirty();
		over(this.comp, div, this.comp);
		return this;
	}
	
	public double dot(Vector2D other)
	{
		return comp[0]*other.comp[0] + comp[1]*other.comp[1];
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
		final Vector2D other = (Vector2D) obj;
		if (Double.doubleToLongBits(comp[0]) != Double.doubleToLongBits(other.comp[0]))
			return false;
		if (Double.doubleToLongBits(comp[1]) != Double.doubleToLongBits(other.comp[1]))
			return false;
		return true;
	}
	
	public Double get(int idx)
	{
		return comp[idx];
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
	
	public Double getX()
	{
		return comp[0];
	}
	
	public Double getY()
	{
		return comp[1];
	}
	
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(comp[0]);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(comp[1]);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	public Vector2D minus(GenericVector2D other)
	{
		Vector2D rval = new Vector2D();
		minus(this.comp, other, rval.comp);
		return rval;
	}
	
	Vector2D multiplyBy(double f)
	{
		dirty();
		times(this.comp, f, this.comp);
		return this;
	}
	
	Vector2D negate()
	{
		dirty();
		opposite(this.comp, this.comp);
		return this;
	}
	
	public Vector2D opposite()
	{
		Vector2D rval = new Vector2D();
		opposite(this.comp, rval.comp);
		return rval;
	}
	
	public Vector2D over(double div)
	{
		Vector2D rval = new Vector2D();
		over(this.comp, div, rval.comp);
		return rval;
	}
	
	public Vector2D plus(GenericVector2D other)
	{
		Vector2D rval = new Vector2D();
		plus(this.comp, other, rval.comp);
		return rval;
	}
	
	void setX(double x)
	{
		dirty();
		comp[0] = x;
	}
	
	void setY(double y)
	{
		dirty();
		comp[1] = y;
	}

	public Vector2D times(double f)
	{
		Vector2D rval = new Vector2D();
		times(this.comp, f, rval.comp);
		return rval;
	}
}
