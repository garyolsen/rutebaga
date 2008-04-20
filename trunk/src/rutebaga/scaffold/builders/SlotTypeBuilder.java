package rutebaga.scaffold.builders;

import rutebaga.model.item.SlotType;
import rutebaga.scaffold.MasterScaffold;

public class SlotTypeBuilder extends ConfigFileBuilder
{

	@Override
	protected String getDefaultFileName()
	{
		return "config/slots";
	}

	public Object create(String id)
	{
		return new SlotType(getProperty(id, "name"));
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		SlotType type = (SlotType) object;
		type.setGlobalMax(getInteger(id, "max"));
	}

}
