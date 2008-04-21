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

public class Mount<T extends Mount<T>> extends Entity<T> {

	private Entity mountee;
	private Vehicle vehicle;
	
	private List<EntityEffect> onDismount = new ArrayList<EntityEffect>();

	public Mount(InstanceType type) {
		super(type);
		
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(isMounted())
			mountee.tick();
	}

	@Override
	public void walk(Vector2D direction) {
		super.walk(direction);
	}

	public boolean canMount(Entity entity) {
		return !isMounted();
	}
	
	public boolean isMounted() {
		return (mountee != null);
	}
	
	public void mount(Entity entity) {
		if(canMount(entity)) {
			mountee = entity;

			entity.setMount(this);
			
			for(ReversibleEntityEffect effect : vehicle.getEntityEffects()) {
				Object id = entity.accept(effect);
				EntityEffect reverse = effect.getReverseEffect(id);
				onDismount.add(reverse);
			}
			
		}
	}
	
	public void dismount(Entity entity) {
		if(mountee == entity) {
			mountee = null;
			entity.setMount(null);
			
			for(EntityEffect effect : onDismount) {
				entity.accept(effect);
			}
			
			onDismount.clear();
		}
	}

	@Override
	public Stats getDamageResistance() {
		if(isMounted())
			return mountee.getDamageResistance();
		
		return new ConcreteStats(this);
	}

	@Override
	public Inventory getInventory() {
		if(isMounted())
			return mountee.getInventory();
		
		return null;
	}

	@Override
	public Stats getStats() {
		if(isMounted()) 
			return mountee.getStats();
		
		return null;
	}

	@Override
	public double getMass() {
		if(isMounted())
			return mountee.getMass() + vehicle.getMass();
		
		return vehicle.getMass();
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public Storefront getStoreFront() {
		
		return null;
	}

	@Override
	public Object accept(EntityEffect effect) {
		if(isMounted())
			mountee.accept(effect);
			
		return null;
	}

}
