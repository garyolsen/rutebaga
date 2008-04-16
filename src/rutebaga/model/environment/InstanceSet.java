package rutebaga.model.environment;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;
import rutebaga.model.map.Tile;

/**
 * Convenience wrapper.
 * 
 * @author Gary LosHuertos
 * 
 */
public class InstanceSet implements Set<Instance>
{
	public static enum InstanceSetIdentifier
	{
		ENTITY(new HashSet<Entity>()),
		ITEM(new HashSet<Item>()),
		EFFECT(new HashSet<Instance>()),
		TILE(new HashSet<Tile>());

		private final HashSet<? extends Instance> prototype;

		private InstanceSetIdentifier(HashSet<? extends Instance> prototype)
		{
			this.prototype = prototype;
		}

		HashSet<? extends Instance> getPrototype()
		{
			return prototype;
		}
	}

	protected class InstanceSetIterator implements Iterator<Instance>
	{
		Iterator<InstanceSetIdentifier> typeIterator = sets.keySet().iterator();
		Iterator<? extends Instance> currentIterator;

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
			if (!currentIterator.hasNext())
				advanceInstanceIterator();
			return rval;
		}

		public void remove()
		{
			throw new UnsupportedOperationException("You tried to do WHAT?");
		}

		private void advanceInstanceIterator()
		{
			if (typeIterator.hasNext())
			{
				currentIterator = sets.get(typeIterator.next()).iterator();
				if (!currentIterator.hasNext())
					advanceInstanceIterator();
			}
		}

	}

	private static final long serialVersionUID = 358791980720848943L;
	
	private Map<InstanceSetIdentifier, HashSet<? extends Instance>> sets = new HashMap<InstanceSetIdentifier, HashSet<? extends Instance>>();
	
	int size = 0;
	
	@SuppressWarnings("unchecked")
	public InstanceSet()
	{
		for (InstanceSetIdentifier type : InstanceSetIdentifier.values())
		{
			sets.put(type, (HashSet<? extends Instance>) type.getPrototype().clone());
		}
	}
	
	

	public boolean add(Instance instance)
	{
		boolean flag = getSetFor(instance).add(instance);
		if (flag)
			size++;
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
		if (!(arg0 instanceof Instance))
		{
			return false;
		}
		Instance instance = (Instance) arg0;
		return getSetFor(instance).contains(instance);
	}

	public boolean containsAll(Collection<?> objects)
	{
		boolean flag = true;
		for (Object obj : objects)
		{
			flag = flag && contains(obj);
			if (!flag)
				break;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public Set<Instance> getEffects()
	{
		return (Set<Instance>) sets.get(InstanceSetIdentifier.EFFECT);
	}

	@SuppressWarnings("unchecked")
	public Set<Entity> getEntities()
	{
		return (Set<Entity>) sets.get(InstanceSetIdentifier.ENTITY);
	}

	@SuppressWarnings("unchecked")
	public Set<Item> getItems()
	{
		return (Set<Item>) sets.get(InstanceSetIdentifier.ITEM);
	}

	@SuppressWarnings("unchecked")
	public Set<Tile> getTiles()
	{
		return (Set<Tile>) sets.get(InstanceSetIdentifier.TILE);
	}

	public boolean isEmpty()
	{
		boolean flag = true;
		for (Set<?> set : sets.values())
		{
			flag = flag && set.isEmpty();
			if (!flag)
				break;
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
		if (!(arg0 instanceof Instance))
		{
			return false;
		}
		Instance instance = (Instance) arg0;
		return getSetFor(instance).remove(instance);
	}

	public boolean removeAll(Collection<?> arg0)
	{
		boolean flag = false;
		for (Object obj : arg0)
		{
			flag = flag || remove(obj);
		}
		return flag;
	}

	public boolean retainAll(Collection<?> collection)
	{
		boolean flag = false;
		for (Set<?> set : sets.values())
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
		int idx = 0;
		for (Instance instance : this)
		{
			rval[idx++] = instance;
		}
		return rval;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] arr)
	{
		Set<T> set = new HashSet<T>();
		for (Instance instance : this)
		{
			set.add((T) instance);
		}
		return set.toArray(arr);
	}

	private HashSet<? extends Instance> getSet(InstanceSetIdentifier type)
	{
		return sets.get(type);
	}

	@SuppressWarnings("unchecked")
	private <T extends Instance> HashSet<? super T> getSetFor(T instance)
	{
		return (HashSet<? super T>) getSet(instance.getSetIdentifier());
	}
}
