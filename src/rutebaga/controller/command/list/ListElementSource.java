package rutebaga.controller.command.list;

import java.util.Iterator;
import java.util.Observable;

/**
 * 
 * ListElementSource is used to produce objects of a certain type for a
 * {@link DynamicElementalList}.
 * 
 * @see DynamicElementalList
 * @param <E>
 *            The type of the elements to be generated.
 * @author Matthew Chuah
 */
public interface ListElementSource<E> {
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
	 * @return an iterator of Es
	 */
	public Iterator<E> iterator();
	
	/**
	 * Computes the size of this ListElementSource's source set.
	 * @return number of ListElements returned by this ListElementSource's iterator
	 */
	public int contentSize();
	
	/**
	 * Indicates whether <em>any</em> of the elements returned by this source have
	 * changed since the last time hasChanged was invoked by the specified Object.
	 * @return true if this ListElementSource's iterator will return different elements from the last time it was invoked.
	 */
	public boolean hasChanged(Object object);
}
