package rutebaga.test.model.inventory;

import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.inventory.ConcreteInventory;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.item.Item;
import rutebaga.model.item.ItemType;
import rutebaga.model.item.SlotType;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.test.scaffold.TestScaffold;

public class InventoryTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MasterScaffold s = TestScaffold.getInstance();

		Entity<?> entity = ((EntityType<?>) s.get("entityDefault"))
				.makeInstance();
		Inventory inventory = entity.getInventory();

		SlotType hand = new SlotType("Hand");
		hand.setGlobalMax(2);

		ItemType<?> sword = new ItemType();
		sword.setEquippable(true);
		sword.setName("Sword");
		sword.getAllocation().add(hand, 1);

		System.out.println("Adding 3 swords...");
		sword.makeInstance().giveTo(inventory);
		sword.makeInstance().giveTo(inventory);
		sword.makeInstance().giveTo(inventory);
		System.out.println(inventory);
		
		System.out.println("Attempting to equip a sword...");
		inventory.equip(inventory.getUnequipped().iterator().next());
		System.out.println(inventory);

		System.out.println("Adding 4 hand allocations...");
		inventory.addSlotAllocation(hand, 4);

		System.out.println("Attempting to equip a sword...");
		inventory.equip(inventory.getUnequipped().iterator().next());
		System.out.println(inventory);
		
		System.out.println("Attempting to equip a sword...");
		inventory.equip(inventory.getUnequipped().iterator().next());
		System.out.println(inventory);
		
		System.out.println("Attempting to equip a sword...");
		inventory.equip(inventory.getUnequipped().iterator().next());
		System.out.println(inventory);

	}

}
