package rutebaga.model.entity;


public abstract class ReversibleEntityEffect extends EntityEffect
{
	public abstract EntityEffect getReverseEffect(Object id);
}
