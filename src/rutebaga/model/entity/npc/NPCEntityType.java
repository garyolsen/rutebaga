package rutebaga.model.entity.npc;

import rutebaga.model.entity.Entity;

/**
 * @author nicholasstamas
 * 
 */
public class NPCEntityType
{

	/**
	 * Creates and returns a new NPCEntity instance with a SimpleBrain.
	 * 
	 * @return NPCEntity
	 */
	public NPCEntity make()
	{

		NPCEntity newNPCEntity = new NPCEntity(this);
		newNPCEntity.setBrain(new NPCSimpleBrain());
		return newNPCEntity;

	}

	/**
	 * Forwards the tick to the passed NPCEntity's brain.
	 * 
	 * @param npc
	 */
	public void tick(NPCEntity npc)
	{
		// XXX breaks LoD
		npc.getBrain().tick(npc);
	}

	/**
	 * Returns true if an NPCEntity's current target is within its range of
	 * vision.
	 * 
	 * @param npc
	 * @return boolean
	 */
	public boolean targetInSight(NPCEntity npc)
	{
		return true;
	}

	/**
	 * Returns true if an NPCEntity has an ability that can be used from its
	 * distance to its current target.
	 * 
	 * @param npc
	 * @return boolean
	 */
	public boolean targetInRange(NPCEntity npc)
	{
		return true;
	}

	/**
	 * Sets the current target for the NPCEntity.
	 * 
	 * @param npc
	 * @param entity
	 */
	public void setTarget(NPCEntity npc, Entity entity)
	{
		npc.setTarget(entity);
	}

	/**
	 * Causes NPCEntity to say something.
	 * 
	 * @param npc
	 */
	public void speak(NPCEntity npc)
	{
		// XXX breaks LoD
		npc.getBrain().speak(npc);
	}

	/**
	 * Attempt to barter with NPCEntity.
	 * 
	 * @param npc
	 */
	public void barter(NPCEntity npc)
	{
		// XXX breaks LoD
		npc.getBrain().barter(npc);
	}

	/**
	 * Sets an Entity's target and puts it in a hostile state.
	 * 
	 * @param npc
	 * @param entity
	 */
	public void takeHostileGesture(NPCEntity npc, Entity entity)
	{
		npc.setTarget(entity);
		// TXXX breaks LoD
		npc.getBrain().makeHostile(npc);
	}

	/**
	 * Sets an Entity's target and puts it a friendly state.
	 * 
	 * @param npc
	 * @param entity
	 */
	public void takeFriendlyGesture(NPCEntity npc, Entity entity)
	{
		npc.setTarget(entity);
		// XXX breaks LoD
		npc.getBrain().makeFriendly(npc);
	}

}
