package rutebaga.commons;

public class Log
{
	static boolean logging = false;

	public static void log(Object message)
	{
		if (logging)
			System.out.println(message);
	}
	
	public static void log()
	{
		log("\n");
	}
	
	
}
