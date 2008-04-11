package rutebaga.model.entity.npc.state;

import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;

/**
 * @author nicholasstamas
 *
 */
public class Chase extends NPCState {

	public NPCState barter(NPCEntity npc) {
		System.out.println("I can't barter and chase at the same time");
		return this;
	}

	public NPCState makeFriendly(NPCEntity npc) {
		return NPCState.wander;
	}

	public NPCState makeHostile(NPCEntity npc) {
		return this;
	}

	public NPCState speak(NPCEntity npc) {
		System.out.println("I'm gonna get you!");
		return this;
	}

	public NPCState tick(NPCEntity npc) {
		if ( npc.targetInSight() && npc.targetInRange() )
		{
			return NPCState.attack;
		}
		else
		{
			//npc.chaseTarget();
			return this;
		}
	}


}
