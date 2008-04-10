package rutebaga.commons;

/**
 * A vector in 2D-space.  Vectors are immutable.
 * 
 * @author Gary LosHuertos
 * 
 */
public class Vector {

	private final double x;
	private final double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Vector plus(Vector rhs) {
		return new Vector(this.x + rhs.x, this.y + rhs.y);
	}

	public Vector minus(Vector rhs) {
		return new Vector(this.x - rhs.x, this.y - rhs.y);
	}

	/**
	 * @return	the dot product of the two vectors
	 */
	public double dot(Vector rhs) {
		return this.x * rhs.x + this.y * rhs.y;
	}

	public Vector negate() {
		return new Vector(-this.x, -this.y);
	}

}
