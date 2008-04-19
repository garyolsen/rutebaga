package rutebaga.controller;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.view.UserInterfaceFacade;
import rutebaga.view.ViewFacade;

public class TitleActionInterpreter implements UserActionInterpreter {

	private UserInterfaceFacade ui;
	
	private GameDaemon daemon;
	
	public void installActionInterpreter(GameDaemon daemon, Game game,
			UserInterfaceFacade facade) {
		
		this.ui = facade;
		this.daemon = daemon;
	
		prepareTitleScreen();
	}

	private void prepareTitleScreen() {
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

		ui.createTitleScreen(list);
	}
	
	public void reactivateActionInterpreter() {
		prepareTitleScreen();
	}

	public boolean eventsFallThrough() {
		// TitleActionInterpreter is a 'root' controller
		return false;
	}

	public void tick() {
		// TitleActionInterpreter doesn't care about your puny ticks
	}

	public void uninstallActionInterpreter() {
		// TODO Auto-generated method stub
	}
}
