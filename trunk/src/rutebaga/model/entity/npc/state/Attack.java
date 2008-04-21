package rutebaga.model.entity.npc.state;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;

/**
 * @author nicholasstamas
 * 
 */
public class Attack extends NPCState
{

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
		entity.recieveSpeech(npc, "I'm attacking you! Be very afraid!");
		return this;
	}

	@Override
	public NPCState tick(NPCEntity npc)
	{
		if (npc.isDead())
			return NPCState.dead;
		else
		{
			if (npc.targetInRange() && npc.targetInSight())
			{
				// npc.attackTarget();
				return this;
			}
			else if (npc.targetInSight())
			{
				return NPCState.chase;
			}
			else
			{
				return NPCState.wander;
			}
		}
	}

}
