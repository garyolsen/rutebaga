package rutebaga.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rutebaga.commons.math.BidirectionalValueProvider;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.inventory.Inventory;
import rutebaga.model.entity.stats.ConcreteStats;
import rutebaga.model.entity.stats.Stats;
import rutebaga.model.environment.InstanceType;
import rutebaga.model.storefront.Storefront;

public class Mount<T extends Mount<T>> extends Entity<T>
{

	private Entity mountee;
	private Vehicle vehicle;
	private double mass = 1.0;

	private List<EntityEffect> onDismount = new ArrayList<EntityEffect>();

	public Mount(InstanceType type)
	{
		super(type);

	}

	@Override
	public Object accept(EntityEffect effect, EffectSource source)
	{
		if (isMounted())
			mountee.accept(effect, source);

		return null;
	}

	public void allocateSkillPoints(AbilityCategory category, int qty)
	{
		if(mountee == null)
			super.allocateSkillPoints(category, qty);
		mountee.allocateSkillPoints(category, qty);
	}

	public boolean canMount(Entity entity)
	{
		return !isMounted() && (entity.getFacingTile().equals(getTile()));
	}

	public void dismount(Entity entity)
	{
		if (mountee == entity)
		{
			getEnvironment().add(
					entity,
					new Vector2D(this.getFacingTile().getX(), this
							.getFacingTile().getY()));
			
			entity.removeVision(super.getVision());

			if (entity.existsInUniverse())
			{
				mountee = null;
				entity.setMount(null);

				for (EntityEffect effect : onDismount)
				{
					entity.accept(effect, null);
				}

				onDismount.clear();

			}
		}
	}

	public int getAvailableSkillPoints()
	{
		if(mountee == null)
			return super.getAvailableSkillPoints();
		return mountee.getAvailableSkillPoints();
	}

	public ValueProvider getBargainSkill()
	{
		if(mountee == null)
			return super.getBargainSkill();
		return mountee.getBargainSkill();
	}

	@Override
	public Stats getDamageResistance()
	{
		if (isMounted())
			return mountee.getDamageResistance();

		return new ConcreteStats(this);
	}

	public ValueProvider getDeadStrategy()
	{
		if(mountee == null)
			return super.getDeadStrategy();
		return mountee.getDeadStrategy();
	}

	public int getDecayTime()
	{
		if(mountee == null)
			return super.getDecayTime();
		return mountee.getDecayTime();
	}

	@Override
	public Inventory getInventory()
	{
		if (isMounted())
			return mountee.getInventory();

		return null;
	}

	@Override
	public double getMass()
	{
		if (isMounted())
			return mountee.getMass() + vehicle.getMass();

		return vehicle.getMass();
	}

	public double getMoneyAmount()
	{
		if(mountee == null)
			return getMoneyAmount();
		return mountee.getMoneyAmount();
	}

	public Entity getMountee()
	{
		return mountee;
	}

	public double getMovementSpeed()
	{
		if(mountee == null)
			return super.getMovementSpeed();
		return mountee.getMovementSpeed();
	}

	public int getSkillPoints(AbilityCategory category)
	{
		if(mountee == null)
			return super.getSkillPoints(category);
		return mountee.getSkillPoints(category);
	}

	public BidirectionalValueProvider getSkillPtStrat()
	{
		if(mountee == null)
			return super.getSkillPtStrat();
		return mountee.getSkillPtStrat();
	}

	@Override
	public Stats getStats()
	{
		if (isMounted())
			return mountee.getStats();

		return null;
	}

	@Override
	public Storefront getStoreFront()
	{

		return null;
	}

	public Vehicle getVehicle()
	{
		return vehicle;
	}

	public BidirectionalValueProvider getWallet()
	{
		if(mountee == null)
			return super.getWallet();
		return mountee.getWallet();
	}

	public boolean isMounted()
	{
		return (mountee != null);
	}

	public void mount(Entity entity)
	{
		if (canMount(entity))
		{
			mountee = entity;

			entity.getEnvironment().remove(entity);
			entity.setMount(this);
			
			entity.addVision(super.getVision());

			for (ReversibleEntityEffect effect : vehicle.getEntityEffects())
			{
				Object id = entity.accept(effect, null);
				EntityEffect reverse = effect.getReverseEffect(id);
				onDismount.add(reverse);
			}

		}
	}

	@Override
	public void setMass(double mass)
	{
		this.mass = mass;
	}

	public void setVehicle(Vehicle vehicle)
	{
		this.vehicle = vehicle;
	}

	@Override
	public void tick()
	{
		super.tick();

		if (isMounted())
			mountee.tick();
	}

	@Override
	public void walk(Vector2D direction)
	{
		super.walk(direction);
	}

}
