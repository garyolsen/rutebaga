package rutebaga.scaffold;

/**
 * Builders are used to make specific instances of Objects. These instances are
 * identified by their string keys.
 * 
 * The Objects created by Builder are generally game data. These Objects are
 * initialized, but are transient in that they do not retain state between
 * games.
 * 
 * @author Gary
 * 
 */
public interface Builder {
	Object create(String id);

	void initialize(String id, Object object, MasterScaffold scaffold);

	String[] availableIds();
}
