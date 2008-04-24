package rutebaga.model.entity.npc.state;

import java.util.List;
import java.util.Random;

import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.MutableVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.Entity;
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

	private int lifetime = 0;
	private IntVector2D lastTile;
	private List<AStarNodeLocationAdapter> lastPath;

	@Override
	public NPCState barter(NPCEntity npc)
	{
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
	public NPCState speak(NPCEntity npc, Entity entity)
	{
		entity.recieveSpeech(npc, "I'm gonna get you!");
		return this;
	}

	@Override
	public NPCState tick(NPCEntity npc)
	{
		if (npc.isDead())
			return NPCState.dead;
		else
		{
			//System.out.println("target in sigh: " + npc.targetInSight());
			//System.out.println("target in range: " + npc.targetInRange());
			//System.out.println("target: " + npc.getTarget());
			if (npc.targetInSight() && npc.targetInRange())
			{
				return NPCState.attack;

			}
			else if (!(npc.getTarget() == null) && (npc.targetInSight()))
			{
				//System.out.println("chase");
				manager = new AStarNodeLocationManager(npc.getEnvironment(),
						npc, npc.getTargetTile());
				List<AStarNodeLocationAdapter> path = aStarSearch.findPath(
						new AStarNodeLocationAdapter(manager, npc.getTile()),
						new AStarNodeLocationAdapter(manager, npc
								.getTargetTile()));

				IntVector2D moveTo = npc.getTile();

				if (!(path == null) && !(path.isEmpty()))
				{
					moveTo = path.get(0).getTile();
					path.remove(0);
				}

				MutableVector2D moveVector = new MutableVector2D(moveTo);
				moveVector.detract(npc.getTile()).divideBy(2.0).accumulate(
						npc.getTile()).detract(npc.getCoordinate());
				moveVector.becomeUnitVector().multiplyBy(0.03);

				npc.walk(moveVector);

				lifetime = 100;
				lastPath = path;
				lastTile = npc.getTile();

				return this;
			}
			if (!(lastPath == null) && !lastPath.isEmpty() && (lifetime > 0))
			{
				rutebaga.commons.Log.log(lastTile);
				//System.out.println("I lost sight of you. Interest level at: "	+ lifetime);
				lifetime--;
				MutableVector2D moveVector;
				rutebaga.commons.Log
						.log("Pathfinding to last known position...");
				IntVector2D moveTo = lastPath.get(0).getTile();
				if ((npc.getTile().getX() != lastTile.getX())
						&& (npc.getTile().getY() != lastTile.getY()))
				{
					rutebaga.commons.Log.log("Removing tile from list");
					lastTile = npc.getTile();
					lastPath.remove(0);
				}
				moveVector = new MutableVector2D(moveTo);
				moveVector.detract(npc.getTile()).divideBy(2.0).accumulate(
						npc.getTile()).detract(npc.getCoordinate());
				moveVector.becomeUnitVector().multiplyBy(0.03);
				npc.walk(moveVector);
				return this;
			}
			else
			{
				// System.out.println("Going into hostile wander state!");
				return NPCState.hostileWander;
			}
		}

	}

}
