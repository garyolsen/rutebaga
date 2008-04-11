package rutebaga.controller;

/**
 * @author may
 * 
 * ListElements are discrete elements in an itemized list. Each ListElement
 * offers a label describing itself and may further provide an associated
 * Command.
 */
public interface ListElement {
	/**
	 * Gets the label for this ListElement.
	 * 
	 * @return the label of this ListElement
	 */
	public String getLabel();

	/**
	 * Gets the Command for this ListElement. Since ListElements are not
	 * required to have associated Commands, this may return null.
	 * 
	 * @return this ListElement's Command, or null if there is none
	 */
	public Command getCommand();
}
