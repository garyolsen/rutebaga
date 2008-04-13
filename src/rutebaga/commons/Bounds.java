package rutebaga.commons;

import java.util.HashSet;
import java.util.Set;

/**
 * Base class for a discrete bounds within space.
 * 
 * As mentioned, a bounds must be discrete; a bounds such as "all areas below
 * y=2" is not sufficient (as a location set could not possibly be generated).
 * 
 * @author Gary LosHuertos
 * 
 */
public abstract class Bounds
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
	public abstract boolean contains(Vector v);

	/**
	 * Tests if the {@link Vector Vectors} that are the columns in the provided
	 * {@link Matrix} are contained by these Bounds and returns another Matrix
	 * who's columns are only the {@link Vector Vectors} from the provided
	 * {@link Matrix} that are contained in these Bounds.
	 * 
	 * @param m
	 *            {@link Matrix} who's columns are {@link Vector}s to be tested
	 *            for containment in these Bounds.
	 * @return The {@link Matrix} who's columns are {@link Vector}s that exist
	 *         within these Bounds.
	 * @see Matrix
	 * @see Vector
	 */
	public Matrix filter(Matrix m)
	{
		Vector[] vectors = m.asVectors();

		for (int idx = 0; idx < vectors.length; idx++)
		{
			if (!contains(vectors[idx]))
				vectors[idx] = null;
		}

		return new Matrix(vectors);
	}

	/**
	 * Filters the given set of vectors into a set containing only vectors
	 * within this bounds.
	 * 
	 * @param vectors
	 *            the sample vector set
	 * @return the vector set within the bounds
	 */
	public Set<Vector> filter(Set<Vector> vectors)
	{
		Set<Vector> rval = new HashSet<Vector>();
		for (Vector vector : vectors)
		{
			if (this.contains(vector))
				rval.add(vector);
		}
		return rval;
	}

	/**
	 * Filters the given set of vectors into a set containing only vectors
	 * within this bounds, displaced by an offset.
	 * 
	 * @param vectors
	 *            the sample vector set
	 * @param offset
	 *            the offset to use
	 * @return the vector set within the bounds
	 */
	public Set<Vector> filter(Set<Vector> vectors, Vector offset)
	{
		Set<Vector> rval = new HashSet<Vector>();
		for (Vector vector : vectors)
		{
			if (this.contains(vector.plus(offset)))
				rval.add(vector);
		}
		return rval;
	}

	/**
	 * Determines the rectangular bounding box of this bounds.
	 * 
	 * This bounding box is defined as the smallest surface that can fully
	 * contain these bounds, for which each face must be orthogonal to or
	 * parallel to every vector axis of the coordinate system.
	 * 
	 * @return the bounding box of this bounds
	 */
	public abstract VectorRectangle getBoundingBox();

	/**
	 * @param scale
	 * @return
	 */
	public Set<Vector> locationSet(double scale)
	{
		Set<Vector> rval = getBoundingBox().locationSet(scale);
		return filter(rval);
	}
}
