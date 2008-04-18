package rutebaga.model.entity.npc;

import rutebaga.model.entity.npc.state.Attack;
import rutebaga.model.entity.npc.state.Chase;
import rutebaga.model.entity.npc.state.Evade;
import rutebaga.model.entity.npc.state.Wander;

/**
 * Encapsulates state-specific behavior of a and defines state transitions. For
 * example, a default state might be wander, but hostile action might facilitate
 * a transition to the chase or the attack state.
 * 
 * @author Nicholas Stamas
 * @see NPCEntity
 */
public abstract class NPCState
{
	static NPCState initialState;
	
	protected static final NPCState chase = new Chase();
	
	protected static final NPCState wander = new Wander();

	// XXX: because it's
	// the first state,
	// the initialState will be wander. Probably not too good.

	protected static final NPCState evade = new Evade();

	protected static final NPCState attack = new Attack();

	protected NPCState()
	{
		if (initialState == null)
			initialState = this;
	}

	/**
	 * Returns the state that results from a tick.
	 * 
	 * @param npc
	 *            The {@link NPCEntity} whos state is in question.
	 * @return The resulting NPCState.
	 * @see NPCEntity
	 */
	public abstract NPCState tick(NPCEntity npc);

	/**
	 * Returns the state that results from speaking.
	 * 
	 * @param npc
	 *            The {@link NPCEntity} whos state is in question.
	 * @return The resulting NPCState.
	 * @see NPCEntity
	 */
	public abstract NPCState speak(NPCEntity npc);

	/**
	 * Returns the state that results from bartering.
	 * 
	 * @param npc
	 *            The {@link NPCEntity} whos state is in question.
	 * @return The resulting NPCState.
	 * @see NPCEntity
	 */
	public abstract NPCState barter(NPCEntity npc);

	/**
	 * Returns the state that results from becoming hostile.
	 * 
	 * @param npc
	 *            The {@link NPCEntity} whos state is in question.
	 * @return The resulting NPCState.
	 * @see NPCEntity
	 */
	public abstract NPCState makeHostile(NPCEntity npc);

	/**
	 * Returns the state that results from becoming friendly.
	 * 
	 * @param npc
	 *            The {@link NPCEntity} whos state is in question.
	 * @return The resulting NPCState.
	 * @see NPCEntity
	 */
	public abstract NPCState makeFriendly(NPCEntity npc);

}
