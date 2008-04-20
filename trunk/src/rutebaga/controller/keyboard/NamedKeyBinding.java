package rutebaga.controller.keyboard;

import rutebaga.model.Named;

public interface NamedKeyBinding<C> extends KeyBinding<C>, Named {
	KeyCode getKeyCode();
	
	C getBinding();

	String getName();
}
