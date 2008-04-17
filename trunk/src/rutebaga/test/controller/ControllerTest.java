package rutebaga.test.controller;

import java.util.Collection;

import rutebaga.controller.Game;
import rutebaga.controller.GameDaemon;
import rutebaga.controller.GameInitializer;
import rutebaga.controller.NewGameInitializer;
import rutebaga.controller.TitleActionInterpreter;
import rutebaga.scaffold.Builder;

public class ControllerTest {
	private static int SCREENWIDTH = 800, SCREENHEIGHT = 600;

	public static void main(String args[]) {

		Game game = new Game() {
			GameInitializer init = new NewGameInitializer(null/*scaffold*/);
			public GameInitializer getGameInitializer() {
				return init;
			}
			
			public Collection<Builder> getBuilders() {
				return null;
			}
		};
		
		GameDaemon daemon = GameDaemon.initialize(game);
		
		TitleActionInterpreter title = new TitleActionInterpreter();
		
		daemon.activate(title);
	}
}
