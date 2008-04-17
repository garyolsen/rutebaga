package rutebaga.model.environment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class World {

	private Set<Environment> environments;
	
	public World() {
		environments = new HashSet<Environment>();
	}
	
	public void add(Environment e) {
		environments.add(e);
	}
	
	public void addAll(Collection<Environment> e) {
		environments.addAll(e);
	}
	
	public void remove(Environment e) {
		environments.remove(e);
	}
	
	public void removeAll(Collection<Environment> e) {
		environments.removeAll(e);
	}
	
	public void tick() {
		for (Environment e: environments)
			e.tick();
	}
}
