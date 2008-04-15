package rutebaga.controller.list;

/**
 * 
 * A ListElementFactory is used to create {@link ListElement ListElements} for
 * objects of the parameterizing type.
 * 
 * @param <E>
 *            The type of the elements to be generated.
 * @author Matthew Chuah
 * @see ListElementSource
 * @see ListElement
 */
public interface ListElementFactory<E>
{

	/**
	 * Produces a {@link ListElement} containing the specified element.
	 * 
	 * @param element
	 *            the element to make a ListElement for
	 * @return a ListElement representing the specified element.
	 * @see ListElement
	 */
	public ListElement makeElement(E element);
}
