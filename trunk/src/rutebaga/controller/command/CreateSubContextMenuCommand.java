package rutebaga.controller.command;

import rutebaga.controller.command.list.ElementalList;


/**
 * This {@link Command} causes the {@link rutebaga.view.ViewFacade ViewFacade}
 * to open a sub context menu with the previously specified set of elements.
 * 
 * @author Matthew Chuah
 */
public class CreateSubContextMenuCommand extends CreateContextMenuCommand {
	
	public CreateSubContextMenuCommand() {
		super();
	}
	
	public CreateSubContextMenuCommand(ElementalList list) {
		super(list);
	}
	
	/**
	 * Causes the previously specified
	 * {@link rutebaga.view.ViewFacade ViewFacade} to open a sub context menu
	 * with the previously specified set of elements.
	 * 
	 * @see rutebaga.controller.Command#execute()
	 */
	@Override
	public void execute() {
		getUIFacade().createSubContextMenu(getElements());
	}
}
