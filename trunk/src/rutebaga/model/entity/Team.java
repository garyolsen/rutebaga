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

	public Team(String name) {
		this.name = name;
	}

	public String getTeamName() {
		return name;
	}

	public void setTeamName() {
		this.name = name;
	}
	
	public void changeHostility(Team team, double amount) {
		if (hostilityMap.containsKey(team))
		{
			HostilityRating hostility = hostilityMap.get(team);
			hostility.setRating(hostility.getRating() + amount);
		} else
		{
			HostilityRating hostility = new HostilityRating(amount);
			hostilityMap.put(team, hostility);
		}
	}
	
	public void changeHostility(Entity entity, double amount) {
		if (hostilityMap.containsKey(entity.getTeam()))
		{
			HostilityRating hostility = hostilityMap.get(entity.getTeam());
			hostility.setRating(hostility.getRating() + amount);
		} else
		{
			HostilityRating hostility = new HostilityRating(amount);
			hostilityMap.put(entity.getTeam(), hostility);
		}
	}

}
