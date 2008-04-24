package rutebaga.model.entity.npc;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.Team;
import rutebaga.model.environment.ConcreteInstanceType;

public class NPCType<T extends NPCEntity> extends EntityType<T> {

	private Team team;
	private ValueProvider<Entity> offensivityStrategy;
	
	public ValueProvider<Entity> getOffensivityStrategy()
	{
		return offensivityStrategy;
	}

	public void setOffensivityStrategy(ValueProvider<Entity> offensivityStrategy)
	{
		this.offensivityStrategy = offensivityStrategy;
	}

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
		npc.setOffensivityStrategy(offensivityStrategy);
		npc.setTeam(team);
	}

}
