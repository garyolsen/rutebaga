package rutebaga.model.entity.effect;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.environment.Environment;

public class TeleportEffect extends EntityEffect {

	private Vector2D vector;
	
	public TeleportEffect() {
	}
	
	protected void affect(Entity entity, Object id) {
		Environment environment = entity.getEnvironment();
		
		
		if( !environment.blockedAtTile(environment.getTileOf(vector), entity) ) {
			environment.remove(entity);
			
			environment.add(entity, vector);
		}
	}
	
	public String toString() {
		return "Teleporter to " + vector + "\n";
	}
	
	public void setVector(Vector2D vector) {
		this.vector = vector;
	}

}
