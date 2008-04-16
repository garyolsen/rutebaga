package rutebaga.model.entity.npc.state;

import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;
import rutebaga.model.environment.AStarNodeLocationAdapter;
import rutebaga.model.pathfinding.AStarSearch;

/**
 * @author nicholasstamas
 * 
 */
public class Chase extends NPCState
{
	
	private AStarSearch<AStarNodeLocationAdapter> aStarSearch;

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
		}
		else
		{
			manager = new AStarNodeLocationManager()
			aStarSearch.findPath(new AStarNodeLocationAdapter(), goalNode)
			return this;
		}
	}

}
