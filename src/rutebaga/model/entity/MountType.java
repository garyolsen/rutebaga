package rutebaga.model.entity;


public class MountType<T extends Mount> extends EntityType<T> {

	private Vehicle vehicle;
	
	public T create()
	{
		return (T) new Mount(this);
	}
	
	public void initialize(T mount)
	{
		super.initialize(mount);
		
		mount.setVehicle(vehicle);
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public Vehicle getVehicle(Vehicle vehicle) {
		return vehicle;
	}
}
