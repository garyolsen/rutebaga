package rutebaga.commons;

public class CircleBounds extends Bounds
{
	private Vector center;
	private Vector radii;

	public CircleBounds(Vector center, Vector size)
	{
		this.center = center;
		this.radii = size;
	}

	public Vector getCenter()
	{
		return center;
	}

	public Vector getRadii()
	{
		return radii;
	}

	public void setCenter(Vector center)
	{
		this.center = center;
	}

	public void setRadii(Vector size)
	{
		this.radii = size;
	}

	public boolean contains(Vector v)
	{
		double value = 0;
		for(int idx=0; value <= 1 && idx<v.getDimension(); idx++)
		{
			double numerator = (v.get(idx)-center.get(idx));
			numerator *= numerator;
			value += numerator/radii.get(idx);
		}
		return value <= 1;
	}
}
