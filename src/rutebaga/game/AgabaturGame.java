package rutebaga.game;

import java.util.Collection;

import rutebaga.controller.Game;
import rutebaga.controller.GameInitializer;
import rutebaga.controller.NewGameInitializer;
import rutebaga.scaffold.Builder;

public class AgabaturGame implements Game
{

	public Collection<Builder> getBuilders()
	{
		return null;
	}

	public GameInitializer getGameInitializer()
	{
		return new NewGameInitializer(null);
	}

}
