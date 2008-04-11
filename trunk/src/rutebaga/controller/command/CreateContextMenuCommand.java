package rutebaga.controller.command;

import rutebaga.controller.ElementalList;
import rutebaga.view.ViewFacade;

/**
 * @author may
 * 
 * OpenContextMenuCommand is a high-level convenience class for opening a
 * context menu with a set of Commands in the form of an ElementalList. When
 * executed, an OpenContextMenuCommand simply asks the View to open a new
 * sub-context menu with the specified list of Commands (in the form of an
 * ElementalList).
 */
public abstract class CreateContextMenuCommand implements Command {

	private ElementalList elements;

	private ViewFacade facade;

	/**
	 * Set the list of elements to put in the context menu.
	 * 
	 * @param list
	 *            a list of elements to display
	 */
	public final void setElements(ElementalList list) {
		elements = list;
	}

	/**
	 * Set the ViewFacade that this OpenContextMenuCommand should ask to open a
	 * context menu on.
	 * 
	 * @param view
	 */
	public final void setViewFacade(ViewFacade view) {
		facade = view;
	}

	/**
	 * Asks the ViewFacade to open a context menu with the list of elements
	 * specified. 
	 * @see rutebaga.controller.command.Command#execute()
	 */
	public abstract void execute();

	/**
	 * Always returns true, indicating that this command may always be executed.
	 * 
	 * @see rutebaga.controller.command.Command#isFeasible()
	 */
	public boolean isFeasible() {
		return true;
	}
	
	/**
	 * Provides access to this CreateContextMenuCommand's ViewFacade, allowing
	 * subclasses to access it in their execute method.
	 * @return
	 */
	protected ViewFacade getViewFacade() { 
		return facade;
	}
	
	/**
	 * Provides access to this CreateContextMenuCommand's ElementalList, allowing
	 * subclasses to access it in their execute method. 
	 * @return this CreateContextMenuCommand's ElementalList
	 */
	protected ElementalList getElements() {
		return elements;
	}
}
