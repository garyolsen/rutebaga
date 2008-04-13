package rutebaga.model.entity.effect;

import rutebaga.commons.UIDProvider;
import rutebaga.model.entity.Entity;

public abstract class EntityEffect
{
	protected final Object applyTo(Entity entity)
	{
		Object uid = UIDProvider.getUID();
		affect(entity);
		return uid;
	}
	
	protected abstract void affect(Entity entity);
}
