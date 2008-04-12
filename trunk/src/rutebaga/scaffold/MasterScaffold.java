package rutebaga.scaffold;

import java.util.HashMap;
import java.util.Map;

/**
 * The MasterScaffold initializes and keeps track of Objects that are "on the
 * scaffold" used in game initialization.
 * 
 * MasterScaffold is responsible for providing access to Objects based on a
 * unique key. MasterScaffold delegates responsibility for initializing and
 * creating objects to the {@link Builder} registered with that key.
 * 
 * @author Gary
 * @see Builder
 */
public class MasterScaffold {
	private Map<String, Object> scaffold = new HashMap<String, Object>();
	private Map<String, Builder> builders = new HashMap<String, Builder>();

	/**
	 * Returns an Object corresponding to an identifying string. Clients are
	 * assured that every call to get(String) will return the exact same Object
	 * instance when passed the same String.
	 * 
	 * @param id
	 *            The String associated with the Object to be fetched from the
	 *            MasterScaffold.
	 * @return The Object associated with the id String.
	 */
	public Object get(String id) {
		if (scaffold.get(id) == null) {
			Builder builder = builders.get(id);
			Object object = builder.create(id);
			scaffold.put(id, object);
			builder.initialize(id, object, this);
		}
		return scaffold.get(id);
	}

	public void setBuilder(String id, Builder builder) {
		this.builders.put(id, builder);
	}

	public void build() {
		for (String id : builders.keySet()) {
			get(id);
		}
	}

	/**
	 * Adds a {@link Builder} to the MasterScaffold. There must be a
	 * {@link Builder} registered on the MasterScaffold to handle any possible
	 * call to {@link #get(String)}. Each particular {@link Builder} registered
	 * will handle a particular type. Each Builder will know how to initialize
	 * at least one instance of its type based on a String passed to
	 * {@link #get(String)};
	 * 
	 * @param builder
	 *            A {@link Builder} to add to the MasterScaffold.
	 * @see Builder
	 */
	public void registerBuilder(Builder builder) {
		for (String id : builder.availableIds()) {
			builders.put(id, builder);
		}
	}
}
