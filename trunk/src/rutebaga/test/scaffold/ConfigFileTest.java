package rutebaga.test.scaffold;

import rutebaga.game.builders.ImageBuilder;
import rutebaga.model.entity.EntityType;
import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;

public class ConfigFileTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MasterScaffold scaffold = new MasterScaffold();
		Builder imgBuilder = new ImageBuilder(scaffold);
		Builder entityTypeBuilder = new EntityTypeBuilder(scaffold);
		scaffold.registerBuilder(imgBuilder);
		scaffold.registerBuilder(entityTypeBuilder);
		scaffold.build();
		
		EntityType type = (EntityType) scaffold.get("entityDefault");
		System.out.println(type);
		System.out.println(type.getImage());
	}

}
