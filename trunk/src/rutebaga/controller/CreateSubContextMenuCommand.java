package rutebaga.controller;

public class CreateSubContextMenuCommand extends CreateContextMenuCommand {
	/**
	 * Causes the previously specified ViewFacade to open a sub context menu
	 * with the previously specified set of elements.
	 * 
	 * @see rutebaga.controller.Command#execute()
	 */
	public void execute() {
		getViewFacade().createSubContextMenu(getElements());
	}
}
