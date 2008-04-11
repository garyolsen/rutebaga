package rutebaga.controller;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;


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

	/**
	 * The label of this ConcreteElementalList.
	 */
	private String label;
	
	/**
	 * The list of ElementalLists for this ConcreteElementalList.  The
	 * Iterator iterates over this list and then over the ListElements
	 * within each ElementalList.
	 */
	private LinkedList<ElementalList> list = new LinkedList<ElementalList>();
	
	/**
	 * Add a String to this ConcreteElementalList without a Command.
	 * @param label the String element to add to this list
	 */
	public void add(String label) {
		add(label, null);
	}
	
	/**
	 * Add a named Command to this ConcreteElementalList.  It is admissible
	 * for the label to be null, in which case it will be replaced by the empty
	 * String.
	 * @param label the String element to add to this list
	 * @param command the Command to associate with this list element
	 */
	public void add(String label, Command command) {
		// To hell with efficiency!
		list.add(new SingleCommandElementalList(label, command));
	}
	
	/**
	 * Add an entire ElementalList as a component of this ConcreteElementalList.
	 * When this ConcreteElementalList's iterator runs, it will visit each of
	 * the specified ElementalList's ListElements in the order its Iterator
	 * returns.
	 * @param list the ElementalList to add as a child to this ConcreteElementalList
	 */
	public void add(ElementalList list) {
		if (list != null)
			this.list.add(list);
	}
	
	/* (non-Javadoc)
	 * @see rutebaga.controller.ElementalList#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label of this ConcreteElementalList.
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = (label == null ? label : "");
	}
	
	/**
	 * Returns an Iterator over all the ListElements contained by this
	 * ConcreteElementalList.  If an entire ElementalList has been added
	 * as a child to this ConcreteElementalList, each of the child's 
	 * ListElements will be returned by this Iterator.
	 * @see ElementalList#iterator()
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<ListElement> iterator() {
		return new ConcreteElementalListIterator();
	}

	/**
	 * @author may
	 *
	 * A private Iterator that iterates through all the ElementalLists contained
	 * by the parent ConcreteElementalList, visiting each of their ListElements
	 * in turn.
	 */
	private class ConcreteElementalListIterator implements Iterator<ListElement> {
		private Iterator<ElementalList> lists = ConcreteElementalList.this.list.iterator();
		private Iterator<ListElement> currentList;
		
		/**
		 * Determines if there is another unvisited ListElement in the current
		 * ElementalList visited or an unvisited ElementalList contained by
		 * this ConcreteElementalList.
		 */
		public boolean hasNext() {
			return lists.hasNext() || currentList.hasNext();
		}
		
		/**
		 * Returns the next unvisited ListElement.
		 * @return an unvisited ListElement
		 */
		public ListElement next() {
			// currentList is spent, go to next
			while (currentList == null || !currentList.hasNext()) {
				// lists are spent...the end
				if (!lists.hasNext())
					throw new NoSuchElementException("No further ListElements");
				currentList = lists.next().iterator();
			}
			return currentList.next();
		}
		
		/**
		 * Don't even think about it.
		 */
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("Bad! Bad!");
		}
	}
	
	/**
	 * @author may
	 *
	 * A wrapper for a String-Command list element.  This ElementalList
	 * contains a single ListElement, which is accessed via its Iterator,
	 * that contains the label-command pair the SingleCommandElementalList
	 * was constructed with. 
	 */
	private class SingleCommandElementalList implements ElementalList {

		/**
		 * The label of the ListElement "contained" by this
		 * SingleCommandElementalList.
		 */
		private String label;
		
		/**
		 * The command of the ListElement "contained" by this
		 * SingleCommandElementalList. 
		 */
		private Command command;
		
		/**
		 * Create a new SingleCommandElementalList with the specified label
		 * and command.  If label is null, it will be replaced by the empty
		 * string.
		 * @param label the label of the ListElement to be contained by this SingleCommandElementalList
		 * @param command the command of the ListElement to be contained by this SingleCommandElementalList
		 */
		public SingleCommandElementalList(String label, Command command) {
			this.label = (label == null ? label : "");
			this.command = command;
		}
		
		public String getLabel() {
			return label;
		}

		/**
		 * Returns an Iterator over a single ListElement defined by the label
		 * and command this SingleCommandElementalList was created with.
		 * @see java.lang.Iterable#iterator()
		 */
		public Iterator<ListElement> iterator() {
			return new Iterator<ListElement>() {
				private boolean visited = false;
				
				public boolean hasNext() {
					return !visited;
				}
				
				public ListElement next() {
					if (visited)
						throw new NoSuchElementException("Only one ListElement!");
					else {
						visited = true;
						return new ListElement() {
							public String getLabel() {
								return SingleCommandElementalList.this.label;
							}
							
							public Command getCommand() {
								return SingleCommandElementalList.this.command;
							}
						};
					}
				}
				
				public void remove() throws UnsupportedOperationException {
					throw new UnsupportedOperationException("Mommy, make the bad man stop ><");
				}
			};
		}
		
	}
}
