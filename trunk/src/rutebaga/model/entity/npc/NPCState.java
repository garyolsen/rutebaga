package rutebaga.model.entity.npc;

import rutebaga.model.entity.npc.state.Attack;
import rutebaga.model.entity.npc.state.Chase;
import rutebaga.model.entity.npc.state.Evade;
import rutebaga.model.entity.npc.state.Wander;

/**
 * @author nicholasstamas
 *
 */
public abstract class NPCState {
	static NPCState initialState;
	protected static final NPCState wander = new Wander();
	protected static final NPCState chase = new Chase();
	protected static final NPCState evade = new Evade();
	protected static final NPCState attack = new Attack();
	
	protected NPCState() {
		if (initialState == null) initialState = this;
	}
	
	public abstract NPCState tick( NPCEntity npc );
	public abstract NPCState speak( NPCEntity npc );
	public abstract NPCState barter( NPCEntity npc );
	public abstract NPCState makeHostile( NPCEntity npc );
	public abstract NPCState makeFriendly( NPCEntity npc );
	
}
