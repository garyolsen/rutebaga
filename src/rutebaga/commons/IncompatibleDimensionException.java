package rutebaga.commons;

public class IncompatibleDimensionException extends RuntimeException {
	
	private static final long serialVersionUID = -2884085458913564910L;

	public IncompatibleDimensionException(int given, int required)
	{
		super("Incompatible dimensions: " + given + " provided; " + required + "required");
	}
	
}
