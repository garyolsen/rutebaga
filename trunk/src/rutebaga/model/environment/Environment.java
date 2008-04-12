package rutebaga.model.environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.Vector;

public class Environment
{
	private Set<Instance> instances;
	private Map<Vector, InstanceSet> tileCache = new HashMap<Vector, InstanceSet>();
	
	protected Map<Vector, InstanceSet> getTileCache()
	{
		return tileCache;
	}
}
