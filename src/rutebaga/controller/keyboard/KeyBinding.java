package rutebaga.controller.keyboard;

public interface KeyBinding<C> {
	KeyCode getKeyCode();
	
	C getBinding();
}
