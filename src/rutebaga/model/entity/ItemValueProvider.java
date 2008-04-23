package rutebaga.model.entity;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.item.Item;
import rutebaga.model.item.ItemType;

public class ItemValueProvider extends ValueProvider<Entity>
{
	private ItemType type;

	public ItemValueProvider()
	{
		super();
	}

	public ItemValueProvider(ItemType type)
	{
		super();
		this.type = type;
	}

	public ItemType getType()
	{
		return type;
	}

	@Override
	public double getValue(Entity t)
	{
		int value = 0;
		for(Item item : t.getInventory().getItems())
		{
			if(type.equals(item.getType()))
				value++;
		}
		return value;
	}

	public void setType(ItemType type)
	{
		this.type = type;
	}

}
