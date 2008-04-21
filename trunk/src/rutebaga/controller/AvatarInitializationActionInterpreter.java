package rutebaga.controller;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.LabelDeterminer;
import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.model.entity.Entity;
import rutebaga.view.UserInterfaceFacade;
import rutebaga.view.ViewFacade;
import rutebaga.view.rwt.TextFieldListener;

/**
 * 
 * @author Matthew Chuah
 */
public class AvatarInitializationActionInterpreter implements UserActionInterpreter, TextFieldListener {

	private String name;
	private int job = 0;
	
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
				Entity<?>[] avatars = gi.getAvatars();
				UserActionInterpreter gpai = new GamePlayActionInterpreter(gi.getWorld(), avatars[job], gi.getDisplayedStats());

				avatars[job].setName(name);
				
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
		
		list.setLabel(new DefaultNameProvider().getLabel());
	
		list.add("Plumber", new Command() {
			public void execute() {
				job = 0;
			}
			public boolean isFeasible() {
				return true;
			}
		});
		list.add("Lurker", new Command() {
			public void execute() {
				job = 1;
			}
			public boolean isFeasible() {
				return true;
			}
		});
		list.add("Dino", new Command() {
			public void execute() {
				job = 2;
			}
			public boolean isFeasible() {
				return true;
			}
		});

		daemon.registerAsTextFieldListener(this);
		facade.createAvatarCreationScreen(this, list, accept, cancel);
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
	
	private class DefaultNameProvider implements LabelDeterminer {
		public String getLabel() {
			try {
				return System.getProperty("user.name", "Dave");
			}
			catch (Exception e) {
				return "Dave";
			}
		}
	}
}
