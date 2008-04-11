package rutebaga.controller;

import rutebaga.controller.command.Command;

/**
 * 
 * ListElements are discrete elements in an itemized list. Each ListElement
 * offers a label describing itself and may further provide an associated
 * {@link Command}.
 * @see Command
 * @author may
 */
public interface ListElement {
	/**
	 * Gets the label for this ListElement.
	 * 
	 * @return the label of this ListElement
	 */
	public String getLabel();

	/**
	 * Gets the {@link Command} for this ListElement. Since ListElements are not
	 * required to have associated Commands, this may return null.
	 * 
	 * @return this ListElement's {@link Command}, or null if there is none
	 * @see Command
	 */
	public Command getCommand();
}
