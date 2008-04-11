package rutebaga.model.entity.npc.state;

import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;

/**
 * @author nicholasstamas
 *
 */
public class Wander extends NPCState{

	public NPCState barter(NPCEntity npc) {
		System.out.println("I'm willing to barter.");
		return this;
	}

	public NPCState makeFriendly(NPCEntity npc) {
		return this;
	}

	public NPCState makeHostile(NPCEntity npc) {
		return NPCState.chase;
	}

	public NPCState speak(NPCEntity npc) {
		System.out.println("I'm wandering around...");
		return this;
	}

	public NPCState tick(NPCEntity npc) {
		//npc.randomWalk();
		return this;
	}


}
