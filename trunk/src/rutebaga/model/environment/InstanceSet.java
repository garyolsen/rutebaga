package rutebaga.model.environment;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Convenience wrapper.
 * 
 * @author Gary LosHuertos
 * 
 */
public class InstanceSet implements Set<Instance>
{
	protected class InstanceSetIterator implements Iterator<Instance>
	{
		Iterator<InstanceSetIdentifier> typeIterator = sets.keySet().iterator();
		Iterator<Instance> currentIterator;
		
		public InstanceSetIterator()
		{
			advanceInstanceIterator();
		}
		
		public boolean hasNext()
		{
			return currentIterator.hasNext();
		}

		public Instance next()
		{
			Instance rval = currentIterator.next();
			if(!currentIterator.hasNext())
				advanceInstanceIterator();
			return rval;
		}

		public void remove()
		{
			throw new UnsupportedOperationException("You tried to do WHAT?");
		}

		private void advanceInstanceIterator()
		{
			currentIterator = sets.get(typeIterator.next()).iterator();
			if(!currentIterator.hasNext() && typeIterator.hasNext())
				advanceInstanceIterator();
		}
		
	}
	
	public static enum InstanceSetIdentifier
	{
		ENTITY, ITEM, EFFECT, TILE;
	}
	
	private static final long serialVersionUID = 358791980720848943L;

	private Map<InstanceSetIdentifier, Set<Instance>> sets = new HashMap<InstanceSetIdentifier, Set<Instance>>();
	
	int size = 0;

	public InstanceSet()
	{
		for (InstanceSetIdentifier type : InstanceSetIdentifier.values())
		{
			sets.put(type, new HashSet<Instance>());
		}
	}

	public boolean add(Instance instance)
	{
		boolean flag = getSetFor(instance).add(instance);
		if(flag) size++;
		return flag;
	}

	public boolean addAll(Collection<? extends Instance> instances)
	{
		boolean flag = false;
		for (Instance instance : instances)
		{
			flag = flag || add(instance);
		}
		return flag;
	}

	public void clear()
	{
		for (Set<?> set : sets.values())
			set.clear();
	}

	public boolean contains(Object arg0)
	{
		// this is OK-- pre-generics signature
		if(!(arg0 instanceof Instance))
		{
			return false;
		}
		Instance instance = (Instance) arg0;
		return getSetFor(instance).contains(instance);
	}

	public boolean containsAll(Collection<?> objects)
	{
		boolean flag = true;
		for(Object obj : objects)
		{
			flag = flag && contains(obj);
			if(!flag) break;
		}
		return flag;
	}

	public boolean isEmpty()
	{
		boolean flag = true;
		for(Set<?> set : sets.values())
		{
			flag = flag && set.isEmpty();
			if(!flag) break;
		}
		return flag;
	}

	public Iterator<Instance> iterator()
	{
		return new InstanceSetIterator();
	}

	public boolean remove(Object arg0)
	{
		// OK-- see note above (pre-Java5)
		if(!(arg0 instanceof Instance))
		{
			return false;
		}
		Instance instance = (Instance) arg0;
		return getSetFor(instance).remove(instance);
	}

	public boolean removeAll(Collection<?> arg0)
	{
		boolean flag = false;
		for(Object obj : arg0)
		{
			flag = flag || remove(obj);
		}
		return flag;
	}

	public boolean retainAll(Collection<?> collection)
	{
		boolean flag = false;
		for(Set<?> set : sets.values())
		{
			flag = flag || set.retainAll(collection);
		}
		return flag;
	}

	public int size()
	{
		return size;
	}

	public Object[] toArray()
	{
		Object rval[] = new Object[size];
		int idx=0;
		for(Instance instance : this)
		{
			rval[idx++] = instance;
		}
		return rval;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] arr)
	{
		Set<T> set = new HashSet<T>();
		for(Instance instance : this)
		{
			set.add((T) instance);
		}
		return set.toArray(arr);
	}

	private Set<Instance> getSet(InstanceSetIdentifier type)
	{
		return sets.get(type);
	}
	
	private Set<Instance> getSetFor(Instance instance)
	{
		return getSet(instance.getSetIdentifier());
	}
}
