package rutebaga.model.entity.npc;

/**
 * NPCBrain specifies the interface that a ConcreteNPCBrain must support,
 * allowing seamless swapping of different ConcreteNPCBrains. The operations it
 * supports are abstractions of inputs to an NPC brain.
 * 
 * @author Nicholas Stamas
 */
public interface NPCBrain
{

	/**
	 * A method called on by the {@link NPCEntity} owning this NPCBrain to
	 * decide what to do at each clock tick.
	 * 
	 * @see NPCEntity
	 * @param npc
	 *            The {@link NPCEntity} controlled by this NPCBrain.
	 */
	public void tick(NPCEntity npc);

	/**
	 * A method called on by the {@link NPCEntity} owning this NPCBrain to
	 * decide what to do when speaking.
	 * 
	 * @param npc
	 *            The {@link NPCEntity} controlled by this NPCBrain.
	 */
	public void speak(NPCEntity npc);

	/**
	 * A method called on by the {@link NPCEntity} owning this NPCBrain to
	 * decide what to do when bartering.
	 * 
	 * @param npc
	 *            The {@link NPCEntity} controlled by this NPCBrain.
	 */
	public void barter(NPCEntity npc);

	/**
	 * A method called on by the {@link NPCEntity} owning this NPCBrain to
	 * decide what to do when made hostile.
	 * 
	 * @param npc
	 *            The {@link NPCEntity} controlled by this NPCBrain.
	 */
	public void makeHostile(NPCEntity npc);

	/**
	 * A method called on by the {@link NPCEntity} owning this NPCBrain to
	 * decide what to do when made friendly.
	 * 
	 * @param npc
	 *            The {@link NPCEntity} controlled by this NPCBrain.
	 */
	public void makeFriendly(NPCEntity npc);

}
