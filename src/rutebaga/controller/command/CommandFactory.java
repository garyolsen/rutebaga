package rutebaga.controller.command;

import rutebaga.controller.command.list.ElementalList;


public interface CommandFactory<T> {

	public ElementalList getCommandListFor(T element);
}
