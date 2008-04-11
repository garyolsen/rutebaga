package rutebaga.commons;

/**
 * 
 * A class for representing Matrices.
 * 
 * @author Gary
 * 
 */
public class Matrix
{

	private final int dimX;

	private final int dimY;

	private double[] components;

	/**
	 * Creates a new empty Matrix of the specified dimensions.
	 * 
	 * @param dimX
	 *            The width of the new Matrix.
	 * @param dimY
	 *            The height of the new Matrix.
	 */
	public Matrix(int dimX, int dimY)
	{
		this.dimX = dimX;
		this.dimY = dimY;
		components = new double[dimX * dimY];
	}

	/**
	 * Creates a new Matrix composed of the specified components.
	 * 
	 * @param components
	 *            the components to be inserted into the new Matrix.
	 */
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

	/**
	 * Constructs a matrix from an array of vectors. Null values will be
	 * ignored.
	 * 
	 * @param vectors
	 */
	public Matrix(Vector[] vectors)
	{
		int dimX = vectors.length;
		Integer dimY = null;
		for (int idx = 0; idx < vectors.length; idx++)
			if (vectors[idx] == null)
				dimX--;
			else if (dimY == null)
				dimY = vectors[idx].getDimension();
		this.dimX = dimX;
		this.dimY = dimY;
		this.components = new double[dimX * dimY];
		for (int idx = 0, ptr = 0; idx < vectors.length; idx++)
		{
			if (vectors[idx] != null)
			{
				for (int loop = 0; loop < vectors[idx].getDimension(); loop++, ptr++)
				{
					this.components[ptr] = vectors[idx].get(loop);
				}
			}
		}
	}

	/**
	 * Returns the x dimension or width of this Matrix.
	 * 
	 * @return The width of the Matrix.
	 */
	public int getDimX()
	{
		return dimX;
	}

	/**
	 * Returns the y dimension or height of this Matrix.
	 * 
	 * @return The height of the Matrix.
	 */
	public int getDimY()
	{
		return dimY;
	}

	/**
	 * Returns the component of the Matrix corresponding tot he provided
	 * coordinates.
	 * 
	 * @param x
	 *            The x coordinate of the desired component.
	 * @param y
	 *            The y coordinate of the desired component.
	 * @return The component corresponding to the specified coordinates.
	 */
	public double get(int x, int y)
	{
		return components[resolve(x, y)];
	}

	/**
	 * Assigns the provided value to the component in the Matrix specified by
	 * the provided coordinates.
	 * 
	 * @param x
	 *            The x coordinate of the component to be set.
	 * @param y
	 *            The y coordinate of the component to be set.
	 * @param value
	 *            The value to set to the component corresponding tot he given
	 *            coordinates.
	 */
	public void set(int x, int y, double value)
	{
		this.components[resolve(x, y)] = value;
	}

	private int resolve(int x, int y)
	{
		return x * dimY + y;
	}

	/**
	 * Returns the sum of this Matrix and the provided Matrix while leaving this
	 * Matrix untouched.
	 * 
	 * @param rhs
	 *            A Matrix to add to this Matrix.
	 * @return The Matrix that is the sum of this Matrix and the Matrix rhs.
	 */
	public Matrix plus(Matrix rhs)
	{
		Matrix rval = new Matrix(this.dimX, this.dimY);
		for (int idx = 0; idx < dimX * dimY; idx++)
		{
			rval.components[idx] = this.components[idx] + rhs.components[idx];
		}
		return rval;
	}

	/**
	 * Returns the difference between this Matrix and the provided Matrix while
	 * leaving this Matrix untouched (this Matrix - rhs Matrix).
	 * 
	 * @param rhs
	 *            A Matrix to subtract from this Matrix.
	 * @return The Matrix that is the difference between this Matrix and the
	 *         Matrix rhs.
	 */
	public Matrix minus(Matrix rhs)
	{
		Matrix rval = new Matrix(this.dimX, this.dimY);
		for (int idx = 0; idx < dimX * dimY; idx++)
		{
			rval.components[idx] = this.components[idx] - rhs.components[idx];
		}
		return rval;
	}

	/**
	 * Returns the product of this Matrix and the provided Matrix while leaving
	 * this Matrix untouched (this Matrix x rhs Matrix).
	 * 
	 * @param rhs
	 *            A Matrix to multiply with this Matrix.
	 * @return The Matrix that is the product of this Matrix and the Matrix rhs.
	 */
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

	/**
	 * Returns the product of this Matrix and the provided scalar while leaving
	 * this Matrix untouched.
	 * 
	 * @param scalar
	 *            The value to scale this Matrix by.
	 * @return The Matrix that is the product of this Matrix and the scalar.
	 */
	public Matrix times(double scalar)
	{
		Matrix rval = new Matrix(this.dimX, this.dimY);
		for (int idx = 0; idx < dimX * dimY; idx++)
		{
			rval.components[idx] = this.components[idx] * scalar;
		}
		return rval;
	}

	/**
	 * Returns the {@link Vector}s that compose the columns of this Matrix.
	 * 
	 * @return An array of {@link Vector}s that compose the columns of this
	 *         Matrix.
	 * @see Vector
	 */
	public Vector[] asVectors()
	{
		Vector[] rval = new Vector[dimX];
		for (int idx = 0; idx < dimX; idx++)
		{
			rval[idx] = new Vector(components, idx * dimY, (idx + 1) * dimY);
		}
		return rval;
	}

}
