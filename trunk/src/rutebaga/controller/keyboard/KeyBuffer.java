package rutebaga.controller.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBuffer implements KeyListener {

	private static final int NUM_SUPPORTED_KEY_CODES = 256;

	private static final boolean KEY_UP = false;
	private static final boolean KEY_DOWN = true;

	private final boolean[] states;
	private final KeyEvent[] events;

	public KeyBuffer() {
		// The size of states and events must be the same or puppies will die.
		states = new boolean[NUM_SUPPORTED_KEY_CODES];
		events = new KeyEvent[states.length];
	}

	/**
	 * Determines whether the given keyCode is valid (i.e., whether it is within
	 * the bounds of the states/events arrays or not). Because this method if
	 * private/final and has no local variables, the compiler is able to inline
	 * it, requiring no actual method invocation in users of this method.
	 * 
	 * @param keyCode
	 * @return
	 */
	private final boolean validKey(int keyCode) {
		return (keyCode >= 0 && keyCode < states.length);
	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (validKey(code)) {
			events[code] = e;
			states[code] = KEY_DOWN;
		}
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (validKey(code))
			states[code] = KEY_UP;
	}

	public void keyTyped(KeyEvent e) {
		/*
		 * We don't care about typed events--this is high level information that
		 * isn't useful to us.
		 */
	}

	public void poll(KeyListener k) {
		for (int code = 0; code < states.length; ++code) {
			/*
			 * If there is a cached key event OR the key is still pressed down,
			 * then send the key event to the listener.
			 */
			if (events[code] != null || states[code] == KEY_DOWN) {
				// Dispatch the cached event to the listener
				k.keyPressed(events[code]);
				// If the key is no longer held down, clear the cached event.
				if (states[code] == KEY_UP) {
					events[code] = null;
					k.keyReleased(events[code]);
				}
			}
		}
	}
}
