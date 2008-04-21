package rutebaga.controller.command.list;

import rutebaga.controller.command.CloseContextMenuCommand;
import rutebaga.controller.command.Command;
import rutebaga.controller.command.CommandFactory;
import rutebaga.controller.command.CreateSubContextMenuCommand;
import rutebaga.model.Named;
import rutebaga.view.UserInterfaceFacade;

public class BackedListElementFactory<T extends Named> implements ListElementFactory<T> {

	private final CommandFactory<T> factory;
	private final UserInterfaceFacade facade;
	
	public BackedListElementFactory(CommandFactory<T> commands, UserInterfaceFacade facade) {
		this.factory = commands;
		this.facade = facade;
	}
	
	public ListElement makeElement(final T element) {
		return new ListElement() {
			public Command getCommand() {
				ConcreteElementalList list = new ConcreteElementalList();
				list.add("Close", new CloseContextMenuCommand(facade));
				list.add(factory.getCommandListFor(element));
				CreateSubContextMenuCommand command = new CreateSubContextMenuCommand(list);
				command.setUIFacade(facade);
				return command;
			}
			
			public String getLabel() {
				return element.getName();
			}
		};
	}
}
