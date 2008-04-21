package rutebaga.model.entity.npc;

import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.Team;
import rutebaga.model.environment.ConcreteInstanceType;

public class NPCType<T extends NPCEntity> extends EntityType<T> {

	private Team team;
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	@Override
	public T create() {
		return (T) new NPCEntity(this);
	}
	
	public void initialize(T npc) {
		super.initialize(npc);
		npc.setBrain(new NPCSimpleBrain());
		//npc.setTeam(team);
	}

}
