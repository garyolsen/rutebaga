package rutebaga.model.item;

import java.util.List;

import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;

public abstract class EquippableAspect
{
	public abstract List<ReversibleEntityEffect> getReversibleEquipEffects();
	public abstract List<EntityEffect> getPermanentEquipEffects();
}
