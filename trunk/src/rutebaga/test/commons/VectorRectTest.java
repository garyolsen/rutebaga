package rutebaga.test.commons;

import rutebaga.commons.Vector;
import rutebaga.commons.VectorRectangle;

public class VectorRectTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		VectorRectangle boundingBox = new VectorRectangle(new Vector(0, 0, 0), new Vector(2, 2, 2));
		for(Vector v : boundingBox.locationSet(1))
		{
			System.out.println(v);
		}
	}

}
