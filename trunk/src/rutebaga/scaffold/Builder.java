package rutebaga.scaffold;

/**
 * Builders are used to make specific instances of Objects. These instances are
 * identified by their identifying Strings.
 * 
 * The Objects created by Builder are generally game data. These Objects are
 * initialized, but are transient in that they do not retain state between
 * games.
 * <p>
 * Proper use follows
 * <ol>
 * <li>Override {@link #create(String)} to return a new Instance of the Object
 * this {@link Builder} is responsible for.
 * <li>Override {@link #availableIds()} to return the identifying Strings of
 * any specific Object this Builder will understand.
 * <li>Override {@link #initialize(String, Object, MasterScaffold)} to be able
 * to initialize an Object passed in (using its mutators) based on the
 * identifying String passed in.
 * </ol>
 * 
 * @author Gary
 * @see MasterScaffold
 */
public interface Builder
{
	Object create(String id);

	/**
	 * Initializes an Object based on the identifying String.
	 * {@link MasterScaffold#get(String)} is used to resolve references to other
	 * Objects.
	 * 
	 * @param id
	 *            The String identifying this specific Object and defining its
	 *            state.
	 * @param object
	 *            The Object to be mutated based on the id String.
	 * @param scaffold
	 *            The {@link MasterScaffold} that could be used to obtain
	 *            references to other Objects that might be linked to from the
	 *            Object being initialized.
	 */
	void initialize(String id, Object object, MasterScaffold scaffold);

	String[] availableIds();
}
