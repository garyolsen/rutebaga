package rutebaga.model.entity.npc.state;

import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;

/**
 * @author nicholasstamas
 *
 */
public class Evade extends NPCState{

	public NPCState barter(NPCEntity npc) {
		System.out.println("I can't barter, I'm running away.");
		return this;
	}

	public NPCState makeFriendly(NPCEntity npc) {
		return NPCState.wander;
	}

	public NPCState makeHostile(NPCEntity npc) {
		return this;
	}

	public NPCState speak(NPCEntity npc) {
		System.out.println("I'm scared of you! Ahhh!");
		return this;
	}

	public NPCState tick(NPCEntity npc) {
		//TODO: evasion logic
		return this;
	}


}
