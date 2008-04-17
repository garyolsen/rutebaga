package rutebaga.commons;

import java.rmi.server.UID;

public class UIDProvider
{
	private static long longUID = 0;
	
	public static Object getUID()
	{
		UID rval = new UID();
		return rval;
	}
	
	public synchronized static long getLongUID()
	{
		return longUID++;
	}
}
