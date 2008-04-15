package rutebaga.model.environment;

import java.util.HashMap;
import java.util.Map;

import rutebaga.commons.Vector;

public class AStarNodeLocationManager
{
	private Map<Vector, AStarNodeLocationAdapter> nodes = new HashMap<Vector, AStarNodeLocationAdapter>();
	private Environment environment;
	
	private AStarNodeLocationManager(Environment environment)
	{
		super();
		this.environment = environment;
	}

	public AStarNodeLocationAdapter getNode(Vector v)
	{
		if(!nodes.containsKey(v))
		{
			AStarNodeLocationAdapter node = new AStarNodeLocationAdapter(this, v);
			nodes.put(v, node);
		}
		return nodes.get(v);
	}

	public Environment getEnvironment()
	{
		return environment;
	}
}
