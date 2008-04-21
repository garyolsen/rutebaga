package rutebaga.model.entity.npc.state;

import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;

/**
 * @author nicholasstamas
 * 
 */
public class Evade extends NPCState
{

	@Override
	public NPCState barter(NPCEntity npc)
	{
		npc.makeNewSpeech(npc, "I can't barter, I'm running away.");
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
		npc.makeNewSpeech(npc, "I'm scared of you! Ahhh!");
		return this;
	}

	@Override
	public NPCState tick(NPCEntity npc)
	{
		if (npc.isDead())
			return NPCState.dead;
		else
		{
			return this;
		}
	}

}
