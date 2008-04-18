package rutebaga.model.entity;

import rutebaga.model.entity.inventory.ConcreteInventory;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.entity.stats.ConcreteStats;
import rutebaga.model.entity.stats.Stats;
import rutebaga.model.environment.InstanceType;

public class CharEntity<T extends CharEntity<T>> extends Entity<T>
{
	private ConcreteStats stats = new ConcreteStats(this);

	private ConcreteInventory inventory = new ConcreteInventory(this);
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
		// TODO Auto-generated method stub
		return 1.0;
	}

	@Override
	public Stats getStats()
	{
		return stats;
	}

}
