package rutebaga.model.entity.inventory;

import java.util.Set;

import rutebaga.model.item.Item;

public interface Inventory
{
	Set<Item> getUnequipped();
	Set<Item> getEquipped();
	boolean canEquip(Item item);
	void equip(Item item);
	void unequip(Item item);
}
