package rutebaga.game;

import java.util.ArrayList;
import java.util.Collection;

import rutebaga.controller.Game;
import rutebaga.controller.GameInitializer;
import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.DefaultBuilder;

public class AgabaturGame implements Game
{
	public Collection<Builder> getBuilders()
	{
		Collection<Builder> rval = new ArrayList<Builder>();
		rval.add(new DefaultBuilder());
		return rval;
	}

	public GameInitializer getGameInitializer()
	{
		MasterScaffold scaffold = new MasterScaffold();
		for(Builder builder : getBuilders())
		{
			scaffold.registerBuilder(builder);
		}
		return new AgabaturNewGameInitializer(scaffold);
	}

}
