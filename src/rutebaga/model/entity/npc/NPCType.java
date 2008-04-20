package rutebaga.model.entity.npc;

import rutebaga.model.environment.ConcreteInstanceType;

public class NPCType<T extends NPCEntity> extends ConcreteInstanceType<T> {

	@Override
	protected T create() {
		return (T) new NPCEntity(this);
	}
	
	public void initialize(T npc) {
		npc.setBrain(new NPCSimpleBrain());
	}

}
