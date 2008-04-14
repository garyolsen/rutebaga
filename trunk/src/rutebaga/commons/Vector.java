package rutebaga.commons;

import java.util.Arrays;

/**
 * A vector in space.
 * 
 * Vectors are Comparable. The comparison algorithm returns the comparison of
 * the first non-equal components of the two vectors, starting with the leftmost
 * component:
 *  < 0, 0, 0 > lt < 0, 0, 1 > < 0, 1, 0 > gt < 0, 0, 1 > < 0, 1, 1 > gt < 0, 1,
 * 0 >
 * 
 * @author Gary LosHuertos
 * 
 */
public class Vector implements Comparable<Vector> {

	private final double[] components;

	private Double magnitude;

	private final int dimension;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("< ");
		for (double component : components) {
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
	public Vector(double... components) {
		dimension = components.length;
		this.components = new double[dimension];
		for (int idx = 0; idx < components.length; idx++) {
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
	public Vector(double[] components, int start, int end) {
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
	private Vector(int dimension) {
		this.dimension = dimension;
		this.components = new double[dimension];
	}

	/**
	 * Returns the value of the dot product between this Vector and the Vector
	 * rhs. This value will be rhs.getManitude()*this.getMagnitude()*cos(theta),
	 * where theta is the angle between the two Vectors. (If both Vectors have
	 * getMagnitude()==1, then the result will be the cosine of the angle
	 * between the two vectors)
	 * 
	 * @param rhs
	 *            A Vector to compare with this Vector with the dot product.
	 * @return The value of the dot product between this Vector and the Vector
	 *         rhs.
	 * 
	 */
	public double dot(Vector rhs) {
		check(rhs);
		double rval = 0;
		for (int idx = 0; idx < dimension; idx++) {
			rval += components[idx] * rhs.components[idx];
		}
		return rval;
	}

	@Override
	public boolean equals(Object obj) {
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
		if (magnitude == null) {
			if (other.magnitude != null)
				return false;
		} else if (!magnitude.equals(other.magnitude))
			return false;
		return true;
	}

	/**
	 * Returns the component of this Vector at the specified index.
	 * 
	 * @param idx
	 *            Index of a component in this Vector.
	 * @return The Component of this Vector at the location specified by idx.
	 */
	public double get(int idx) {
		return components[idx];
	}

	/**
	 * The dimension of the vector is the number of orthogonal vector axes that
	 * define its space; it is the number of components.
	 * 
	 * @return the dimension of this vector
	 */
	public int getDimension() {
		return dimension;
	}

	/**
	 * The vector's direction is defined as the unit vector parallel to it.
	 * 
	 * @return the unit vector of this vector
	 */
	public Vector getDirection() {
		return this.times(1 / this.getMagnitude());
	}

	/**
	 * Returns the magnitude, or length, of this Vector.
	 * 
	 * @return The magnitude, or length, of this Vector.
	 */
	public double getMagnitude() {
		if (magnitude == null) {
			magnitude = 0.0;
			for (double c : components) {
				magnitude += c * c;
			}
			magnitude = Math.sqrt(magnitude);
		}
		return magnitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(components);
		result = prime * result + dimension;
		result = prime * result
				+ ((magnitude == null) ? 0 : magnitude.hashCode());
		return result;
	}

	/**
	 * Returns the difference between this Vector and the Vector passed in while
	 * leaving this Vector alone. Throws an IncompatibleDimensionException when
	 * the vectors are of different dimension.
	 * 
	 * @param rhs
	 *            A Vector to subtract from this Vector.
	 * @return The Vector that is the difference between this vector and the
	 *         Vector rhs that was passed in. (this Vector minus Vector rhs).
	 */
	public Vector minus(Vector rhs) {
		check(rhs);
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for (int idx = 0; idx < dimension; idx++) {
			components[idx] = this.components[idx] - rhs.components[idx];
		}
		return rval;
	}

	/**
	 * Leaves this Vector alone while returning a negative version of this
	 * Vector.
	 * 
	 * @return This Vector negated.
	 */
	public Vector negate() {
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for (int idx = 0; idx < dimension; idx++) {
			components[idx] = -this.components[idx];
		}
		return rval;
	}

	/**
	 * Returns the sum of this Vector and the Vector passed in while leaving
	 * this Vector alone. Throws an IncompatibleDimensionException if the
	 * Vectors are of different dimension.
	 * 
	 * @param rhs
	 *            A Vector to add to this Vector.
	 * @return The Vector that is the sum of this vector and the Vector rhs that
	 *         was passed in.
	 */
	public Vector plus(Vector rhs) {
		check(rhs);
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for (int idx = 0; idx < dimension; idx++) {
			components[idx] = this.components[idx] + rhs.components[idx];
		}
		return rval;
	}

	/**
	 * Returns the product of this Vector a scalar passed in while leaving this
	 * Vector alone.
	 * 
	 * @param factor
	 * @return The Vector that has been scaled by the specified factor.
	 */
	public Vector times(double factor) {
		Vector rval = new Vector(dimension);
		double components[] = rval.components;
		for (int idx = 0; idx < dimension; idx++) {
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
	private void check(Vector rhs) {
		if (dimension != rhs.dimension)
			throw new IncompatibleDimensionException(dimension, rhs.dimension);
	}

	/**
	 * @return an array containing the values of this array
	 */
	public double[] asArray() {
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
	public int compareTo(Vector other) {
		check(other);
		for (int idx = 0; idx < dimension; idx++) {
			if (Double.doubleToLongBits(this.components[idx]) != Double
					.doubleToLongBits(other.components[idx]))
				return new Double(this.components[idx]).compareTo(new Double(
						other.components[idx]));
		}
		return 0;
	}
}
