package rutebaga.model.environment;

import java.util.HashMap;
import java.util.Map;

import rutebaga.commons.math.Vector;
import rutebaga.model.entity.Entity;

public class AStarNodeLocationManager
{
	private Map<Vector, AStarNodeLocationAdapter> nodes = new HashMap<Vector, AStarNodeLocationAdapter>();
	private Environment environment;
	private Entity entity;
	
	private AStarNodeLocationManager(Environment environment, Entity entity)
	{
		this.environment = environment;
		this.entity = entity;
	}
	
	/**
	 * Returns the AStarNodeLocationAdapter at vector v.
	 * If vector v is not in the entity's vision, or it does
	 * not exist in the environment, or something on vector v
	 * blocks it, the node returned will be null;
	 * 
	 * @param v
	 * @return The node at the given vector
	 */
	public AStarNodeLocationAdapter getNode(Vector v)
	{
		AStarNodeLocationAdapter node;
		if(!nodes.containsKey(v))
		{
			if (entity.canSee(v) && environment.exists(v) && environment.blockedAtTile(v, entity)) {
				node = new AStarNodeLocationAdapter(this, v);
			} else {
				node = null;
			}
			nodes.put(v, node);
		}
		return nodes.get(v);
	}

	public Environment getEnvironment()
	{
		return environment;
	}
}
