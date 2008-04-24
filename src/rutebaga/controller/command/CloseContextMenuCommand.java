package rutebaga.controller.command;

import rutebaga.view.UserInterfaceFacade;

public class CloseContextMenuCommand implements Command {

	private UserInterfaceFacade facade;
	
	public CloseContextMenuCommand(UserInterfaceFacade facade) {
		this.facade = facade;
	}
	
	public void execute() {
		facade.popMenu();
	}

	public boolean isFeasible() {
		return true;
	}
	
}