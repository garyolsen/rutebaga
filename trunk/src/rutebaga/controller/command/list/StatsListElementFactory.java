package rutebaga.controller.command.list;

import rutebaga.controller.command.Command;
import rutebaga.model.entity.stats.StatValue;
 
public class StatsListElementFactory implements ListElementFactory<StatValue> {

	public ListElement makeElement(final StatValue stat) {
		return new ListElement() {
			public Command getCommand() {
				return null;
			}
			public String getLabel() {
				return stat.getId().getName() + ": " + (int) stat.getValue();
			}
		};
	}

}
