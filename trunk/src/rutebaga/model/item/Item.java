package rutebaga.model.item;

import java.util.List;

import rutebaga.model.Named;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;

public abstract class Item extends Instance implements Named
{
	private EquippableAspect equippableAspect;
	private ItemType itemType;
	private Double defaultPrice;

	private String name;

	public Item(ItemType itemType)
	{
		super();
		this.itemType = itemType;
	}

	public Double getDefaultPrice()
	{
		return defaultPrice;
	}

	public ItemType getItemType()
	{
		return itemType;
	}

	public String getName()
	{
		return name;
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

	public void giveTo(Inventory inventory)
	{
		inventory.accept(this);
	}

	public boolean isEquippable()
	{
		return equippableAspect != null;
	}

	public void setDefaultPrice(Double defaultPrice)
	{
		this.defaultPrice = defaultPrice;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	protected InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.ITEM;
	}
}
