package rutebaga.model.entity.npc.state;

import java.util.Random;

import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;
import rutebaga.commons.math.Vector;

/**
 * @author nicholasstamas
 * 
 */
public class Wander extends NPCState
{

	@Override
	public NPCState barter(NPCEntity npc)
	{
		System.out.println("I'm willing to barter.");
		return this;
	}

	@Override
	public NPCState makeFriendly(NPCEntity npc)
	{
		return this;
	}

	@Override
	public NPCState makeHostile(NPCEntity npc)
	{
		return NPCState.chase;
	}

	@Override
	public NPCState speak(NPCEntity npc)
	{
		System.out.println("I'm wandering around...");
		return this;
	}

	@Override
	public NPCState tick(NPCEntity npc)
	{
		Random rand = new Random();
		npc.applyMomentum(new Vector( rand.nextDouble(), rand.nextDouble() ));
		return this;
	}

}
