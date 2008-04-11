package rutebaga.controller;

import java.util.Iterator;

/**
 * @author may
 * 
 * ListElementSource is used to produce objects of a certain type for a
 * DynamicElementalList.
 * 
 * @param <ElementType>
 */
public interface ListElementSource<ElementType> {
	/**
	 * Gets the current label that should be assigned to the
	 * DynamicElementalList as a whole.
	 * @return the label of the ElementalList as a whole.
	 */
	public String getLabel();

	/**
	 * Return an iterator over the elements produced by this source.
	 * @return an iterator of ElementTypes
	 */
	public Iterator<ElementType> iterator();
}
