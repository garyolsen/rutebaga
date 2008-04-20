package rutebaga.model.entity.rule;

import rutebaga.commons.logic.Rule;
import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;
import rutebaga.model.item.ItemType;

public class ItemRule implements Rule<Entity>
{
	private boolean mustBeEquipped = false;
	private ItemType type;

	public boolean isMustBeEquipped()
	{
		return mustBeEquipped;
	}

	public void setMustBeEquipped(boolean mustBeEquipped)
	{
		this.mustBeEquipped = mustBeEquipped;
	}

	public ItemType getType()
	{
		return type;
	}

	public void setType(ItemType type)
	{
		this.type = type;
	}

	public boolean determine(Entity context)
	{
		if (!mustBeEquipped)
		{
			for (Item item : context.getInventory().getUnequipped())
			{
				if (type.equals(item.getType()))
					return true;
			}
		}

		for (Item item : context.getInventory().getEquipped())
		{
			if (type.equals(item.getType()))
				return true;
		}
		
		return false;

	}

}
