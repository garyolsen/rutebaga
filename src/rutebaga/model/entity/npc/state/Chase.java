package rutebaga.model.entity.npc.state;

import java.util.List;

import rutebaga.commons.math.IntVector2D;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;
import rutebaga.model.environment.AStarNodeLocationAdapter;
import rutebaga.model.environment.AStarNodeLocationManager;
import rutebaga.model.pathfinding.AStarSearch;

/**
 * @author nicholasstamas
 * 
 */
public class Chase extends NPCState
{
	
	private AStarSearch<AStarNodeLocationAdapter> aStarSearch = new AStarSearch<AStarNodeLocationAdapter>();
	private AStarNodeLocationManager manager;

	@Override
	public NPCState barter(NPCEntity npc)
	{
		System.out.println("I can't barter and chase at the same time");
		return this;
	}

	@Override
	public NPCState makeFriendly(NPCEntity npc)
	{
		return NPCState.wander;
	}

	@Override
	public NPCState makeHostile(NPCEntity npc)
	{
		return this;
	}

	@Override
	public NPCState speak(NPCEntity npc)
	{
		System.out.println("I'm gonna get you!");
		return this;
	}

	@Override
	public NPCState tick(NPCEntity npc)
	{
		if (npc.targetInSight() && npc.targetInRange())
		{
			return NPCState.attack;
			
		} else if (!(npc.getTarget() == null))
		{
			manager = new AStarNodeLocationManager(npc.getEnvironment(), npc, npc.getTargetTile());
			List<AStarNodeLocationAdapter> path = aStarSearch.findPath(new AStarNodeLocationAdapter(manager, npc.getTile()), new AStarNodeLocationAdapter(manager, npc.getTargetTile()));
			
			IntVector2D moveTo = npc.getTile();
			
			if ( !(path == null) && !(path.isEmpty()) )
				moveTo = path.get(0).getTile();
			
			npc.applyImpulse((moveTo.minus(npc.getTile())).times(0.07));
			
			return this;
		}
		return NPCState.wander;
	}

}
