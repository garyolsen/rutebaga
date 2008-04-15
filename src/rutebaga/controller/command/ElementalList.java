package rutebaga.controller.command;

import java.util.Iterator;


/**
 * 
 * An ElementalList is an ordered collection of {@link ListElement ListElements}.
 * Clients access ListElements via the provided Iterator. ElementalLists allow
 * the Controller to encapsulate a list of named {@link Command Commands} to
 * pass to the View. Clients of ElementalList should be aware that
 * implementations of ElementalList may return a different label and collection
 * of ListElements each time the list is queried.
 * 
 * @author Matthew Chuah
 * @see Command
 * @see ListElement
 */
public interface ElementalList extends Iterable<ListElement>
{

	/**
	 * Gets the current label for this ElementalList describing the collection
	 * as a whole.
	 * 
	 * @return the label for this ElementalList
	 */
	public String getLabel();

	/**
	 * Gets an Iterator over the {@link ListElement ListElements} of this
	 * ElementalList.
	 * 
	 * @see ListElement
	 * @return an Iterator of ListElements
	 */
	public Iterator<ListElement> iterator();
}
