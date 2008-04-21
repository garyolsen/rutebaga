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
	
	private int wait = 0;
	private Random rand = new Random();
	private MutableVector2D direction=new MutableVector2D((rand.nextFloat()-0.5)*0.2, (rand.nextFloat()-0.5)*0.2);
	private boolean pausing = false;
	
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
		wait++;
		if (wait % 50 == 0){
			direction=new MutableVector2D((rand.nextFloat()-0.5)*0.2, (rand.nextFloat()-0.5)*0.2);
			pausing=rand.nextBoolean();
		}
		if(!pausing)
			npc.walk(direction);
		return this;
	}

}
