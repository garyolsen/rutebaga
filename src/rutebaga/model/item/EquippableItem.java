package rutebaga.model.item;

import java.util.List;

import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.environment.Instance;

public abstract class EquippableItem extends Instance
{
	//FIXME implement, make not abstract
	//TODO change this into EquippableAspect
	
	public abstract List<ReversibleEntityEffect> getReversibleEquipEffects();
	public abstract List<EntityEffect> getPermanentEquipEffects();
}
