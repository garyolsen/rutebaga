package rutebaga.model.entity.inventory;

import java.util.Set;

import rutebaga.model.item.EquippableItem;

public interface Inventory
{
	Set<EquippableItem> getUnequipped();
	Set<EquippableItem> getEquipped();
	boolean canEquip(EquippableItem item);
	void equip(EquippableItem item);
	void unequip(EquippableItem item);
}
