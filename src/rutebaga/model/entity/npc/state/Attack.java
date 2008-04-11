package rutebaga.model.entity.npc.state;

import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;

/**
 * @author nicholasstamas
 * 
 */
public class Attack extends NPCState {

	public NPCState barter(NPCEntity npc) {
		System.out.println("I'm super mad. I'm in no mood for bartering!");
		return this;
	}

	public NPCState makeFriendly(NPCEntity npc) {
		return NPCState.wander;
	}

	public NPCState makeHostile(NPCEntity npc) {
		return this;
	}

	public NPCState speak(NPCEntity npc) {
		System.out.println("I'm attacking you! Be very afraid!");
		return this;
	}

	public NPCState tick(NPCEntity npc) {
		if ( npc.targetInRange() && npc.targetInSight() ) {
			//npc.attackTarget();
			return this;
		}
		else if ( npc.targetInSight() ){
			return NPCState.chase;
		}
		else {
			return NPCState.wander;
		}
	}

}
