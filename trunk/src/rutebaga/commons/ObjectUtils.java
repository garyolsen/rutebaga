package rutebaga.commons;

/**
 * Utility class for statically accessed operations relating to objects.
 * 
 * @author Gary LosHuertos
 * 
 */
public class ObjectUtils
{
	/**
	 * Convenience method to avoid <code>NullPointerException</code>.
	 * 
	 * @param o1
	 *            object to compare
	 * @param o2
	 *            object to compare
	 * @return true if the objects are .equals() or both null, false otherwise
	 * 
	 * @see Object
	 */
	public static boolean equals(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
			return true;
		if (o1 == null || o2 == null)
			return false;
		return o1.equals(o2);
	}
}
