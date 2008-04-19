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
//		double dx = direction % 2;
//		double dy = direction % 2 + 1;
//		boolean negative = (direction / 2) % 2 == 0;
//		MutableVector2D impulse = new MutableVector2D(dx * 0.05, dy * 0.05);
//		if (negative)
//			impulse = impulse.negate();
		
		npc.applyImpulse(new MutableVector2D((rand.nextFloat()-0.5)*0.2, (rand.nextFloat()-0.5)*0.2));

		wait++;
		if (wait % 10 == 0)
			direction++;
		
		return this;
	}

}
