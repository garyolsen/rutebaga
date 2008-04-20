package rutebaga.scaffold.builders;

import rutebaga.model.entity.npc.NPCType;
import rutebaga.scaffold.MasterScaffold;

public class NPCTypeBuilder extends EntityTypeBuilder{

	public Object create(String id)
	{
		return new NPCType();
	}

	@SuppressWarnings("unchecked")
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);

	}

}
