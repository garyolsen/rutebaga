package rutebaga.model.item;

import java.util.ArrayList;
import java.util.List;

import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.InstanceType;

public class ItemType<T extends Item> extends ConcreteInstanceType<T>
{
	private boolean equippable = false;
	private SlotAllocation allocation = new SlotAllocation();
	private List<EntityEffect> permanentEffects = new ArrayList<EntityEffect>();
	private List<ReversibleEntityEffect> reversibleEffects = new ArrayList<ReversibleEntityEffect>();
	
	private String name;

	public SlotAllocation getAllocation()
	{
		return allocation;
	}

	public String getName()
	{
		return name;
	}

	public boolean isEquippable()
	{
		return equippable;
	}

	public void setAllocation(SlotAllocation allocation)
	{
		this.allocation = allocation;
	}

	public void setEquippable(boolean equippable)
	{
		this.equippable = equippable;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	protected T create()
	{
		return (T) new Item(this);
	}

	@Override
	protected void initialize(T instance)
	{
		super.initialize(instance);
		instance.setName(name);
		if(equippable)
		{
			EquippableAspect equip = new EquippableAspect();
			equip.getAllocation().add(allocation);
			equip.getPermanentEquipEffects().addAll(permanentEffects);
			equip.getReversibleEquipEffects().addAll(reversibleEffects);
			instance.setEquippableAspect(equip);
		}
	}
}
