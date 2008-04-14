package rutebaga.model.entity;

import java.util.Map;
import java.util.Set;
import rutebaga.model.environment.Instance;

public class Vision {

	private Set<Instance> previousActiveSet;
	private Map<Vector, Appearance> memorySet;
	private Entity entity;
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public Set<Instance> getActiveSet() {
		previousActiveSet =  entity.getVisibleInstances();
		return previousActiveSet;
	}
	
	public final void tick() {
		Set<Instance> activeSet = entity.getVisibleInstances();
		previousActiveSet.removeAll( activeSet );
		memorySet.addAll( previousActiveSet );
		memorySet.removeAll( activeSet );
	}
}
