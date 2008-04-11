package rutebaga.test.commons;

import rutebaga.commons.Matrix;
import rutebaga.commons.Vector;

public class MatrixTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		double[][] comps =
		{
		{ 0, 1, 2 },
		{ 3, 4, 5 },
		{ 6, 7, 8 },
		{ 9, 10, 11 } };
		Matrix mA = new Matrix(comps);
		Matrix mB = new Matrix(comps);
		Matrix mC = mA.plus(mB);
		print(mA);
		print(mB);
		print(mC);
		print(mA.times(2));
		double[][] compsA =
		{
		{ 1, 0, 2 },
		{ -1, 3, 1 } };
		double[][] compsB =
		{
		{ 3, 1 },
		{ 2, 1 },
		{ 1, 0 } };
		print(new Matrix(compsA).times(new Matrix(compsB)));

		for (Vector v : mA.asVectors())
		{
			print(v);
		}
		System.out.println();
		for (Vector v : new Matrix(mA.asVectors()).asVectors())
		{
			print(v);
		}
	}

	public static void print(Matrix m)
	{
		for (int y = 0; y < m.getDimY(); y++)
		{
			for (int x = 0; x < m.getDimX(); x++)
			{
				System.out.print(m.get(x, y) + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void print(Vector v)
	{
		for (int idx = 0; idx < v.getDimension(); idx++)
		{
			System.out.print(v.get(idx) + "\t");
		}
		System.out.println();
	}

}
