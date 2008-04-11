package rutebaga.commons;

/**
 * Interface for declaring a bounds within space.
 * 
 * @author Gary LosHuertos
 * 
 */
public abstract class Bounds {
	public abstract boolean contains(Vector v);

	/**
	 * Tests if the {@link Vector}s that are the columns in {@link Matrix}
	 * m are contained by these Bounds and returns another Matrix who's columns
	 * are only the {@link Vector}s from {@link Matrix} m that are contained in
	 * these Bounds.
	 * 
	 * @param m
	 *            {@link Matrix} who's columns are {@link Vector}s to be tested
	 *            for containment in these Bounds.
	 * @return The {@link Matrix} who's columns are {@link Vector}s that exist
	 *         within these Bounds.
	 *        @see Matrix
	 *        @see Vector
	 */
	public Matrix filter(Matrix m) {
		Vector[] vectors = m.asVectors();

		for (int idx = 0; idx < vectors.length; idx++) {
			if (!contains(vectors[idx]))
				vectors[idx] = null;
		}

		return new Matrix(vectors);
	}
}
