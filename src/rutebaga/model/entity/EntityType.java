package rutebaga.model.entity;


/**
 * 
 * EntityType is used by {@link Entity} to define its type. Most of an Entity's
 * behavior will forward to its EntityType. Subclasses of {@link Entity} that require
 * new operations should defer the execution of those operations to their own
 * subclass of EntityType.
 * 
 * @author Nick
 * @see Entity
 */
public class EntityType {

	public void tick(Entity entity) {
		flushEffectQueue(entity);
		
		//XXX: Again with the LoD violations?
		//XXX: perhaps make a singular method in entity to tick its children? (glosh)
		entity.getVision().tick();
	}
	
	private void flushEffectQueue(Entity entity)
	{
		for(Object id : entity.getEffectQueue().keySet())
		{
			//XXX LOD violation
			entity.getEffectQueue().get(id).affect(entity, id);
		}
		entity.getEffectQueue().clear();
	}
	
	//public void move( Entity entity, Direction direction, double p ) {
	//	
	//}
	
	//public void teleport( Entity entity, Location location) {
	//	
	//}
	
	//public Vision getVision( Entity entity ) {
	//	return null;
	//}
	
	public void hostilityTo( Entity source, Entity target ) {
		
	}
	
	public void mount( Entity entity, Vehicle vehicle ) {
		
	}
	
}