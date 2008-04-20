package rutebaga.model.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import rutebaga.model.entity.npc.HostilityRating;

public class Team {

	private String name;
	private Map<Team, HostilityRating> hostilityMap = new HashMap();
	private Collection<Entity> entities = new HashSet<Entity>();

	public Team(String name) {
		this.name = name;
	}

	public String getTeamName() {
		return name;
	}

	public void setTeamName() {
		this.name = name;
	}
	
	public void addEntityTo(Entity entity) {
		entities.add(entity);
	}
	
	public Collection<Entity> getEntities() {
		return entities;
	}

}
