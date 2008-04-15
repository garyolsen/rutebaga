package rutebaga.controller.command;

import rutebaga.commons.Vector;
import rutebaga.controller.list.ElementalList;

/**
 * @author Matthew Chuah
 */
public class CreateRootContextMenuCommand extends CreateContextMenuCommand {
	/**
	 * Create a new CreateRootContextMenuCommand with an empty command list.
	 */
	public CreateRootContextMenuCommand() {
		super();
	}

	/**
	 * Create a new CreateRootContextMenuCommand with the given command list,
	 * specified as an ElementalList.
	 * 
	 * @param list
	 *            a list of commands to place in the menu
	 */
	public CreateRootContextMenuCommand(ElementalList list) {
		super(list);
	}

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
	@Override
	public void execute() {
		getViewFacade().createRootContextMenu(getElements(), location);
	}
}
