package rutebaga.commons;

public class IncompatibleDimensionException extends RuntimeException {
	
	public IncompatibleDimensionException(int given, int required)
	{
		super("Incompatible dimensions: " + given + " provided; " + required + "required");
	}
	
}
