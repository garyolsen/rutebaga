package rutebaga.model.entity;

import java.util.HashMap;
import java.util.Map;

import rutebaga.commons.EllipseBounds;
import rutebaga.commons.UIDProvider;
import rutebaga.commons.Vector;
import rutebaga.model.Named;
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
public abstract class Entity extends Instance implements Named
{
	private Map<Object, EntityEffect> effectQueue = new HashMap<Object, EntityEffect>();

	private EllipseBounds visionBounds;
	private Vision vision;
	
	private String name;

	public Entity()
	{
		visionBounds = new EllipseBounds(new Vector(10, 10));
		// XXX: connascence of timing
		vision = new Vision(this);
	}

	/**
	 * Queues an effect to be applied to this entity.
	 * 
	 * @param effect
	 *            the effect to apply
	 * @return the token identifying the effect's application
	 */
	public Object accept(EntityEffect effect)
	{
		Object uid = UIDProvider.getUID();
		effectQueue.put(uid, effect);
		return uid;
	}

	public abstract Inventory getInventory();

	public String getName()
	{
		return name;
	}

	public abstract Stats getStats();

	public Vision getVision()
	{
		return vision;
	}

	public EllipseBounds getVisionBounds()
	{
		return visionBounds;
	}

	public Vector getVisionRadius()
	{
		return visionBounds.getRadii();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setVisionBounds(EllipseBounds visionBounds)
	{
		this.visionBounds = visionBounds;
	}

	public void setVisionRadius(Vector visionRadius)
	{
		visionBounds.setRadii(visionRadius);
	}
	
	private void flushEffectQueue()
	{
		for (Object id : getEffectQueue().keySet())
		{
			getEffectQueue().get(id).affect(this, id);
		}
		getEffectQueue().clear();
	}

	@Override
	public void tick()
	{
		flushEffectQueue();
		getVision().tick();
	}

	protected Map<Object, EntityEffect> getEffectQueue()
	{
		return effectQueue;
	}
}
