package rutebaga.test.scaffold;

import java.awt.Image;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.ConfigFileBuilder;

public class EntityTypeBuilder extends ConfigFileBuilder
{

	@Override
	protected String getDefaultFileName()
	{
		return "config/entity";
	}

	public Object create(String id)
	{
		return new EntityType();
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		EntityType type = (EntityType) object;
		type.setImage((Image) getObjectFor(id, "image", scaffold));
		type.setMovementSpeed((ValueProvider<Entity>) getValueProvider(id,
				"moveSpd", scaffold));
	}

}
