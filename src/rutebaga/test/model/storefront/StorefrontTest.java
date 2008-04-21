package rutebaga.test.model.storefront;

import java.util.Collection;
import java.util.HashSet;

import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.item.Item;
import rutebaga.model.item.ItemType;
import rutebaga.model.storefront.StoreInstance;
import rutebaga.model.storefront.Storefront;
import rutebaga.model.storefront.VendingMachine;

public class StorefrontTest {

	public static void main(String args[])
	{
		Collection<Item> items = new HashSet();
		ItemType<Item> itemType = new ItemType<Item>();
		
		Item sword = itemType.makeInstance();
		sword.setName("Big-Ass Sword");
		sword.setDefaultPrice(10.0);
		items.add(sword);
		
		Item shield = itemType.makeInstance();
		shield.setName("Shield");
		shield.setDefaultPrice(100.0);
		items.add(shield);
		
		InstanceType<Entity> entityType = new EntityType<Entity>();
		
		Entity entity = entityType.makeInstance();
		entity.addToMoney(1);
		
		Entity entity1 = entityType.makeInstance();
		entity1.addToMoney(1000);
		entity1.getInventory().accept(sword);
		
		System.out.println(entity.getInventory());
		System.out.println(entity1.getInventory());
		
		Storefront store = entity1.getStoreFront();
		StoreInstance storeInstance = store.getInstance(entity);
		
		Collection<Item> itemsForSale = storeInstance.getItems();
		
		System.out.println(itemsForSale);
		
		System.out.println( storeInstance.sellPriceOf( (Item)itemsForSale.toArray()[0]) );
		
		storeInstance.sell( (Item)itemsForSale.toArray()[0] );
		
		System.out.println(entity.getInventory());
		System.out.println(entity.getMoneyAmount());
		
		System.out.println(entity1.getInventory());
		System.out.println(entity1.getMoneyAmount());
	}
}
