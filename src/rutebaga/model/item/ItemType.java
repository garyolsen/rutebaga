package rutebaga.model.item;

import rutebaga.model.environment.InstanceType;

public class ItemType<T extends Item> implements InstanceType<Item>
{

	public Item makeInstance() {
		return (T) new Item(this);
	}
	
}
