package rutebaga.commons.math;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Base class for a discrete bounds within space.
 * 
 * Bounds must be discrete; a bounds such as "all areas below y=2" is not
 * sufficient (as a location set could not possibly be generated).
 * 
 * @author Gary LosHuertos
 * 
 */
public abstract class Bounds2D
{
	/**
	 * Tests whether or not the given vector is within the bounds.
	 * 
	 * This operation is stable when v is null.
	 * 
	 * @param v
	 *            the vector to check
	 * @return true if v is within these bounds, false otherwise
	 */
	public abstract <T extends Number, U extends GenericVector2D<T, U>> boolean contains(
			U v);
	
	public abstract <T extends Number, U extends GenericVector2D<T, U>> boolean contains(
			U v, GenericVector2D offset);

	/**
	 * Tests if the {@link Vector Vectors} that are the columns in the provided
	 * {@link Matrix} are contained by these Bounds. Returns another Matrix
	 * who's columns are only the Vectors from the provided passed Matrix that
	 * are contained in these Bounds.
	 * 
	 * @param m
	 *            {@link Matrix} who's columns are {@link Vector Vectors} to be
	 *            tested for containment in these Bounds.
	 * @return The {@link Matrix} who's columns are {@link Vector Vectors} that
	 *         exist within these Bounds.
	 * @see Matrix
	 * @see Vector
	 */
	@Deprecated
	public Matrix filter(Matrix m)
	{
//		Vector[] vectors = m.asVectors();
//
//		for (int idx = 0; idx < vectors.length; idx++)
//		{
//			if (!contains(vectors[idx]))
//				vectors[idx] = null;
//		}
//
//		return new Matrix(vectors);
		return null;
	}

	/**
	 * Filters the given set of {@link Vector Vectors} into a set containing
	 * only Vectors within this Bounds.
	 * 
	 * @param vectors
	 *            The sample Vector set.
	 * @return the Vector set within this Bounds.
	 */
	public <T extends Number, U extends GenericVector2D<T, U>> Collection<U> filter(
			Collection<U> vectors)
	{
		ArrayList<U> rval = new ArrayList<U>();
		for (U vector : vectors)
		{
			if (this.contains(vector))
				rval.add(vector);
		}
		return rval;
	}

	/**
	 * Filters the given set of {@link Vector Vectors} into a set containing
	 * only Vectors within this Bounds, displaced by an offset.
	 * 
	 * @param vectors
	 *            The sample Vector set.
	 * @param offset
	 *            The offset to use.
	 * @return The Vector set within this Bounds.
	 */
	public <T extends Number, U extends GenericVector2D<T, U>> Collection<U> filter(
			Collection<U> vectors, GenericVector2D<?, ?> offset)
	{
		ArrayList<U> rval = new ArrayList<U>();
		for (U vector : vectors)
		{
			if (this.contains(vector, offset))
				rval.add(vector);
		}
		return rval;
	}

	/**
	 * Determines the rectangular bounding box of this Bounds.
	 * 
	 * This bounding box is defined as the smallest surface that can fully
	 * contain this Bounds. Each face must be orthogonal to, or parallel to,
	 * every {@link Vector} axis of this coordinate system.
	 * 
	 * @return The bounding box of this Bounds.
	 */
	public abstract Vector2DRectangle getBoundingBox();

	/**
	 * Returns a set of the locations within this Bounds.
	 * 
	 * @param scale
	 *            Controls the resolution of the location set returned. For any
	 *            {@link Vector} in the returned set, the closest another Vector
	 *            can be is the distance of one basis Vector scaled by this
	 *            scale.
	 * @return A set of location Vectors that are within this Bounds.
	 */
	public Collection<Vector2D> locationSet(double scale)
	{
		Collection<Vector2D> rval = getBoundingBox().locationSet(scale);
		return filter(rval);
	}
	
	public Collection<IntVector2D> intLocationSet()
	{
		Collection<IntVector2D> rval = getBoundingBox().intLocationSet();
		return filter(rval);
	}
}
