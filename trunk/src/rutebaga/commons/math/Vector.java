package rutebaga.commons.math;

import java.util.Arrays;


/**
 * A vector in space.
 * 
 * Vectors are Comparable. The comparison algorithm returns the comparison of
 * the first non-equal components of the two vectors, starting with the leftmost
 * component: < 0, 0, 0 > lt < 0, 0, 1 > < 0, 1, 0 > gt < 0, 0, 1 > < 0, 1, 1 >
 * gt < 0, 1, 0 >
 * 
 * @author Gary LosHuertos
 * 
 */
@Deprecated
public class Vector implements GeneralVector
{

	private final double[] components;

	private Double magnitude;

	private final int dimension;

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("< ");
		for (double component : components)
		{
			sb.append(component);
			sb.append(" ");
		}
		sb.append(">");
		return sb.toString();
	}

	/**
	 * Creates a new Vector using the specified components.
	 * 
	 * @param components
	 *            The components to insert into the new Vector.
	 */
	public Vector(double... components)
	{
		dimension = components.length;
		this.components = new double[dimension];
		for (int idx = 0; idx < components.length; idx++)
		{
			this.components[idx] = components[idx];
		}
	}

	/**
	 * Constructs a new vector using components from a specified range of an
	 * array.
	 * 
	 * @param components
	 * @param start
	 * @param end
	 */
	public Vector(double[] components, int start, int end)
	{
		this.dimension = end - start;
		this.components = new double[dimension];
		for (int idx = start; idx < end; idx++)
			this.components[idx - start] = components[idx];
	}

	/**
	 * Creates a new, empty Vector of the specified dimension.
	 * 
	 * @param int
	 *            Dimension of the new Vector.
	 */
	private Vector(int dimension)
	{
		this.dimension = dimension;
		this.components = new double[dimension];
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#dot(rutebaga.commons.math.Vector)
	 */
	public double dot(GeneralVector rhs)
	{
		check(rhs);
		double rval = 0;
		for (int idx = 0; idx < dimension; idx++)
		{
			rval += components[idx] * rhs.get(idx);
		}
		return rval;
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vector other = (Vector) obj;
		if (!Arrays.equals(components, other.components))
			return false;
		if (dimension != other.dimension)
			return false;
		if (magnitude == null)
		{
			if (other.magnitude != null)
				return false;
		}
		else if (!magnitude.equals(other.magnitude))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#get(int)
	 */
	public double get(int idx)
	{
		return components[idx];
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#getDimension()
	 */
	public int getDimension()
	{
		return dimension;
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#getDirection()
	 */
	public Vector getDirection()
	{
		return this.times(1 / this.getMagnitude());
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#getMagnitude()
	 */
	public double getMagnitude()
	{
		if (magnitude == null)
		{
			magnitude = 0.0;
			for (double c : components)
			{
				magnitude += c * c;
			}
			magnitude = Math.sqrt(magnitude);
		}
		return magnitude;
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(components);
		result = prime * result + dimension;
		result = prime * result
				+ ((magnitude == null) ? 0 : magnitude.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#minus(rutebaga.commons.math.Vector)
	 */
	public Vector minus(GeneralVector rhs)
	{
		check(rhs);
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for (int idx = 0; idx < dimension; idx++)
		{
			components[idx] = this.components[idx] - rhs.get(idx);
		}
		return rval;
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#negate()
	 */
	public Vector negate()
	{
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for (int idx = 0; idx < dimension; idx++)
		{
			components[idx] = -this.components[idx];
		}
		return rval;
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#plus(rutebaga.commons.math.Vector)
	 */
	public Vector plus(GeneralVector rhs)
	{
		check(rhs);
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for (int idx = 0; idx < dimension; idx++)
		{
			components[idx] = this.components[idx] + rhs.get(idx);
		}
		return rval;
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#times(double)
	 */
	public Vector times(double factor)
	{
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for (int idx = 0; idx < dimension; idx++)
		{
			components[idx] = this.components[idx] * factor;
		}
		return rval;
	}

	/**
	 * Throws a IncompatibleDimensionException if the Vector passed in is not of
	 * the same dimension as this Vector.
	 * 
	 * @param rhs
	 *            A vector whose dimension is to be compared to the dimension of
	 *            this Vector.
	 */
	private void check(GeneralVector rhs)
	{
		if (dimension != rhs.getDimension())
			throw new IncompatibleDimensionException(dimension, rhs.getDimension());
	}

	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#asArray()
	 */
	public double[] asArray()
	{
		double[] rval = new double[dimension];
		for (int idx = 0; idx < rval.length; idx++)
			rval[idx] = components[idx];
		return rval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see rutebaga.commons.math.GeneralVector#compareTo(rutebaga.commons.math.Vector)
	 */
	public int compareTo(GeneralVector other)
	{
		check(other);
		for (int idx = 0; idx < dimension; idx++)
		{
			if (Double.doubleToLongBits(this.components[idx]) != Double
					.doubleToLongBits(other.get(idx)))
				return Double.compare(this.components[idx], other.get(idx));
		}
		return 0;
	}
}
