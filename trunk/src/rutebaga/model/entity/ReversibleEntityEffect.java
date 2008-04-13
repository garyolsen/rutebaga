package rutebaga.model.entity;

import rutebaga.model.entity.effect.EntityEffect;

public abstract class ReversibleEntityEffect extends EntityEffect
{
	public abstract EntityEffect getReverseEffect(Object id);
}
