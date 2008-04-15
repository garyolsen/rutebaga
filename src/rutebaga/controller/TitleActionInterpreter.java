package rutebaga.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import rutebaga.controller.command.Command;
import rutebaga.controller.list.ConcreteElementalList;
import rutebaga.view.ViewFacade;

public class TitleActionInterpreter implements UserActionInterpreter {

	public void installActionInterpreter(final GameDaemon daemon,
			final ViewFacade facade) {
		ConcreteElementalList list = new ConcreteElementalList();

		list.add("Load Game", new Command() {
			public boolean isFeasible() {
				return true;
			}

			public void execute() {

			}
		});

		list.add("New Game", new Command() {
			public boolean isFeasible() {
				return true;
			}

			public void execute() {
				UserActionInterpreter uai = new AvatarInitializationActionInterpreter();
				daemon.activate(uai);
			}
		});

		list.add("Exit", new Command() {
			public boolean isFeasible() {
				return true;
			}

			public void execute() {
				System.exit(0);
			}
		});

		facade.createTitleScreen(list);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TitleActionInterpreter doesn't care about your silly actions
	}

	public boolean eventsFallThrough() {
		// TitleActionInterpreter is a 'root' controller
		return false;
	}

	public void tick() {
		// TitleActionInterpreter doesn't care about your ticks
	}

	public void uninstallActionInterpreter() {
		// TODO Auto-generated method stub
	}

	public void keyPressed(KeyEvent arg0) {
		// TitleActionInterpreter doesn't care about your extraneous key presses
	}

	public void keyReleased(KeyEvent arg0) {
		// TitleActionInterpreter doesn't care about your extraneous key
		// releases
	}

	public void keyTyped(KeyEvent arg0) {
		// TitleActionInterpreter doesn't care about your extraneous key typed
	}
}
