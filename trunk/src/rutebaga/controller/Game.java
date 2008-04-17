package rutebaga.controller;

import java.util.Collection;
import rutebaga.scaffold.Builder;

public interface Game {
	GameInitializer getGameInitializer();
	
	Collection<Builder> getBuilders(); // or something like that
}
