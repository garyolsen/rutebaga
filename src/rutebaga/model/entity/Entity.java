package rutebaga.model.entity;

import java.util.HashMap;
import java.util.Map;

import rutebaga.commons.UIDProvider;
import rutebaga.commons.math.EllipseBounds2D;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.DefaultLayers;
import rutebaga.model.Named;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.entity.stats.Stats;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;

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
	public static int SIGHT_RANGE = 15;
	
	private Map<Object, EntityEffect> effectQueue = new HashMap<Object, EntityEffect>();

	private EllipseBounds2D visionBounds;

	private Vision vision;

	private String name;
	public Entity()
	{
		visionBounds = new EllipseBounds2D(new Vector2D(SIGHT_RANGE, SIGHT_RANGE));
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

	@Override
	public double getLayer()
	{
		return DefaultLayers.GROUND.getLayer();
	}

	public String getName()
	{
		return name;
	}

	public abstract Stats getStats();

	public Vision getVision()
	{
		return vision;
	}

	public EllipseBounds2D getVisionBounds()
	{
		return visionBounds;
	}

	public Vector2D getVisionRadius()
	{
		return visionBounds.getRadii();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setVisionBounds(EllipseBounds2D visionBounds)
	{
		this.visionBounds = visionBounds;
	}

	public void setVisionRadius(Vector2D visionRadius)
	{
		visionBounds.setRadii(visionRadius);
	}
	
	public boolean canSee(IntVector2D v) {
		Vector2D dV = new Vector2D(v.getX(), v.getY());
		return visionBounds.contains(dV.minus(this.getCoordinate()));
	}

	@Override
	public void tick()
	{
		flushEffectQueue();
		getVision().tick();
	}
	
	private void flushEffectQueue()
	{
		for (Object id : getEffectQueue().keySet())
		{
			getEffectQueue().get(id).affect(this, id);
		}
		getEffectQueue().clear();
	}

	protected Map<Object, EntityEffect> getEffectQueue()
	{
		return effectQueue;
	}

	@Override
	public final boolean blocks(Instance other)
	{
		return other.getSetIdentifier().equals(InstanceSetIdentifier.ENTITY);
//		return false;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.ENTITY;
	}
}
