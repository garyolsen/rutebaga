package rutebaga.controller.command.list;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import rutebaga.controller.command.LabelDeterminer;

public class CollectionListElementSource<E> implements ListElementSource<E> {

	private LabelDeterminer label;
	private Collection<E> source;
	private Set<E> cache;
	
	public CollectionListElementSource(LabelDeterminer label, Collection<E> source) {
		this.label = label;
		this.source = source;
		this.cache = new HashSet<E>();
	}
	
	public String getLabel() {
		return label.getLabel();
	}

	public Iterator<E> iterator() {
		return source.iterator();
	}

	public int contentSize() {
		return source.size();
	}

	public boolean hasChanged(Object object) {	
		if (cache.size() != contentSize())
			return true; // Source changed since cache was formed
		
		do {
			int cacheSize = cache.size();
			cache.retainAll(source);
		
			if (cacheSize != cache.size())
				break; // Source lost objects since cache was formed
			if (cache.size() != source.size())
				break; // Source gained objects since cache was formed
		
			cache.clear();
			cache.addAll(source);
		
			return false;
		} while (false);
		
		return true;
	}
}
