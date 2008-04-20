package rutebaga.controller;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.view.UserInterfaceFacade;
import rutebaga.view.ViewFacade;
import rutebaga.view.rwt.TextFieldListener;

/**
 * 
 * @author Matthew Chuah
 */
public class AvatarInitializationActionInterpreter implements UserActionInterpreter, TextFieldListener {

	public boolean eventsFallThrough() {
		// AvatarInitializationActionInterpreter is a 'root' interpreter
		return false;
	}

	public void installActionInterpreter(final GameDaemon daemon, final Game game, final UserInterfaceFacade facade) {
		Command accept = new Command() {
			private boolean activated = false;
			
			public void execute() {
				if(!activated) {
					GameInitializer gi = game.getGameInitializer();
					gi.build();
					UserActionInterpreter gpai = new GamePlayActionInterpreter(gi.getWorld(), gi.getAvatar());
					daemon.activate(gpai);
					activated = true;
				}
			}

			public boolean isFeasible() {
				return true;
			}
		};

		Command cancel = new Command() {
			public void execute() {
				daemon.deactivate(AvatarInitializationActionInterpreter.this);
			}

			public boolean isFeasible() {
				return true;
			}
		};

		ConcreteElementalList list = new ConcreteElementalList();
		
		// This is where you populate the list with Occupation buttons
		
		facade.createAvatarCreationScreen(this, list, accept, cancel);
		
		daemon.registerAsTextFieldListener(this);
	}

	public void reactivateActionInterpreter() {
		// Hmm...okay?
	}

	public void tick() {
		// AvatarInitializationActionInterpreters do not perform any tick based
		// actions
	}

	public void uninstallActionInterpreter() {
		// AvatarInitializationActionInterpreters do not require any cleanup
	}
	
	public void fieldChanged(String string) {
		
	}
}
