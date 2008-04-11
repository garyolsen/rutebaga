package rutebaga.test.commons;

import rutebaga.commons.CircleBounds;
import rutebaga.commons.Matrix;
import rutebaga.commons.Vector;

public class CircleBoundsTest
{
	public static void main(String ... args)
	{
		CircleBounds bounds = new CircleBounds(new Vector(0, 0), new Vector(5, 5));
		double[][] coords =
		{
		{ 0, 0},
		{ 1, 1},
		{ 6, 1},
		{ 5, 5},
		{ 6, 6} };
		Vector[] vectors = new Matrix(coords).asVectors();
		for(Vector v : vectors)
		{
			System.out.print(bounds.contains(v) + " for ");
			MatrixTest.print(v);
		}
	}
}
