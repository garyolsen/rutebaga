package rutebaga.controller;

import java.util.Iterator;

/**
 * @author may
 *
 * An ElementalList is an ordered collection of ListElements.  Clients
 * access ListElements via the provided Iterator.  ElementalLists allow
 * the Controller to encapsulate a list of named Commands to pass to the
 * View. An instance of ElementalList may return a unique Iterator each
 * time iterator is invoked. ElementalLists also provide a label possibly
 * describing the collection as a whole. Clients of ElementalList should
 * be aware that implementations ElementalList may return a different
 * label and collection of ListElements each time the list is queried.
 */
public interface ElementalList extends Iterable<ListElement> {

	/**
	 * Gets the current label for this ElementalList.
	 * @return the label for this ElementalList
	 */
	public String getLabel();
	
	/**
	 * Gets an Iterator over the ListElements of this ElementalList.
	 * @return an Iterator of ListElements
	 */
	public Iterator<ListElement> iterator();
}
