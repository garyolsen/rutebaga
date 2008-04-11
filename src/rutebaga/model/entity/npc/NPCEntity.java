package rutebaga.model.entity.npc;

import rutebaga.model.entity.*;

/**
 * NPCEntity is a sub-type of {@link Entity} and adds needed functionality to an
 * Entity that is not player controlled. It supports a simple interface that
 * mostly delegates to its {@link NPCBrain}.
 * 
 * @see NPCBrain
 * 
 * @author Nicholas Stamas
 * 
 */
public class NPCEntity {

	private NPCEntityType type;

	private Entity target;
	private NPCBrain brain;

	protected NPCEntity(NPCEntityType type) {
		this.type = type;
	}

	/**
	 * A method fired by the environment. It will be forwarded to the
	 * {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 */
	public void tick() {
		type.tick(this);
	}

	/**
	 * Decides if the current target is in sight based on the
	 * {@link NPCEntityType}. Will be forwarded to {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 * @return True if the target is in sight.
	 */
	public boolean targetInSight() {
		return type.targetInSight(this);
	}

	/**
	 * Decides if the current target is in attack range based on the
	 * {@link NPCEntityType}. Will be forwarded to {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 * @return True if the target is in attack range.
	 */
	public boolean targetInRange() {
		return type.targetInRange(this);
	}

	/**
	 * Gives this NPCEntity a target.
	 * 
	 * @param target
	 *            {@link rutebaga.model.entity.Entity Entity} to target.
	 * @see rutebaga.model.entity.Entity
	 */
	public void setTarget(Entity target) {
		this.target = target;
	}

	/**
	 * Instructs this NPCEntity to speak. Will be forwarded to
	 * {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 */
	public void speak() {
		type.speak(this);
	}

	/**
	 * Instructs this NPCEntity to take a hostile gesture on the provided
	 * {@link rutebaga.model.entity.Entity Entity}. Will be forwarded to
	 * {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 * @param entity
	 *            The {@link rutebaga.model.entity.Entity Entity} to take a
	 *            hostile gesture against.
	 * @see rutebaga.model.entity.Entity
	 */
	public void takeHostileGesture(Entity entity) {
		type.takeHostileGesture(this, entity);
	}

	/**
	 * Instructs this NPCEntity to make a friendly gesture to the provided
	 * {@link rutebaga.model.entity.Entity Entity}. Will be forwarded to
	 * {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 * @param entity
	 *            The {@link rutebaga.model.entity.Entity Entity} to make a
	 *            friendly gesture towards.
	 * @see rutebaga.model.entity.Entity
	 */
	public void takeFriendlyGesture(Entity entity) {
		type.takeFriendlyGesture(this, entity);
	}

	/**
	 * Instructs this NPCEntity to barter. Will be forwarded to
	 * {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 */
	public void barter() {
		type.barter(this);
	}

	/**
	 * Returns this NPCEntity's current target.
	 * 
	 * @see rutebaga.model.entity.Entity
	 * @return The {@link rutebaga.model.entity.Entity Entity} currently being
	 *         targeted by this NPCEntity.
	 */
	public Entity getTarget() {
		return target;
	}

	/**
	 * Sets this NPCEntity's current {@link NPCBrain}.
	 * @param brain The {@link NPCBrain} to be inserted into this NPCEntity.
	 * @see NPCBrain
	 */
	public void setBrain(NPCBrain brain) {
		this.brain = brain;
	}

	/** 
	 * Returns this NPCEntity's current {@link NPCBrain}.
	 * @return The {@link NPCBrain} currently controlling this NPCEntity.
	 * @see NPCBrain
	 */
	public NPCBrain getBrain() {
		return this.brain;
	}
}
