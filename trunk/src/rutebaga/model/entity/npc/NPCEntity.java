package rutebaga.model.entity.npc;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.MutableVector2D;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.Team;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.entity.stats.Stats;
import rutebaga.model.environment.InstanceType;

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
public class NPCEntity<T extends NPCEntity<T>> extends CharEntity<T>
{

	private Entity target;
	private NPCBrain brain;
	private boolean pausing = true;
	private Random rand = new Random();
	private MutableVector2D direction=new MutableVector2D((rand.nextFloat()-0.5)*0.2, (rand.nextFloat()-0.5)*0.2);
	
	public NPCEntity(InstanceType<T> type)
	{
		super(type);
		this.brain = new NPCSimpleBrain();
	}

	/**
	 * A method fired by the environment. It will be forwarded to the
	 * {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 */
	public void tick()
	{
		super.tick();
		brain.tick(this);
	}

	/**
	 * Decides if the current target is in sight based on the
	 * {@link NPCEntityType}. Will be forwarded to {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 * @return True if the target is in sight.
	 */
	public boolean targetInSight()
	{
		return getVision().inActiveSet(target);
	}

	/**
	 * Decides if the current target is in attack range based on the
	 * {@link NPCEntityType}. Will be forwarded to {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 * @return True if the target is in attack range.
	 */
	public boolean targetInRange()
	{
		return false;
	}

	/**
	 * Gives this NPCEntity a target.
	 * 
	 * @param target
	 *            {@link rutebaga.model.entity.Entity Entity} to target.
	 * @see rutebaga.model.entity.Entity
	 */
	public void setTarget(Entity target)
	{
		this.target = target;
	}

	/**
	 * Instructs this NPCEntity to speak. Will be forwarded to
	 * {@link NPCEntityType}.
	 * @return 
	 * 
	 * @see NPCEntityType
	 */
	public Stack<Speech> speak(Entity entity)
	{
		brain.speak(this, entity);
		return getSpeech();
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
	public void takeHostileGesture(Entity entity)
	{
		target = entity;
		brain.makeHostile(this);
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
	public void takeFriendlyGesture(Entity entity)
	{
		target = entity;
		brain.makeFriendly(this);
	}

	/**
	 * Instructs this NPCEntity to barter. Will be forwarded to
	 * {@link NPCEntityType}.
	 * 
	 * @see NPCEntityType
	 */
	public void barter()
	{
		brain.barter(this);
	}

	/**
	 * Returns this NPCEntity's current target.
	 * 
	 * @see rutebaga.model.entity.Entity
	 * @return The {@link rutebaga.model.entity.Entity Entity} currently being
	 *         targeted by this NPCEntity.
	 */
	public Entity getTarget()
	{
		return target;
	}

	/**
	 * Sets this NPCEntity's current {@link NPCBrain}.
	 * 
	 * @param brain
	 *            The {@link NPCBrain} to be inserted into this NPCEntity.
	 * @see NPCBrain
	 */
	public void setBrain(NPCBrain brain)
	{
		this.brain = brain;
	}
	
	public IntVector2D getTargetTile() {
		return target.getTile();
	}

	/**
	 * Returns this NPCEntity's current {@link NPCBrain}.
	 * 
	 * @return The {@link NPCBrain} currently controlling this NPCEntity.
	 * @see NPCBrain
	 */
	public NPCBrain getBrain()
	{
		return this.brain;
	}

	public MutableVector2D getDirection() {
		return direction;
	}

	public void setDirection(MutableVector2D direction) {
		this.direction = direction;
	}

	public boolean isPausing() {
		return pausing;
	}

	public void setPausing(boolean pausing) {
		this.pausing = pausing;
	}
	

}
