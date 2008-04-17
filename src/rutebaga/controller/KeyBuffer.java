package rutebaga.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBuffer implements KeyListener {

	private static final int SUPPORTED_KEYS = 256;
	
	private static final boolean PRESSED = true;
	private static final boolean RELEASED = false;
	
	private final boolean[] keyStates;
	private final int[] keyPresses;
	private final KeyEvent[] events;
	private int keysQueued;
	private int keysPressed;
	
	public KeyBuffer() {
		keyPresses = new int[SUPPORTED_KEYS];
		keyStates = new boolean[SUPPORTED_KEYS];
		events = new KeyEvent[SUPPORTED_KEYS];
	}
	
	private boolean validKey(int keyCode) {
		return (keyCode >= 0 && keyCode < keyPresses.length);
	}
	
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (validKey(code)) {
			++keyPresses[code];
			if (keyStates[code] == RELEASED)
				++keysPressed;
			keyStates[code] = PRESSED;
			events[code] = e;
			++keysQueued;
		}
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (validKey(code)) {
			keyStates[code] = RELEASED;
			--keysPressed;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void poll(KeyListener k) {
		if (keysQueued > 0 || keysPressed > 0)
			for (int i = 0; i < keyPresses.length; ++i) {
				if (keyPresses[i] > 0) {
					--keyPresses[i];
					k.keyPressed(events[i]);
					if (keyPresses[i] == 0 && keyStates[i] == RELEASED)
						events[i] = null;
					--keysQueued;
				}
				else if (keyStates[i] == PRESSED)
					k.keyPressed(events[i]);
			}
	}
}
