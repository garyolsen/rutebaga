package rutebaga.controller.keyboard;

public class ConcreteKeyBinding<C> implements NamedKeyBinding<C> {

	private final KeyCode keyCode;
	private final C binding;
	private final String name;
	
	public ConcreteKeyBinding(KeyCode code, C binding) {
		this("", code, binding);
	}

	public ConcreteKeyBinding(String name, KeyCode code, C binding) {
		if (code == null)
			throw new NullPointerException();
			
		this.keyCode = code;
		this.binding = binding;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public C getBinding() {
		return binding;
	}

	public KeyCode getKeyCode() {
		return keyCode;
	}
}
