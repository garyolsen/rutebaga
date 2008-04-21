package rutebaga.scaffold;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceType;

/**
 * The MasterScaffold initializes and keeps track of Objects that are "on the
 * scaffold" and used in game initialization.
 * 
 * MasterScaffold is responsible for providing access to Objects based on a
 * unique key. MasterScaffold delegates responsibility for initializing and
 * creating objects to the {@link Builder} registered with that key.
 * 
 * There should be a {@link Builder} registered on the MasterScaffold to handle
 * any foreseeable call to {@link #get(String)}.
 * <p>
 * Following is the intended use of MasterScaffold
 * <ol>
 * <li>Register all required builders.
 * <li>Call {@link #build()} to initialize all Objects.
 * <li>Use {@link #get(String)} as needed.
 * </ol>
 * 
 * @author Gary
 * @see Builder
 * @see #registerBuilder(Builder)
 * @see #get(String)
 */
public class MasterScaffold
{
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
	public Object get(String id)
	{
		if(id == null) return null;
		if (scaffold.get(id) == null)
		{
			Builder builder = builders.get(id);
			if(builder == null) return null;
			Object object;
			try
			{
				object = builder.create(id);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException();
			}
			scaffold.put(id, object);
			builder.initialize(id, object, this);
		}
		return scaffold.get(id);
	}
	
	public boolean contains(String id)
	{
		return builders.get(id) != null;
	}

	/**
	 * Adds a {@link Builder} to the MasterScaffold. Unlike
	 * {@link #registerBuilder(Builder)}, which instructs the MasterScaffold to
	 * use the provided Builder on calls to {@link #get(String)} for all id
	 * Strings that {@link Builder} understands, setBuilder(String,Builder}
	 * instructs the MasterScaffold to use the provided {@link Builder} on calls
	 * to {@link #get(String)} only for this id String.
	 * 
	 * @param id
	 *            The String identifying a type of Object that should be
	 *            associated with the Builder builder.
	 * @param builder
	 *            A {@link Builder} to add to the MasterScaffold for building
	 *            Objects associated with this specific String id.
	 * @see Builder
	 * @see #get(String)
	 * @see #registerBuilder(Builder)
	 */
	public void setBuilder(String id, Builder builder)
	{
		this.builders.put(id, builder);
	}

	/**
	 * Because Objects are lazy-loaded when {@link #get(String)} is called,
	 * build() should be called once before any call to {@link #get(String)}
	 * (after all the {@link Builder Builders} have been registered with the
	 * MasterScaffold). This guarantees that any Object returned is not in an
	 * inconsistent state, and moreover any call to {@link #get(String)} won't
	 * take too long as all the hard work of Object initialization is taken care
	 * of with build().
	 * 
	 * @see #get(String)
	 * @see Builder
	 */
	public void build()
	{
		for (String id : builders.keySet())
		{
			get(id);
		}
	}

	/**
	 * Adds a {@link Builder} to the MasterScaffold. Each registered
	 * {@link Builder} should know how to handle at least one particular type
	 * based on a String passed into {@link #get(String)}. Unlike
	 * {@link #setBuilder(String, Builder)}, which instructs the MasterScaffold
	 * to use the specified {@link Builder} only in the case of the specified
	 * identifying String, registerBuilder(Builder) instructs the MasterScaffold
	 * to use the provided {@link Builder} on all calls to {@link #get(String)}
	 * for Strings the specified {@link Builder} understands.
	 * 
	 * @param builder
	 *            A {@link Builder} to add to the MasterScaffold.
	 * @see Builder
	 * @see #setBuilder(String, Builder)
	 * @see #get(String)
	 */
	public void registerBuilder(Builder builder)
	{
		for (String id : builder.availableIds())
		{
			System.out.println("Registering " + id + " with " + builder);
			builders.put(id, builder);
		}
	}

	public Instance makeInstance(String type)
	{
		return ((InstanceType<?>) get(type)).makeInstance();
	}
	
	public Set<String> getKeys()
	{
		return Collections.unmodifiableSet(scaffold.keySet());
	}
}
