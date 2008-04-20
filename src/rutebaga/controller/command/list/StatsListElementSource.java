package rutebaga.controller.command.list;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import rutebaga.controller.command.LabelDeterminer;
import rutebaga.model.entity.stats.ConcreteStatValue;
import rutebaga.model.entity.stats.StatValue;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.entity.stats.Stats;

public class StatsListElementSource implements ListElementSource<StatValue> {
	
	private LabelDeterminer label;
	private Stats stats;
	private Map<StatisticId, Double> cache;
	
	public StatsListElementSource() {
		this.cache = new HashMap<StatisticId, Double>();
	}
	
	private void constructCache() {
		cache.clear();
		for (StatisticId id: stats.getStatIds())
			cache.put(id, stats.getValue(id));
	}
	
	public void setLabel(LabelDeterminer label) {
		this.label = label;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
	public int contentSize() {
		return stats.getStatIds().size();
	}

	public String getLabel() {
		return label == null ? "Stats" : label.getLabel();
	}
	
	public Iterator<StatValue> iterator() {
		constructCache();
		return new StatsIterator();
	}

	public boolean hasChanged(Object object) {
		if (cache.size() != stats.getStatIds().size())
			return true;
		for (StatisticId id: cache.keySet())
			if (cache.get(id) != stats.getValue(id))
				return true;
		return false;
	}
	
	private class StatsIterator implements Iterator<StatValue> {

		private Iterator<StatisticId> ids = stats.getStatIds().iterator(); 
		
		public boolean hasNext() {
			return ids.hasNext();
		}

		public StatValue next() {
			return stats.getStatObject(ids.next());
		}

		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	}
}
