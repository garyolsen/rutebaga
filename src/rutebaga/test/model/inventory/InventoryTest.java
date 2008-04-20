package rutebaga.test.model.inventory;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.effect.SlotEffect;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.item.ItemType;
import rutebaga.model.item.SlotType;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.test.scaffold.TestScaffold;

public class InventoryTest
{
	public static Entity entity;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MasterScaffold s = TestScaffold.getInstance();

		entity = ((EntityType<?>) s.get("entityDefault"))
				.makeInstance();
		Inventory inventory = entity.getInventory();

		SlotType hand = (SlotType) s.get("slotHand");
		
		SlotType ring = (SlotType) s.get("slotRing");
		
		inventory.addSlotAllocation(ring, 4);

		ItemType<?> sword = (ItemType<?>) s.get("itemSword");
		

		System.out.println("Adding 3 swords...");
		sword.makeInstance().giveTo(inventory);
		sword.makeInstance().giveTo(inventory);
		sword.makeInstance().giveTo(inventory);
		System.out.println(inventory); System.out.println(entity.getStats());
		
		System.out.println("Attempting to equip a sword...");
		inventory.equip(inventory.getUnequipped().iterator().next());
		tick();
		System.out.println(inventory); System.out.println(entity.getStats());

		System.out.println("Adding 4 hand allocations...");
		inventory.addSlotAllocation(hand, 4);

		System.out.println("Attempting to equip a sword...");
		inventory.equip(inventory.getUnequipped().iterator().next());
		tick();
		System.out.println(inventory); System.out.println(entity.getStats());
		
		System.out.println("Attempting to equip a sword...");
		inventory.equip(inventory.getUnequipped().iterator().next());
		tick();
		System.out.println(inventory); System.out.println(entity.getStats());
		
		System.out.println("Attempting to equip a sword...");
		inventory.equip(inventory.getUnequipped().iterator().next());
		tick();
		System.out.println(inventory); System.out.println(entity.getStats());
		
		System.out.println("Attempting to equip a sword...");
		inventory.equip(inventory.getUnequipped().iterator().next());
		tick();
		System.out.println(inventory); System.out.println(entity.getStats());
		
		System.out.println("After effect expiry...");
		System.out.println(inventory); System.out.println(entity.getStats());
		
		System.out.println("Attempting to unequip a sword...");
		inventory.unequip(inventory.getEquipped().iterator().next());
		tick();
		System.out.println(inventory); System.out.println(entity.getStats());
		
		System.out.println("Attempting to unequip a sword...");
		inventory.unequip(inventory.getEquipped().iterator().next());
		tick();
		System.out.println(inventory); System.out.println(entity.getStats());
		
		System.exit(0);

	}
	
	public static void tick()
	{
		entity.tick();
	}

}
