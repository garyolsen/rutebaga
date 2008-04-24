package rutebaga.controller.command;

import rutebaga.view.UserInterfaceFacade;

public class CloseContextMenuCommand implements Command {

	private UserInterfaceFacade facade;
	
	public CloseContextMenuCommand(UserInterfaceFacade facade) {
		this.facade = facade;
	}
	
	public void execute() {
		//facade.popMenu();
		facade.clearMenuStack();
	}

	public boolean isFeasible() {
		return true;
	}
	
}