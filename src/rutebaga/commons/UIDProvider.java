package rutebaga.commons;

import java.rmi.server.UID;

public class UIDProvider
{
	public static Object getUID()
	{
		UID rval = new UID();
		return rval;
	}
}
