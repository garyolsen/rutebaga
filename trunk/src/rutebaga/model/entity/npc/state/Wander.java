package rutebaga.model.entity.npc.state;

import java.util.Random;

import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;
import rutebaga.commons.math.MutableVector2D;

/**
 * @author nicholasstamas
 * 
 */
public class Wander extends NPCState
{
	
	private int direction = 0;
	private int wait = 0;
	private Random rand = new Random();
	
	@Override
	public NPCState barter(NPCEntity npc)
	{
		rutebaga.commons.Log.log("I'm willing to barter.");
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
		rutebaga.commons.Log.log("I'm wandering around...");
		return this;
	}

	@Override
	public NPCState tick(NPCEntity npc)
	{
		npc.walk(new MutableVector2D((rand.nextFloat()-0.5)*0.2, (rand.nextFloat()-0.5)*0.2));

		wait++;
		if (wait % 10 == 0)
			direction++;
		
		return this;
	}

}
