package rutebaga.model.storefront;

import java.util.Collection;

import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;

public class EntityStoreFront implements Storefront {

	private Collection<Item> items;
	private Entity seller;
	
	public EntityStoreFront(Entity seller) {
		this.seller = seller;
		items = seller.getInventory().getUnequipped();
	}
	
	public StoreInstance getInstance(Entity user) {
		return new EntityStoreInstance(this, user, seller);
	}
	
	public Collection<Item> getItems() {
		return items;
	}

}
