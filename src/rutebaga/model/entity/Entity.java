package rutebaga.model.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.UIDProvider;
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
public abstract class Entity extends Instance
{
	private EntityType type;
	
	private Map<Object, EntityEffect> eventsQueue = new HashMap<Object, EntityEffect>();

	public Entity(EntityType type)
	{
		this.type = type;
	}
	
	public abstract Stats getStats();

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
		eventsQueue.put(uid, effect);
		return uid;
	}

	protected Map<Object, EntityEffect> getEventsQueue()
	{
		return eventsQueue;
	}
	
	

}
