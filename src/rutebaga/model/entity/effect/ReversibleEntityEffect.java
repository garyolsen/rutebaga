package rutebaga.model.entity.effect;


public abstract class ReversibleEntityEffect extends EntityEffect
{
	public abstract EntityEffect getReverseEffect(Object id);
}
