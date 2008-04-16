package rutebaga.model.environment;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;
import rutebaga.model.map.Tile;

public interface InstanceSet extends Set<Instance>
{

	boolean add(Instance instance);

	boolean addAll(Collection<? extends Instance> instances);

	void clear();

	boolean contains(Object arg0);

	boolean containsAll(Collection<?> objects);

	@SuppressWarnings("unchecked")
	Set<Instance> getEffects();

	@SuppressWarnings("unchecked")
	Set<Entity> getEntities();

	@SuppressWarnings("unchecked")
	Set<Item> getItems();

	@SuppressWarnings("unchecked")
	Set<Tile> getTiles();

	boolean isEmpty();

	Iterator<Instance> iterator();

	boolean remove(Object arg0);

	boolean removeAll(Collection<?> arg0);

	boolean retainAll(Collection<?> collection);

	int size();

	Object[] toArray();

	@SuppressWarnings("unchecked")
	<T> T[] toArray(T[] arr);
	
	Map<InstanceSetIdentifier, ? extends Set<? extends Instance>> getSets();

}