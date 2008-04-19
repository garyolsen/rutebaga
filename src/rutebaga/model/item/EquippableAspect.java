package rutebaga.model.item;

import java.util.ArrayList;
import java.util.List;

import rutebaga.commons.logic.ChainedRule;
import rutebaga.commons.logic.Rule;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.inventory.Inventory;

public class EquippableAspect
{
	private List<ReversibleEntityEffect> reversibleEquipEffects = new ArrayList<ReversibleEntityEffect>();

	private SlotAllocation allocation = new SlotAllocation();

	private List<EntityEffect> permanentEquipEffects = new ArrayList<EntityEffect>();

	private ChainedRule<Entity> equipRules = new ChainedRule<Entity>(true);

	public void addSlotAllocation(SlotType type, int qty)
	{
		this.allocation.add(type, qty);
	}

	public SlotAllocation getAllocation()
	{
		return allocation;
	}

	public List<EntityEffect> getPermanentEquipEffects()
	{
		return permanentEquipEffects;
	}

	public List<ReversibleEntityEffect> getReversibleEquipEffects()
	{
		return reversibleEquipEffects;
	}

	public void addEquipRule(Rule<Entity> rule)
	{
		equipRules.add(rule);
	}

	public void removeEquipRule(Rule<Entity> rule)
	{
		equipRules.remove(rule);
	}

	public boolean canEquip(Inventory inventory)
	{
		return inventory.getCurrentAllocations().contains(this.allocation)
				&& equipRules.determine(inventory.getOwner());
	}
}
