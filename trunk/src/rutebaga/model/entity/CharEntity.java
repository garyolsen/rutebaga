package rutebaga.model.entity;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import rutebaga.commons.math.ConstantValueProvider;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.inventory.ConcreteInventory;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.entity.stats.ConcreteStats;
import rutebaga.model.entity.stats.Stats;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.storefront.EntityStoreFront;
import rutebaga.model.storefront.Storefront;

public class CharEntity<T extends CharEntity<T>> extends Entity<T>
{
	private ConcreteStats stats = new ConcreteStats(this);
	private ConcreteStats damageResistance = new ConcreteStats(this);
	private ConcreteInventory inventory = new ConcreteInventory(this);
	private boolean playerControlled;
	private double mass = 1.0;

	public CharEntity(InstanceType<T> type)
	{
		super(type);
	}

	@Override
	public Inventory getInventory()
	{
		return inventory;
	}

	@Override
	public double getMass()
	{
		return mass + inventory.getWeight();
	}

	@Override
	public Stats getStats()
	{
		return stats;
	}

	@Override
	public Stats getDamageResistance()
	{
		return damageResistance;
	}

	@Override
	public void setMass(double mass) {
		this.mass = mass;
	}

	@Override
	public boolean isPlayerControlled()
	{
		return playerControlled;
	}

	public void setPlayerControlled(boolean playerControlled)
	{
		this.playerControlled = playerControlled;
	}
	
}
