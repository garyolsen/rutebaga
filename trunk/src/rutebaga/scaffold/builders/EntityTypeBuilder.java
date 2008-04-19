package rutebaga.scaffold.builders;

import java.awt.Image;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.scaffold.MasterScaffold;

public class EntityTypeBuilder extends InstanceBuilder
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
		super.initialize(id, object, scaffold);
		EntityType type = (EntityType) object;
		type.setImage((Image) getObjectFor(id, "image", scaffold));
		type.setMovementSpeed((ValueProvider<Entity>) getValueProvider(id,
				"moveSpd", scaffold));
	}

}
