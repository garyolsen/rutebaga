package rutebaga.controller.command;


public interface CommandFactory<T> {

	public ElementalList getCommandListFor(T element);
}
