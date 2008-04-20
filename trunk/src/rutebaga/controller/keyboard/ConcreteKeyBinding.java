package rutebaga.controller.keyboard;

public class ConcreteKeyBinding<C> implements KeyBinding<C> {

	private final KeyCode keyCode;
	private final C binding;
	
	public ConcreteKeyBinding(KeyCode code, C binding) {
		if (code == null)
			throw new NullPointerException();
			
		this.keyCode = code;
		this.binding = binding;
	}

	public C getBinding() {
		return binding;
	}

	public KeyCode getKeyCode() {
		return keyCode;
	}

}
