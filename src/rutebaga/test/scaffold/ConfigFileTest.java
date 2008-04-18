package rutebaga.test.scaffold;

import rutebaga.model.entity.EntityType;
import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.ImageBuilder;

public class ConfigFileTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MasterScaffold scaffold = new MasterScaffold();
		Builder imgBuilder = new ImageBuilder();
		Builder entityTypeBuilder = new EntityTypeBuilder();
		scaffold.registerBuilder(imgBuilder);
		scaffold.registerBuilder(entityTypeBuilder);
		scaffold.build();
		
		EntityType type = (EntityType) scaffold.get("entityDefault");
		System.out.println(type);
		System.out.println(type.getImage());
	}

}
