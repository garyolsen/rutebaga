package rutebaga.scaffold.builders;

import rutebaga.model.environment.Decal;
import rutebaga.model.environment.DecalType;
import rutebaga.scaffold.MasterScaffold;

public class DecalTypeBuilder extends InstanceBuilder<DecalType>
{

	@Override
	protected String getDefaultFileName()
	{
		return "config/decaltypes";
	}

	public Object create(String id)
	{
		return new DecalType();
	}

	@Override
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);

		DecalType type = (DecalType) object;

		Double layer = getDouble(id, "layer");
		if (layer != null)
			type.setLayer(layer);

		Integer lifetime = getInteger(id, "lifetime");
		type.setLifetime(lifetime);
	}

}
