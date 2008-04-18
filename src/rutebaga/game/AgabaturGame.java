package rutebaga.game;

import java.util.ArrayList;
import java.util.Collection;

import rutebaga.controller.Game;
import rutebaga.controller.GameInitializer;
import rutebaga.controller.NewGameInitializer;
import rutebaga.game.builders.GameConfigBuilder;
import rutebaga.game.builders.ImageBuilder;
import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.test.scaffold.EntityTypeBuilder;

public class AgabaturGame implements Game
{
	private MasterScaffold scaffold = new MasterScaffold();

	public Collection<Builder> getBuilders()
	{
		Collection<Builder> rval = new ArrayList<Builder>();
		rval.add(new EntityTypeBuilder(scaffold));
		rval.add(new ImageBuilder(scaffold));
		rval.add(new GameConfigBuilder(scaffold));
		return rval;
	}

	public GameInitializer getGameInitializer()
	{
		for(Builder builder : getBuilders())
		{
			scaffold.registerBuilder(builder);
		}
		return new AgabaturNewGameInitializer(scaffold);
	}

}
