package rutebaga.test.commons;

import rutebaga.commons.math.Matrix;
import rutebaga.commons.math.RectBounds;
import rutebaga.commons.math.Vector;

public class RectBoundsTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		RectBounds bounds = new RectBounds(new Vector(1, 2, 3), new Vector(5,
				5, 5));
		double[][] coords =
		{
		{ 0, 0, 0 },
		{ 1, 1, 1 },
		{ 6, 1, 1 },
		{ 5, 5, 5 },
		{ 6, 6, 6 } };
		Vector[] vectors = new Matrix(coords).asVectors();
		for (Vector v : vectors)
		{
			System.out.print(bounds.contains(v) + " for ");
			MatrixTest.print(v);
		}

		for (Vector v : bounds.filter(new Matrix(vectors)).asVectors())
		{
			MatrixTest.print(v);
		}

		System.out.println();

		for (Vector v : bounds.locationSet(2))
		{
			System.out.println(v);
		}

	}

}
