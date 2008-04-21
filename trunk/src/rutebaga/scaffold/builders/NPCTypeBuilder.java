package rutebaga.scaffold.builders;

import rutebaga.model.entity.Team;
import rutebaga.model.entity.npc.NPCType;
import rutebaga.scaffold.MasterScaffold;

public class NPCTypeBuilder extends EntityTypeBuilder{

	@Override
	protected String getDefaultFileName()
	{
		return "config/npc";
	}
	
	public Object create(String id)
	{
		return new NPCType();
	}

	@SuppressWarnings("unchecked")
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);
		NPCType type = (NPCType) object;
		
		type.setTeam((Team) getObjectFor(id, "team", scaffold));
	}

}
