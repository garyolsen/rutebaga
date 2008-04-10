package rutebaga.commons;

public class Matrix
{

	private final int dimX;
	private final int dimY;

	private double[] components;

	public Matrix(int dimX, int dimY)
	{
		this.dimX = dimX;
		this.dimY = dimY;
		components = new double[dimX * dimY];
	}

	public Matrix(double[][] components)
	{
		this(components.length, components[0].length);
		for (int x = 0; x < dimX; x++)
		{
			for (int y = 0; y < dimY; y++)
			{
				this.set(x, y, components[x][y]);
			}
		}
	}

	public int getDimX()
	{
		return dimX;
	}

	public int getDimY()
	{
		return dimY;
	}

	public double get(int x, int y)
	{
		return components[resolve(x, y)];
	}

	public void set(int x, int y, double value)
	{
		this.components[resolve(x, y)] = value;
	}

	private int resolve(int x, int y)
	{
		return x * dimY + y;
	}

	public Matrix plus(Matrix rhs)
	{
		Matrix rval = new Matrix(this.dimX, this.dimY);
		for (int idx = 0; idx < dimX * dimY; idx++)
		{
			rval.components[idx] = this.components[idx] + rhs.components[idx];
		}
		return rval;
	}

	public Matrix minus(Matrix rhs)
	{
		Matrix rval = new Matrix(this.dimX, this.dimY);
		for (int idx = 0; idx < dimX * dimY; idx++)
		{
			rval.components[idx] = this.components[idx] - rhs.components[idx];
		}
		return rval;
	}

	public Matrix times(Matrix rhs)
	{
		Matrix rval = new Matrix(this.dimX, rhs.dimY);
		for (int y = 0; y < rval.dimY; y++)
		{
			for (int x = 0; x < rval.dimX; x++)
			{
				double value = 0;
				for (int idx = 0; idx < this.dimY; idx++)
				{
					double item = rhs.get(idx, y);
					item *= this.get(x, idx);
					value += item;
				}
				rval.set(x, y, value);
			}
		}
		return rval;
	}

	public Matrix times(double scalar)
	{
		Matrix rval = new Matrix(this.dimX, this.dimY);
		for (int idx = 0; idx < dimX * dimY; idx++)
		{
			rval.components[idx] = this.components[idx] * scalar;
		}
		return rval;
	}
	
	public Vector[] asVectors()
	{
		Vector[] rval = new Vector[dimX];
		for(int idx=0; idx<dimX; idx++)
		{
			rval[idx] = new Vector(components, idx*dimY, (idx+1)*dimY);
		}
		return rval;
	}

}
