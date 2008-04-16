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
public class ConcreteInstanceSet implements InstanceSet
{
	protected class InstanceSetIterator implements Iterator<Instance>
	{
		Iterator<InstanceSetIdentifier> typeIterator = acceptedTypes.iterator();
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

	private final Map<InstanceSetIdentifier, ? extends Set<? extends Instance>> sets;
	private Set<InstanceSetIdentifier> acceptedTypes;

	@SuppressWarnings("unchecked")
	public ConcreteInstanceSet()
	{
		Map<InstanceSetIdentifier, HashSet<? extends Instance>> map = new HashMap<InstanceSetIdentifier, HashSet<? extends Instance>>();
		acceptedTypes = new HashSet<InstanceSetIdentifier>();
		for (InstanceSetIdentifier type : InstanceSetIdentifier.values())
		{
			map.put(type, (HashSet<? extends Instance>) type.getPrototype()
					.clone());
			acceptedTypes.add(type);
		}
		sets = map;
	}

	public ConcreteInstanceSet(
			Map<InstanceSetIdentifier, ? extends Set<? extends Instance>> backingMap, Set<InstanceSetIdentifier> ids)
	{
		this.sets = backingMap;
		this.acceptedTypes = ids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#add(rutebaga.model.environment.Instance)
	 */
	public boolean add(Instance instance)
	{
		if(!check(instance)) return false;
		boolean flag = getSetFor(instance).add(instance);
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends Instance> instances)
	{
		boolean flag = false;
		for (Instance instance : instances)
		{
			flag = flag || add(instance);
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#clear()
	 */
	public void clear()
	{
		for (Set<?> set : sets.values())
			set.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#contains(java.lang.Object)
	 */
	public boolean contains(Object arg0)
	{
		if(!check(arg0)) return false;
		Instance instance = (Instance) arg0;
		return getSetFor(instance).contains(instance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#containsAll(java.util.Collection)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#getEffects()
	 */
	@SuppressWarnings("unchecked")
	public Set<Instance> getEffects()
	{
		return (Set<Instance>) sets.get(InstanceSetIdentifier.EFFECT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#getEntities()
	 */
	@SuppressWarnings("unchecked")
	public Set<Entity> getEntities()
	{
		return (Set<Entity>) sets.get(InstanceSetIdentifier.ENTITY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#getItems()
	 */
	@SuppressWarnings("unchecked")
	public Set<Item> getItems()
	{
		return (Set<Item>) sets.get(InstanceSetIdentifier.ITEM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#getTiles()
	 */
	@SuppressWarnings("unchecked")
	public Set<Tile> getTiles()
	{
		return (Set<Tile>) sets.get(InstanceSetIdentifier.TILE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#isEmpty()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#iterator()
	 */
	public Iterator<Instance> iterator()
	{
		return new InstanceSetIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#remove(java.lang.Object)
	 */
	public boolean remove(Object arg0)
	{
		if(!check(arg0)) return false;
		Instance instance = (Instance) arg0;
		return getSetFor(instance).remove(instance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> arg0)
	{
		boolean flag = false;
		for (Object obj : arg0)
		{
			flag = flag || remove(obj);
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> collection)
	{
		boolean flag = false;
		for (Set<?> set : sets.values())
		{
			flag = flag || set.retainAll(collection);
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#size()
	 */
	public int size()
	{
		int size = 0;
		for(InstanceSetIdentifier id : this.acceptedTypes)
			size += sets.get(id).size();
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#toArray()
	 */
	public Object[] toArray()
	{
		Object rval[] = new Object[size()];
		int idx = 0;
		for (Instance instance : this)
		{
			rval[idx++] = instance;
		}
		return rval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rutebaga.model.environment.InstanceSet#toArray(T[])
	 */
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

	private Set<? extends Instance> getSet(InstanceSetIdentifier type)
	{
		return sets.get(type);
	}

	@SuppressWarnings("unchecked")
	private <T extends Instance> HashSet<? super T> getSetFor(T instance)
	{
		return (HashSet<? super T>) getSet(instance.getSetIdentifier());
	}

	public Map<InstanceSetIdentifier, ? extends Set<? extends Instance>> getSets()
	{
		return sets;
	}

	private boolean check(Object object)
	{
		if (!(object instanceof Instance))
			return false;
		Instance instance = (Instance) object;
		return acceptedTypes.contains(instance.getSetIdentifier());
	}
}
