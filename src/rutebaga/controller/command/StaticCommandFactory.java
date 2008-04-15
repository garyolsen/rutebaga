package rutebaga.controller.command;

import rutebaga.controller.list.ElementalList;

public class StaticCommandFactory<T> implements CommandFactory<T> {
	
	private final ElementalList list;
	
	public StaticCommandFactory(ElementalList list) {
		this.list = list;
	}
	
	public ElementalList getCommandListFor(T element) {
		return list;
	}
}
