package rutebaga.test.controller;

import rutebaga.controller.GameDaemon;
import rutebaga.controller.TitleActionInterpreter;

public class ControllerTest {
	private static int SCREENWIDTH = 800, SCREENHEIGHT = 600;

	public static void main(String args[]) {

		GameDaemon daemon = GameDaemon.initialize();
		
		TitleActionInterpreter title = new TitleActionInterpreter();
		
		daemon.activate(title);
	}
}
