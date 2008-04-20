package rutebaga.test.commons;

import rutebaga.commons.math.Matrix;
import rutebaga.commons.math.RectBounds2D;
import rutebaga.commons.math.Vector2D;

public class RectBoundsTest
{

	/**
	 * @param args
	 */
	/*
	public static void main(String[] args)
	{
		RectBounds2D bounds = new RectBounds2D(new Vector2D(1, 2), new Vector2D(5,
				5));
		double[][] coords =
		{
		{ 0, 0, 0 },
		{ 1, 1, 1 },
		{ 6, 1, 1 },
		{ 5, 5, 5 },
		{ 6, 6, 6 } };
		Vector2D[] vectors = new Matrix(coords).asVectors();
		for (Vector2D v : vectors)
		{
			System.out.print(bounds.contains(v) + " for ");
			MatrixTest.print(v);
		}

		for (Vector v : bounds.filter(new Matrix(vectors)).asVectors())
		{
			MatrixTest.print(v);
		}

		rutebaga.commons.Log.log();

		for (Vector v : bounds.locationSet(2))
		{
			rutebaga.commons.Log.log(v);
		}

	}
	*/

}
