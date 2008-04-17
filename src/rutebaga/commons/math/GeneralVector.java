package rutebaga.commons.math;

@Deprecated
public interface GeneralVector extends Comparable<GeneralVector>
{

	String toString();

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
	double dot(GeneralVector rhs);

	boolean equals(Object obj);

	/**
	 * Returns the component of this Vector at the specified index.
	 * 
	 * @param idx
	 *            Index of a component in this Vector.
	 * @return The Component of this Vector at the location specified by idx.
	 */
	double get(int idx);

	/**
	 * The dimension of the vector is the number of orthogonal vector axes that
	 * define its space; it is the number of components.
	 * 
	 * @return the dimension of this vector
	 */
	int getDimension();

	/**
	 * The vector's direction is defined as the unit vector parallel to it.
	 * 
	 * @return the unit vector of this vector
	 */
	GeneralVector getDirection();

	/**
	 * Returns the magnitude, or length, of this Vector.
	 * 
	 * @return The magnitude, or length, of this Vector.
	 */
	double getMagnitude();

	int hashCode();

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
	GeneralVector minus(GeneralVector rhs);

	/**
	 * Leaves this Vector alone while returning a negative version of this
	 * Vector.
	 * 
	 * @return This Vector negated.
	 */
	GeneralVector negate();

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
	GeneralVector plus(GeneralVector rhs);

	/**
	 * Returns the product of this Vector a scalar passed in while leaving this
	 * Vector alone.
	 * 
	 * @param factor
	 * @return The Vector that has been scaled by the specified factor.
	 */
	GeneralVector times(double factor);

	/**
	 * @return an array containing the values of this array
	 */
	double[] asArray();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	int compareTo(GeneralVector other);

}