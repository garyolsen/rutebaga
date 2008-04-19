package rutebaga.model.entity.npc.state;

import rutebaga.commons.math.MutableVector2D;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;

public class HostileWander extends NPCState {
	
	private int direction = 0;
	private int wait = 0;
	
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
			return NPCState.chase;
		else
		{
			double dx = direction % 2;
			double dy = direction % 2 + 1;
			boolean negative = (direction / 2) % 2 == 0;
			MutableVector2D impulse = new MutableVector2D(dx * 0.05, dy * 0.05);
			if (negative)
				impulse = impulse.negate();
	
			npc.applyImpulse(impulse);
	
			wait++;
			if (wait % 10 == 0)
				direction++;
		}
		
		return this;
	}

}

