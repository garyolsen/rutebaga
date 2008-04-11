package rutebaga.commons;

/**
 * An Exception thrown by {@link Vector#check(Vector)} when a Vector operation cannot
 * be completed because of incompatible dimensions.
 * @author Gary
 * @see Vector#check(Vector)
 */
public class IncompatibleDimensionException extends RuntimeException {
	
	private static final long serialVersionUID = -2884085458913564910L;

	public IncompatibleDimensionException(int given, int required)
	{
		super("Incompatible dimensions: " + given + " provided; " + required + "required");
	}
	
}
