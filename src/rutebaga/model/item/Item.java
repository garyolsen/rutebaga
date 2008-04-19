package rutebaga.model.item;

import java.util.List;

import rutebaga.model.Named;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;

public class Item<T extends Item<T>> extends Instance<T> implements Named
{
	private EquippableAspect equippableAspect;

	private Double defaultPrice;
	private String name;

	public Item(InstanceType<T> type)
	{
		super(type);
	}
	
	@Override
	public boolean blocks(Instance other) {
		return false;
	}

	public Double getDefaultPrice()
	{
		return defaultPrice;
	}

	public EquippableAspect getEquippableAspect()
	{
		return equippableAspect;
	}

	@Override
	public double getLayer() {
		return 0;
	}

	@Override
	public double getMass() {
		return 1.0;
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

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.ITEM;
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
	public void tick() {
		
	}

	public void setEquippableAspect(EquippableAspect equippableAspect)
	{
		this.equippableAspect = equippableAspect;
	}
}
