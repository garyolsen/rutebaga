package rutebaga.model.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.Vector;
import rutebaga.commons.Bounds;
import rutebaga.commons.EllipseBounds;
import rutebaga.commons.UIDProvider;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.entity.stats.Stats;
import rutebaga.model.environment.Instance;

/**
 * Entity stores the state related to an Entity in a physical environment.
 * Entities are objects defined by certain anthropomorphic attributes, such as
 * containing stats and an inventory. However, Entities are not necessarily
 * anthropomorphic themselves.
 * 
 * Entity is defined by its {@link EntityType}, to which it forwards most of
 * its behavior. Entity exists only to retain state. Subclasses of Entity that
 * require new operations should defer the execution of those operations to
 * their own subclass of {@link EntityType}.
 * 
 * @author Nick
 * @see EntityType
 */
public abstract class Entity extends Instance {
	private EntityType type;

	private Map<Object, EntityEffect> eventsQueue = new HashMap<Object, EntityEffect>();

	private EllipseBounds visionBounds;

	private Vision vision;

	/**
	 * Constructs a new Entity based on the specified EntityType.
	 * 
	 * @param type
	 *            The EntityType defining this new Entity.
	 */
	public Entity(EntityType type) {
		this.type = type;
		visionBounds = new EllipseBounds(getCoordinate(), new Vector(10, 10, 0));
	}

	/**
	 * Queues an effect to be applied to this entity.
	 * 
	 * @param effect
	 *            the effect to apply
	 * @return the token identifying the effect's application
	 */
	public Object accept(EntityEffect effect) {
		Object uid = UIDProvider.getUID();
		eventsQueue.put(uid, effect);
		return uid;
	}

	protected Map<Object, EntityEffect> getEventsQueue() {
		return eventsQueue;
	}

	/**
	 * Returns this Entity's Inventory.
	 * 
	 * @return The Inventory held by this Entity.
	 */
	public abstract Inventory getInventory();

	/**
	 * Returns this Entity's Stats.
	 * 
	 * @return The Stats of this Entity.
	 */
	public abstract Stats getStats();

	/**
	 * Returns the {@link rutebaga.commons.Bounds Bounds} respresenting what
	 * this Entity can see.
	 * 
	 * @return The Bounds of this Entity's vision.
	 */
	public EllipseBounds getVisionBounds() {
		return visionBounds;
	}

	/**
	 * Returns what is currently visible to this Entity.
	 * 
	 * @return This Entity's Vision.
	 */
	public Vision getVision() {
		return vision;
	}

	/**
	 * Returns the distance this Entity can see.
	 * 
	 * @return The radius of the {@link rutebaga.commons.Bounds Bounds} of this
	 *         Entity's Vision.
	 */
	public Vector getVisionRadius() {
		return visionBounds.getRadii();
	}

	/**
	 * Sets the {@link rutebaga.commons.Bounds Bounds} of this Entity's Vision.
	 * @param visionBounds This Entity's new Vision Bounds.
	 */
	public void setVisionBounds(EllipseBounds visionBounds) {
		this.visionBounds = visionBounds;
	}

	/**
	 * Changes this Entity's Vision based on a new radius of Vision.
	 * @param visionRadius The distance this Entity will be able to see.
	 */
	public void setVisionRadius(Vector visionRadius) {
		visionBounds.setRadii(visionRadius);
	}

	public final void tick() {
		if (this.type != null)
			type.tick(this);
	}

}
