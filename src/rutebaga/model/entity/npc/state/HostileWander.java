package rutebaga.model.entity.npc.state;

import java.util.Random;

import rutebaga.commons.math.MutableVector2D;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;

public class HostileWander extends NPCState {
	
	private MutableVector2D direction = new MutableVector2D(0,0);
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
		if (npc.targetInSight())
		{
			rutebaga.commons.Log.log("I can't see...going into chase state!");
			return NPCState.chase;
		}
		else
		{	
//			wait++;
//			System.out.println(wait);
//			if ( (wait % 10) == 0)
//			{
//				direction = new MutableVector2D((rand.nextFloat()-0.5)*0.2, (rand.nextFloat()-0.5)*0.2);
//			}
//			
//			npc.walk(direction);
			
			rutebaga.commons.Log.log("Hostile wandering around.");
			return this;
		}
		
	}

}

