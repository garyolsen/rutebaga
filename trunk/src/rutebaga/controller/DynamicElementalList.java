package rutebaga.controller;

import java.util.Iterator;

/**
 * 
 * DynamicElementalList is an implementation of {@link ElementalList} which
 * dynamically constructs the {@link ListElement ListElements} returned by its
 * Iterator on the fly (when the Iterator is requested). The
 * DynamicElementalList must be initialized with a {@link ListElementSource} and
 * a {@link ListElementFactory}, both of which must be parameterized by the
 * same type as the DynamicElementalList. The ListElementSource is used to
 * produce objects of the parameterizing type. These objects are then fed to the
 * ListElementFactory, which creates the ListElements returned by the
 * DynamicElementalList's Iterator.
 * 
 * The DynamicElementalList may internally cache ListElements if it detects that
 * the source has not changed.
 * 
 * @see ListElement
 * @see ListElementSource
 * @see ListElementFactory
 * @author may
 * @param <ElementType>
 *            the type of thing to be represented in the list.
 */
public class DynamicElementalList<ElementType> implements ElementalList
{

	/**
	 * The source of ElementType objects for ListElements returned by this
	 * ElementalList's Iterator.
	 */
	private final ListElementSource<ElementType> source;

	/**
	 * This DynamicElementalList's factory, which creates ListElements from
	 * ElementType objects produced by the source.
	 */
	private final ListElementFactory<ElementType> factory;

	/**
	 * Create a new DynamicElementalList with the specified Source of the
	 * parameterizing ElementType and the specified {@link ListElementFactory}
	 * for creating {@link ListElement ListElements} from the parameterizing
	 * ElementType.
	 * 
	 * @param source
	 *            the {@link ListElementSource} for this DynamicElementalList
	 * @param factory
	 *            the {@link ListElementFactory} for this DynamicElementalList
	 * @see ListElementSource
	 * @see ListElementFactory
	 * @see ListElement
	 */
	public DynamicElementalList(ListElementSource<ElementType> source,
			ListElementFactory<ElementType> factory)
	{
		if (source == null || factory == null)
			throw new NullPointerException(
					"Neither the source nor factory of a DynamicElementalList may be null");
		this.source = source;
		this.factory = factory;
	}

	/**
	 * Gets the current label for this DynamicElementalList describing the
	 * collection as a whole.
	 * 
	 * @return the label for this DynamicElementalList
	 * @see rutebaga.controller.ElementalList#getLabel()
	 */
	public String getLabel()
	{
		return source.getLabel();
	}

	/**
	 * Gets an Iterator over the {@link ListElement ListElements} of this
	 * DynamicElementalList.
	 * 
	 * @see ListElement
	 * @return an Iterator of ListElements
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<ListElement> iterator()
	{
		/*
		 * TODO: Add the capability to cache ListElements so we aren't creating
		 * dozens of objects 30 times a second.
		 */
		return new Iterator<ListElement>()
		{
			private Iterator<ElementType> source = DynamicElementalList.this.source
					.iterator();

			public boolean hasNext()
			{
				return source.hasNext();
			}

			public ListElement next()
			{
				return factory.makeElement(source.next());
			}

			public void remove() throws UnsupportedOperationException
			{
				throw new UnsupportedOperationException();
			}
		};
	}

}
