package rutebaga.controller.command;

import rutebaga.controller.list.ElementalList;

public interface CommandFactory<T> {

	public ElementalList getCommandListFor(T element);
}
