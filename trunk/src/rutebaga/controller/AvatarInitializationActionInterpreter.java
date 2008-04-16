package rutebaga.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.list.ConcreteElementalList;
import rutebaga.view.ViewFacade;
import rutebaga.view.rwt.TextFieldListener;

/**
 * 
 * @author Matthew Chuah
 */
public class AvatarInitializationActionInterpreter extends KeyAdapter implements
		UserActionInterpreter, TextFieldListener {

	public boolean eventsFallThrough() {
		// AvatarInitializationActionInterpreter is a 'root' interpreter
		return false;
	}

	public void installActionInterpreter(final GameDaemon daemon, final ViewFacade facade) {
		Command accept = new Command() {
			public void execute() {
				// Start game play
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
	}

	public void tick() {
		// AvatarInitializationActionInterpreters do not perform any tick based
		// actions
	}

	public void uninstallActionInterpreter() {
		// AvatarInitializationActionInterpreters do not require any cleanup
	}

	public void keyPressed(KeyEvent arg0) {
		// AvatarInitializationActionInterpreters do not recognize any
		// ActionEvents
	}

	public void actionPerformed(ActionEvent arg0) {
		// AvatarInitializationActionInterpreters do not recognize any
		// ActionEvents
	}
}
