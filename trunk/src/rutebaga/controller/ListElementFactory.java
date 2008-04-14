package rutebaga.controller;

/**
 * 
 * A ListElementFactory is used to create {@link ListElement ListElements} for
 * objects of the parameterizing type.
 * 
 * @see ListElement
 * 
 * @param <ElementType>
 *            The type of the elements to be generated.
 * @author may
 * @see ListElementSource
 */
public interface ListElementFactory<ElementType>
{

	/**
	 * Produces a {@link ListElement} containing the specified element.
	 * 
	 * @param element
	 *            the element to make a ListElement for
	 * @return a ListElement representing the specified element.
	 * @see ListElement
	 */
	public ListElement makeElement(ElementType element);
}
