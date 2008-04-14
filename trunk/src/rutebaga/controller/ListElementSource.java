package rutebaga.controller;

import java.util.Iterator;

/**
 * 
 * ListElementSource is used to produce objects of a certain type for a
 * {@link DynamicElementalList}.
 * 
 * @see DynamicElementalList
 * @param <ElementType>
 *            The type of the elements to be generated.
 * @author may
 */
public interface ListElementSource<ElementType>
{
	/**
	 * Gets the current label that should be assigned to the
	 * {@link DynamicElementalList} as a whole.
	 * 
	 * @return the label of the ElementalList as a whole.
	 * @see DynamicElementalList
	 */
	public String getLabel();

	/**
	 * Return an iterator over the elements produced by this source.
	 * 
	 * @return an iterator of ElementTypes
	 */
	public Iterator<ElementType> iterator();
}
