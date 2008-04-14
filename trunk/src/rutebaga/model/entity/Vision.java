package rutebaga.model.entity;

import java.util.Map;
import java.util.Set;

import rutebaga.commons.Vector;
import rutebaga.model.environment.BoundsTracker;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.Appearance;

public class Vision {

	private Set<Instance> previousActiveSet;
	private Map<Vector, Appearance> memorySet;
	private BoundsTracker boundsTracker;
	private Entity entity;
	
	public Vision( Entity entity ) {
		this.entity = entity;
		boundsTracker = new BoundsTracker( entity.getVisionBounds(), entity );
	}
	
	public Set<Instance> getActiveSet() {
		return boundsTracker.getInstances();
	}
	
	public Map<Vector, Appearance> getMemorySet() {
		return memorySet;
	}
	
	public final void tick() {
		Set<Instance> activeSet = getActiveSet();
		previousActiveSet.removeAll( activeSet );
		
		for ( Instance instance : previousActiveSet ) {
			memorySet.put( instance.getCoordinate(), instance.getAppearance() );
		}
		
		Set<Vector> withinVision = entity.getVisionBounds().filter( memorySet.keySet(), entity.getTile() );
		
		for ( Vector vector : withinVision ) {
			memorySet.remove( vector );
		}
	}
}
