package rutebaga.controller;

/**
 * @author may
 *
 * A ListElementFactory is used to create ListElements for objects
 * of the parameterizing type.
 *
 * @param <ElementType>
 */
public interface ListElementFactory<ElementType> {

	/**
	 * Produces a ListElement containing the specified element.
	 * @param element the element to make a ListElement for
	 * @return a ListElement representing the specified element.
	 */
	public ListElement makeElement(ElementType element);
}
