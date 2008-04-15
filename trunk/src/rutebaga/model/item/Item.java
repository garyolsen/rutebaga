package rutebaga.model.item;

import java.util.List;

import rutebaga.model.Named;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.environment.Instance;

public abstract class Item extends Instance implements Named
{
	private EquippableAspect equippableAspect;
	private ItemType itemType;
	
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Item(ItemType itemType)
	{
		super();
		this.itemType = itemType;
	}

	public ItemType getItemType()
	{
		return itemType;
	}

	public List<EntityEffect> getPermanentEquipEffects()
	{
		return (equippableAspect == null) ? null : equippableAspect
				.getPermanentEquipEffects();
	}

	public List<ReversibleEntityEffect> getReversibleEquipEffects()
	{
		return (equippableAspect == null) ? null : equippableAspect
				.getReversibleEquipEffects();
	}

	public boolean isEquippable()
	{
		return equippableAspect != null;
	}
	
	public void giveTo(Inventory inventory)
	{
		//TODO implement
	}
}
