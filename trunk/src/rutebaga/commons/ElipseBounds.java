package rutebaga.commons;

/**
 * A circular bounds.
 * 
 * @author Gary LosHuertos
 * 
 */
public class ElipseBounds extends Bounds {
	private Vector center;

	private Vector radii;

	/**
	 * Constructs a new ElipseBounds using the specified center and radius.
	 * 
	 * @param center
	 *            {@link Vector} representing the the circle's center.
	 * @param size
	 *            {@link Vector} representing the radius of the ElipseBounds
	 *            such that if all the components are equal the Elipse will be a
	 *            spheroid. Otherwise the radii of the elipse will each be
	 *            represented by the corresponding component of this
	 *            {@link Vector}.
	 * @see Vector
	 */
	public ElipseBounds(Vector center, Vector size) {
		this.center = center;
		this.radii = size;
	}

	/**
	 * Returns the center of this ElipseBounds.
	 * 
	 * @return A {@link Vector} representing the center of this ElipseBounds.
	 * @see Vector
	 */
	public Vector getCenter() {
		return center;
	}

	/**
	 * Returns the radii of this ElipseBounds.
	 * 
	 * @return A {@link Vector} representing the radii of this ElipseBounds.
	 *         Each component of the {@link Vecor} represents the elipse's
	 *         radius in the corresponding direction.
	 * @see Vector
	 */
	public Vector getRadii() {
		return radii;
	}

	/**
	 * Sets the center of this ElipseBounds to the specified {@link Vector}.
	 * 
	 * @param center
	 *            The {@link Vector} representing the new center of the
	 *            ElipseBounds.
	 * @see Vector
	 */
	public void setCenter(Vector center) {
		this.center = center;
	}

	/**
	 * Sets the radii of this ElipseBounds to the radii corresponding to the
	 * components of the specified {@link Vector}.
	 * 
	 * @see Vector
	 * @param size
	 *            The {@link Vector} with components corresponding to the
	 *            ElipseBound's new radii.
	 */
	public void setRadii(Vector size) {
		this.radii = size;
	}

	/**
	 * Returns true if this {@link Vector} falls into these ElipeBounds.
	 * @return The boolean corresponding to wether this {@link Vector} is within
	 *         the ElipseBounds.
	 * @param v
	 *            Wether or not this {@link Vector} is contained in the
	 *            ElipseBounds
	 */
	public boolean contains(Vector v) {
		double value = 0;
		for (int idx = 0; value <= 1 && idx < v.getDimension(); idx++) {
			double numerator = (v.get(idx) - center.get(idx));
			numerator *= numerator;
			value += numerator / radii.get(idx);
		}
		return value <= 1;
	}
}
