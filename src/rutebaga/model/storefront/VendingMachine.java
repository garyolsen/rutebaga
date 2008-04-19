package rutebaga.model.storefront;

import java.util.Collection;
import java.util.Random;
import rutebaga.commons.math.BidirectionalValueProvider;
import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;

public class VendingMachine implements Storefront
{
	private Collection<Item> items;
	private Random rand = new Random();
	
	public VendingMachine(Collection<Item> items)
	{
		this.items = items;
	}
	
	public StoreInstance getInstance(Entity entity) {
		VendingMachineInstance instance = new VendingMachineInstance(this, entity);
		instance.setStoreInstanceBargainSkill(rand.nextInt(100));
		return instance;
	}
	
	public Collection<Item> getItems()
	{
		return items;
	}

}
