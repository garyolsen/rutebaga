package rutebaga.test.controller;

import java.util.Collection;

import rutebaga.controller.Game;
import rutebaga.controller.GameDaemon;
import rutebaga.controller.GameInitializer;
import rutebaga.controller.NewGameInitializer;
import rutebaga.controller.TitleActionInterpreter;
import rutebaga.game.AgabaturGame;
import rutebaga.scaffold.Builder;

public class ControllerTest {
	public static void main(String args[]) {

		Game game = new AgabaturGame();
		
		GameDaemon daemon = GameDaemon.initialize(game);
		
		TitleActionInterpreter title = new TitleActionInterpreter();
		daemon.activate(title);
	}
}
