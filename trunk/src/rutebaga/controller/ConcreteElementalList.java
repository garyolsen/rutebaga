package rutebaga.controller;

import java.util.Iterator;


/**
 * @author may
 *
 * ConcreteElementalList is a basic implementation of ElementalList that
 * composites several named Commands. The ConcreteElementalList is also
 * labeled, as required by its interface.
 *
 * ConcreteElementalList offers two add mutators, one which adds a named
 * String-Command pair and one which adds a set of Commands in the form
 * of an ElementalList. Composited Commands cannot be removed, but can be
 * accessed via the Iterator, as per the interface. The Iterator will
 * return ListElements containing the Commands in the same order they
 * were added to the ConcreteElementalList. Commands added to the
 * ConcreteElementalList via the add(ElementalList) operation will be
 * returned in the same order the ElementalList Iterator returns them.
 * Duplicate Commands are both possible and allowed, so clients of
 * ConcreteElementalList must take care to avoid duplicates if they
 * desire Set semantics.
 */
public class ConcreteElementalList implements ElementalList {

	public void add(String label, Command command) {
		
	}
	
	public void add(ElementalList list) {
		
	}
	
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setString() {
		
	}
	
	public Iterator<ListElement> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
