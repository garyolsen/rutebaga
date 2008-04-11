package rutebaga.controller;

/**
 * This {@link Command} causes the {@link rutebaga.view.ViewFacade ViewFacade} to open a sub context menu with
 * the previously specified set of elements.
 * 
 * @author may
 * 
 */
public class CreateSubContextMenuCommand extends CreateContextMenuCommand {
	/**
	 * Causes the previously specified {@link rutebaga.view.ViewFacade ViewFacade} to open a sub context menu
	 * with the previously specified set of elements.
	 * 
	 * @see rutebaga.controller.Command#execute()
	 */
	public void execute() {
		getViewFacade().createSubContextMenu(getElements());
	}
}
