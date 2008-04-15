package rutebaga.controller.command;

import java.util.Iterator;

import rutebaga.model.entity.stats.StatValue;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.entity.stats.Stats;

public class StatsListElementSource implements ListElementSource<StatValue> {
	
	private LabelDeterminer label;
	private Stats stats;

	public void setLabel(LabelDeterminer label) {
		this.label = label;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
	public String getLabel() {
		return label == null ? "Stats" : label.getLabel();
	}
	
	public Iterator<StatValue> iterator() {
		return new StatsIterator();
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
