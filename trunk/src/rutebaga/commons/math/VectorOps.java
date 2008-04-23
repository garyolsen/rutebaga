package rutebaga.commons.math;

public class VectorOps
{
	public static double getAngle(GenericVector2D v)
	{
		return Math.atan2(v.getY().doubleValue(), v.getX().doubleValue());
	}
}
