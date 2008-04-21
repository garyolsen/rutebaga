package rutebaga.model.entity.inventory;

import java.util.Set;

import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;
import rutebaga.model.item.SlotAllocation;
import rutebaga.model.item.SlotType;

public interface Inventory
{
	Set<Item> getUnequipped();

	Set<Item> getEquipped();

	boolean canEquip(Item<?> item);

	void equip(Item<?> item);

	void unequip(Item<?> item);
	
	void drop(Item<?> item);
	
	void accept(Item<?> item);
	
	SlotAllocation getCurrentAllocations();
	
	void addSlotAllocation(SlotType type, int qty);

	Entity getOwner();
	
	Set<Item> getItems();
	
	boolean remove(Item item);
}
