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
		
		Storefront vendingMachine = new VendingMachine(items);
		
		InstanceType<Entity> entityType = new EntityType<Entity>();
		
		Entity entity = entityType.makeInstance();
		entity.getWallet().addTo(entity, 1000);
		
		System.out.println(entity.getWallet().getValue(entity));
		
		StoreInstance vendingInstance = vendingMachine.getInstance(entity);
		vendingInstance.setStoreInstanceBargainSkill(150);
		vendingInstance.setUserBargainSkill(1000000000);
		
		Collection<Item> itemsForSale = vendingInstance.getItems();
		
		System.out.println(entity.getInventory());
		
		System.out.println( vendingInstance.sellPriceOf( (Item)itemsForSale.toArray()[1]) );
		
		vendingInstance.sell( (Item)itemsForSale.toArray()[1] );
		
		System.out.println(entity.getInventory());
		
		System.out.println(entity.getWallet().getValue(entity));
	}
}
