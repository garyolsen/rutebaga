package rutebaga.test.controller;

import rutebaga.controller.Game;
import rutebaga.controller.GameDaemon;
import rutebaga.controller.TitleActionInterpreter;
import rutebaga.game.AgabaturGame;

public class ControllerTest {
	public static void main(String args[]) {

		Game game = new AgabaturGame();
		
		GameDaemon daemon = GameDaemon.initialize(game);
		
		TitleActionInterpreter title = new TitleActionInterpreter();
		daemon.activate(title);
	}
}
