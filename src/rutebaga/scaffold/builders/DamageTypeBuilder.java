package rutebaga.scaffold.builders;

import java.util.ArrayList;

import rutebaga.model.entity.DamageType;
import rutebaga.scaffold.MasterScaffold;

public class DamageTypeBuilder extends StatsBuilder
{

	@Override
	public ArrayList<String> getGlobalIds()
	{
		return new ArrayList<String>();
	}

	@Override
	public Object create(String id)
	{
		return new DamageType(getProperty(id, "name"));
	}

	@Override
	protected String getDefaultFileName()
	{
		return "config/damagetypes";
	}

	@Override
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);
		
		DamageType type = (DamageType) object;
		
		DamageType parent = (DamageType) scaffold.get("parent");
		type.setParent(parent);
	}

}
