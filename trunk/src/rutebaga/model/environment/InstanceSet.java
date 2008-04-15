package rutebaga.model.environment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Convenience wrapper.
 * 
 * @author Gary LosHuertos
 * 
 */
public class InstanceSet implements Set<Instance>
{
	private static final long serialVersionUID = 358791980720848943L;
	
	private Set<Instance> backingSet = new HashSet<Instance>();

	public boolean add(Instance arg0)
	{
		return backingSet.add(arg0);
	}

	public boolean addAll(Collection<? extends Instance> arg0)
	{
		return backingSet.addAll(arg0);
	}

	public void clear()
	{
		backingSet.clear();
	}

	public boolean contains(Object arg0)
	{
		return backingSet.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0)
	{
		return backingSet.containsAll(arg0);
	}

	public boolean equals(Object arg0)
	{
		return backingSet.equals(arg0);
	}

	public int hashCode()
	{
		return backingSet.hashCode();
	}

	public boolean isEmpty()
	{
		return backingSet.isEmpty();
	}

	public Iterator<Instance> iterator()
	{
		return backingSet.iterator();
	}

	public boolean remove(Object arg0)
	{
		return backingSet.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0)
	{
		return backingSet.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0)
	{
		return backingSet.retainAll(arg0);
	}

	public int size()
	{
		return backingSet.size();
	}

	public Object[] toArray()
	{
		return backingSet.toArray();
	}

	public <T> T[] toArray(T[] arg0)
	{
		return backingSet.toArray(arg0);
	}
}
