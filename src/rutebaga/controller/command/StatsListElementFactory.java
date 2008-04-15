package rutebaga.controller.command;

import rutebaga.model.entity.stats.StatValue;
 
public class StatsListElementFactory implements ListElementFactory<StatValue> {

	public ListElement makeElement(final StatValue stat) {
		return new ListElement() {
			public Command getCommand() {
				return null;
			}
			public String getLabel() {
				return "" + stat.getValue();
			}
		};
	}

}
