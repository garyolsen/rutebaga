package rutebaga.model.entity.npc.state;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;

public class Dead extends NPCState {

	@Override
	public NPCState barter(NPCEntity npc) {
		return NPCState.dead;
	}

	@Override
	public NPCState makeFriendly(NPCEntity npc) {
		return NPCState.dead;
	}

	@Override
	public NPCState makeHostile(NPCEntity npc) {
		return NPCState.dead;
	}

	@Override
	public NPCState speak(NPCEntity npc, Entity entity) {
		return NPCState.dead;
	}

	@Override
	public NPCState tick(NPCEntity npc) {
		return NPCState.dead;
	}

}
