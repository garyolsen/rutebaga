package rutebaga.model.item;

import java.util.List;

import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;

public class EquippableAspect
{
	private List<ReversibleEntityEffect> reversibleEquipEffects;

	private List<EntityEffect> permanentEquipEffects;

	public List<ReversibleEntityEffect> getReversibleEquipEffects()
	{
		return reversibleEquipEffects;
	}

	public List<EntityEffect> getPermanentEquipEffects()
	{
		return permanentEquipEffects;
	}
}
