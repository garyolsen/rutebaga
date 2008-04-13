package rutebaga.model.entity;

import rutebaga.model.entity.inventory.ConcreteInventory;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.entity.stats.ConcreteStats;
import rutebaga.model.entity.stats.Stats;
import rutebaga.model.environment.Instance;

public class CharEntity extends Entity
{
	private ConcreteStats stats = new ConcreteStats(this);
	private ConcreteInventory inventory = new ConcreteInventory(this);

	public CharEntity(EntityType type)
	{
		super(type);
	}

	@Override
	public boolean blocks(Instance other)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getFriction()
	{
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	public Inventory getInventory()
	{
		return inventory;
	}

}
