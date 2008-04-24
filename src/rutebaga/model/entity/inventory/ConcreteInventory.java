package rutebaga.model.entity.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.EffectSource;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.item.Item;
import rutebaga.model.item.SlotAllocation;
import rutebaga.model.item.SlotType;

public class ConcreteInventory implements Inventory
{
	// FIXME implement slot types
	private Entity parent;

	private Set<Item> equipped = new HashSet<Item>();

	private Set<Item> unequipped = new HashSet<Item>();
	private SlotAllocation slots = new SlotAllocation();
	private Map<Item, Set<EntityEffect>> onUnequipEffects = new HashMap<Item, Set<EntityEffect>>();
	private double weight;
	
	private EffectSource asEffectSource = new InventoryEffectSource(this);

	public ConcreteInventory(Entity parent)
	{
		super();
		this.parent = parent;
	}

	public void accept(Item item)
	{
		this.unequipped.add(item);
		recalculateWeight();
	}

	public void addSlotAllocation(SlotType type, int qty)
	{
		this.slots.add(type, qty);
	}

	public boolean canEquip(Item item)
	{
		return item.isEquippable() && item.getEquippableAspect().canEquip(this);
	}

	public void drop(Item item)
	{
		this.unequipped.remove(item);
		this.equipped.remove(item);
		
		parent.getEnvironment().add(item, new Vector2D( parent.getFacingTile() ));
		
		recalculateWeight();
	}

	public void equip(Item<?> item)
	{
		// TODO replace with custom exception
		if (!item.isEquippable())
			throw new RuntimeException("Item is not equippable");
		if (!equipped.contains(item) && canEquip(item))
		{
			equipped.add(item);
			unequipped.remove(item);

			Set<EntityEffect> unequipEffects = new HashSet<EntityEffect>();
			for (ReversibleEntityEffect effect : item
					.getReversibleEquipEffects())
			{
				Object id = parent.accept(effect, asEffectSource);
				EntityEffect reverse = effect.getReverseEffect(id);
				unequipEffects.add(reverse);
			}
			onUnequipEffects.put(item, unequipEffects);

			for (EntityEffect effect : item.getPermanentEquipEffects())
			{
				parent.accept(effect, asEffectSource);
			}
			
			slots.remove(item.getEquippableAspect().getAllocation());
		}
	}

	public int getAvailableForSlot(SlotType type)
	{
		return slots.getAvailable(type);
	}

	public SlotAllocation getCurrentAllocations()
	{
		return slots;
	}

	public Set<Item> getEquipped()
	{
		return Collections.unmodifiableSet(equipped);
	}

	public Entity getOwner()
	{
		return parent;
	}

	public Set<Item> getUnequipped()
	{
		return Collections.unmodifiableSet(unequipped);
	}

	public void unequip(Item item)
	{
		for (EntityEffect reverse : onUnequipEffects.remove(item))
		{
			parent.accept(reverse, asEffectSource);
		}
		slots.add(item.getEquippableAspect().getAllocation());
		unequipped.add(item);
		equipped.remove(item);
		recalculateWeight();
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("Slots:\n");
		for (SlotType type : slots.getSlotTypes())
		{
			sb.append("\t").append(type.getName()).append("\t").append(slots.getAllocation(type)).append("\n");
		}
		sb.append("Unequipped Items:\n");
		for (Item item : unequipped)
		{
			sb.append("\tItem: " + item.getName() + "\n");
		}

		sb.append("Equipped Items:\n");
		for (Item item : equipped)
		{
			sb.append("\tItem: " + item.getName() + "\n");
		}

		return sb.toString();
	}
	
	public Set<Item> getItems() {
		Set<Item> items = new HashSet<Item>();
		items.addAll(equipped);
		items.addAll(unequipped);
		return items;
	}
	
	public boolean remove(Item item) {
		if (equipped.contains(item))
			return false;
		
		recalculateWeight();
		return unequipped.remove(item);
	}
	
	public double getWeight() {
		return weight;
	}
	
	private void recalculateWeight() {
		double m = 0.0;
		for (Item i : equipped)
		{
			m = i.getMass() + m;
		}
		for (Item i : unequipped)
		{
			m = i.getMass() + m;
		}
		weight = m;
	}

}
