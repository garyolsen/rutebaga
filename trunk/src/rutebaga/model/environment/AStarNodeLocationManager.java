package rutebaga.model.environment;

import java.util.HashMap;
import java.util.Map;

import rutebaga.commons.math.IntVector2D;
import rutebaga.model.entity.Entity;

public class AStarNodeLocationManager
{
	private Map<IntVector2D, AStarNodeLocationAdapter> nodes = new HashMap<IntVector2D, AStarNodeLocationAdapter>();
	private Environment environment;
	private Entity entity;
	private IntVector2D target;
	
	public AStarNodeLocationManager(Environment environment, Entity entity, IntVector2D target)
	{
//		rutebaga.commons.Log.log("Entity is at " + entity.getTile());
		this.environment = environment;
		this.entity = entity;
		this.target = target;
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
	public AStarNodeLocationAdapter getNode(IntVector2D v)
	{
		AStarNodeLocationAdapter node;
		if(!nodes.containsKey(v))
		{
			if (entity.canSee(v) && environment.exists(v) && (!environment.blockedAtTile(v, entity) || v.equals(target))) {
//				rutebaga.commons.Log.log("adding node " + v);
				node = new AStarNodeLocationAdapter(this, v);
			} else {
//				rutebaga.commons.Log.log("not adding node " + v);
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
