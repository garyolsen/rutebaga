package rutebaga.controller.command;


public class StaticCommandFactory<T> extends ConcreteElementalList implements CommandFactory<T> {
	public ElementalList getCommandListFor(T element) {
		return this;
	}
}
