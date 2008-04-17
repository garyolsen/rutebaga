package rutebaga.test.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import rutebaga.controller.Game;
import rutebaga.controller.GameDaemon;
import rutebaga.controller.UserActionInterpreter;
import rutebaga.view.ViewFacade;

public class GameDaemonTest
{
	public static void main(String[] args)
	{
		GameDaemon daemon = GameDaemon.initialize(null);

		TestInterpreter i1 = new TestInterpreter("Interpreter 1");
		TestInterpreter i2 = new TestInterpreter("Interpreter 2");
		TestInterpreter i3 = new TestInterpreter("Interpreter 3");

		daemon.activate(i1);
		daemon.activate(i2);
		daemon.activate(i3);

		daemon.tick();

		daemon.deactivate(i3);

		daemon.actionPerformed(null);

		daemon.deactivate(i1);
	}

	private static class TestInterpreter implements UserActionInterpreter
	{

		private String name;

		public void reactivateActionInterpreter() {
			System.out.println(name + " received a reactivation request");
		}

		public TestInterpreter(String name)
		{
			this.name = name;
		}

		public boolean eventsFallThrough()
		{
			return true;
		}

		public void installActionInterpreter(GameDaemon daemon, Game game,
				ViewFacade facade)
		{
			System.out.println(name + " received an install request");
		}

		public void tick()
		{
			System.out.println(name + " received a tick");
		}

		public void uninstallActionInterpreter()
		{
			System.out.println(name + " received an uninstall request");
		}

		public void actionPerformed(ActionEvent arg0)
		{
			System.out.println(name
					+ " received an actionPerformed ActionEvent");
		}

		public void keyPressed(KeyEvent arg0)
		{
			System.out.println(name + " received a keyPressed KeyEvent");
		}

		public void keyReleased(KeyEvent arg0)
		{
		}

		public void keyTyped(KeyEvent arg0)
		{
		}
	}
}
