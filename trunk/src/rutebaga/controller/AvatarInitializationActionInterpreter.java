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

	private String name;
	
	public boolean eventsFallThrough() {
		// AvatarInitializationActionInterpreter is a 'root' interpreter
		return false;
	}

	public void installActionInterpreter(final GameDaemon daemon, final Game game, final UserInterfaceFacade facade) {
		Command accept = new Command() {
			private boolean isExecuting = false;

			private boolean checkName() {
				if (name == null || "".equals(name)) {
					ConcreteElementalList list = new ConcreteElementalList();
					list.setLabel("You need to enter a name.");
					list.add("OK", new Command() {
						public void execute() {
							facade.clearWarningBox();
						}
						public boolean isFeasible() {
							return true;
						}
					});
					facade.createWarningBox(list, true);
					return false;
				}
				return true;
			}
			
			public void execute() {
				isExecuting = true;

				if (!checkName()) {
					isExecuting = false;
					return;
				}
				
				ConcreteElementalList list = new ConcreteElementalList();
				list.setLabel("Initializing your new game...");
				facade.createWarningBox(list, true);
				
				GameInitializer gi = game.getGameInitializer();
				gi.build();
				UserActionInterpreter gpai = new GamePlayActionInterpreter(gi.getWorld(), gi.getAvatar());

				gi.getAvatar().setName(name);
				
				facade.clearWarningBox();
				// Connascence: you must deactivate this interpreter before activating the new one
				daemon.deactivate(AvatarInitializationActionInterpreter.this);
				daemon.activate(gpai);
				
				isExecuting = false;
			}

			public boolean isFeasible() {
				return !isExecuting;
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
		this.name = string;
	}
}
