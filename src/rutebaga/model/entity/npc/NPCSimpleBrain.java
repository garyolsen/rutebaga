package rutebaga.model.entity.npc;

import rutebaga.model.entity.Entity;

public class NPCSimpleBrain implements NPCBrain
{

	private NPCState currentState = NPCState.initialState;

	public void barter(NPCEntity npc)
	{
		currentState = currentState.barter(npc);
	}

	public void makeFriendly(NPCEntity npc)
	{
		currentState = currentState.makeFriendly(npc);
	}

	public void makeHostile(NPCEntity npc)
	{
		currentState = currentState.makeHostile(npc);
	}

	public void speak(NPCEntity npc, Entity entity)
	{
		currentState = currentState.speak(npc, entity);
	}

	public void tick(NPCEntity npc)
	{
		currentState = currentState.tick(npc);
	}

}
