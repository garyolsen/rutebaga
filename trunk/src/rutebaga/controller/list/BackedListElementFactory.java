package rutebaga.controller.list;

import rutebaga.controller.command.Command;
import rutebaga.controller.command.CreateSubContextMenuCommand;
import rutebaga.controller.command.CommandFactory;
import rutebaga.model.Named;

public class BackedListElementFactory<T extends Named> implements ListElementFactory<T> {

	private final CommandFactory<T> factory;
	
	public BackedListElementFactory(CommandFactory<T> commands) {
		factory = commands;
	}
	
	public ListElement makeElement(final T element) {
		// TODO: cache old ListElements here
		return new ListElement() {
			public Command getCommand() {
				return new CreateSubContextMenuCommand(factory.getCommandListFor(element));
			}
			
			public String getLabel() {
				return element.getName();
			}
		};
	}
}
