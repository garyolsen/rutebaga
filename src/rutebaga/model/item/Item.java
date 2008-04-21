package rutebaga.model.item;

import java.util.List;

import rutebaga.model.DefaultLayers;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.entity.ReversibleEntityEffect;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.environment.ConcreteInstanceSet;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;

public class Item<T extends Item<T>> extends Instance<T>
{
	private EquippableAspect equippableAspect;
	private double mass = 1.0;
	private Double defaultPrice = 100.0;
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
		return DefaultLayers.GROUND.getLayer();
	}

	@Override
	public double getMass() {
		return 1.0;
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

	@Override
	public void tick() {
		ConcreteInstanceSet set = new ConcreteInstanceSet();
		set.addAll(getEnvironment().instancesAt(getTile()));
		
		for(Entity entity : set.getEntities()) {
			if(entity.getInventory() != null) {
				this.giveTo(entity.getInventory());
				this.getEnvironment().remove(this);
				break;
			}
		}
			
	}

	public void setEquippableAspect(EquippableAspect equippableAspect)
	{
		this.equippableAspect = equippableAspect;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}
}
