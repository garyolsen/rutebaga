package rutebaga.test.commons;

import rutebaga.commons.math.Vector;
import rutebaga.commons.math.Vector2DRectangle;

public class VectorRectTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Vector2DRectangle boundingBox = new Vector2DRectangle(new Vector(0, 0, 0),
				new Vector(2, 2, 2));
		for (Vector v : boundingBox.locationSet(1))
		{
			rutebaga.commons.Log.log(v);
		}
	}

}
