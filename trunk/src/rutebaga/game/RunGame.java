package rutebaga.game;

import rutebaga.controller.Game;
import rutebaga.controller.GameDaemon;
import rutebaga.controller.TitleActionInterpreter;

public class RunGame
{
	public static void main(String args[]) {

		Game game = new AgabaturGame();
		
		GameDaemon daemon = GameDaemon.initialize(game);
		
		TitleActionInterpreter title = new TitleActionInterpreter();
		daemon.activate(title);
	}
}
