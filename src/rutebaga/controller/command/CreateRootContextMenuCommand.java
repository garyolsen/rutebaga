package rutebaga.controller.command;

import rutebaga.commons.Vector;

public class CreateRootContextMenuCommand extends CreateContextMenuCommand {

	/**
	 * The map location at which to open this root context menu.
	 */
	private Vector location;

	/**
	 * Set the map location at which this command should open a context menu.
	 * 
	 * @param loc
	 */
	public void setLocation(Vector loc) {
		location = loc;
	}
	
	/**
	 * Create a root context menu with the previously specified ElementalList at
	 * the previously given map location.
	 * 
	 * @see rutebaga.controller.command.Command#execute()
	 */
	public void execute() {
		getViewFacade().createRootContextMenu(getElements(), location);
	}
}
