package rutebaga.model.entity.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.item.EquippableItem;

public class ConcreteInventory implements Inventory
{
	// FIXME implement slot types
	private Entity parent;
	private Set<EquippableItem> equipped = new HashSet<EquippableItem>();
	private Set<EquippableItem> unequipped = new HashSet<EquippableItem>();

	private Map<EquippableItem, Set<EntityEffect>> onUnequipEffects = new HashMap<EquippableItem, Set<EntityEffect>>();

	public ConcreteInventory(Entity parent)
	{
		super();
		this.parent = parent;
	}

	public boolean canEquip(EquippableItem item)
	{
		// FIXME implement
		return true;
	}

	public void equip(EquippableItem item)
	{
		if (!equipped.contains(item))
		{
			equipped.add(item);
			
			Set<EntityEffect> unequipEffects = new HashSet<EntityEffect>();
			for(ReversibleEntityEffect effect : item.getReversibleEquipEffects())
			{
				Object id = parent.accept(effect);
				EntityEffect reverse = effect.getReverseEffect(id);
				unequipEffects.add(reverse);
			}
			onUnequipEffects.put(item, unequipEffects);
			
			for(EntityEffect effect : item.getPermanentEquipEffects())
			{
				parent.accept(effect);
			}
		}
	}
	
	public void unequip(EquippableItem item)
	{
		for(EntityEffect reverse : onUnequipEffects.remove(item))
		{
			parent.accept(reverse);
		}
	}

	public Set<EquippableItem> getEquipped()
	{
		return Collections.unmodifiableSet(equipped);
	}

	public Set<EquippableItem> getUnequipped()
	{
		return Collections.unmodifiableSet(unequipped);
	}

}
