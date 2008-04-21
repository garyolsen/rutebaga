package rutebaga.model.environment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlagManager
{
	private Map<String, Boolean> flags = new HashMap<String, Boolean>();

	public void setFlag(String flag)
	{
		setFlag(flag, true);
	}

	public void clearFlag(String flag)
	{
		setFlag(flag, false);
	}

	public void setFlag(String flag, boolean value)
	{
		flags.put(flag, value);
	}

	public boolean isSet(String flag)
	{
		if (flags.containsKey(flag))
		{
			return flags.get(flag);
		}
		else
			return false;
	}
}
