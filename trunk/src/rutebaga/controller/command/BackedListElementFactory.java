package rutebaga.controller.command;

import rutebaga.model.Named;

public class BackedListElementFactory<T extends Named> implements ListElementFactory<T> {

	private final CommandFactory<T> factory;
	
	public BackedListElementFactory(CommandFactory<T> commands) {
		factory = commands;
	}
	
	public ListElement makeElement(final T element) {
		// TODO: cache old ListElements here?  Or maybe in the DynamicElementalList itself...
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
