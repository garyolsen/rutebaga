package rutebaga.model.entity.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.item.Item;

public class ConcreteInventory implements Inventory
{
	// FIXME implement slot types
	private Entity parent;
	private Set<Item> equipped = new HashSet<Item>();
	private Set<Item> unequipped = new HashSet<Item>();

	private Map<Item, Set<EntityEffect>> onUnequipEffects = new HashMap<Item, Set<EntityEffect>>();

	public ConcreteInventory(Entity parent)
	{
		super();
		this.parent = parent;
	}

	public boolean canEquip(Item item)
	{
		// FIXME implement
		return true;
	}

	public void equip(Item item)
	{
		if (!equipped.contains(item))
		{
			equipped.add(item);

			Set<EntityEffect> unequipEffects = new HashSet<EntityEffect>();
			for (ReversibleEntityEffect effect : item
					.getReversibleEquipEffects())
			{
				Object id = parent.accept(effect);
				EntityEffect reverse = effect.getReverseEffect(id);
				unequipEffects.add(reverse);
			}
			onUnequipEffects.put(item, unequipEffects);

			for (EntityEffect effect : item.getPermanentEquipEffects())
			{
				parent.accept(effect);
			}
		}
	}

	public void unequip(Item item)
	{
		for (EntityEffect reverse : onUnequipEffects.remove(item))
		{
			parent.accept(reverse);
		}
	}

	public Set<Item> getEquipped()
	{
		return Collections.unmodifiableSet(equipped);
	}

	public Set<Item> getUnequipped()
	{
		return Collections.unmodifiableSet(unequipped);
	}

	public void drop(Item item)
	{
		this.unequipped.remove(item);
		this.equipped.remove(item);
	}

}
