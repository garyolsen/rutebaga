package rutebaga.controller.keyboard;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a POD.
 * 
 * @author Matty
 */
public class KeyCode {
	private static final int INITIAL_KEYCODES = 256;
	private static KeyCode[] codes = new KeyCode[INITIAL_KEYCODES];
	
	private static void ensureCapacity(int size) {
		if (size > codes.length) {
			KeyCode[] newCodes = new KeyCode[size];
			System.arraycopy(codes, 0, newCodes, 0, codes.length);
			codes = newCodes;
		}
	}
	
	public static KeyCode get(int keyCode) {
		ensureCapacity(keyCode);
		KeyCode code = codes[keyCode];
		if (code == null) {
			code = new KeyCode(keyCode);
			codes[keyCode] = code;
		}
		return code;
	}
	
	public static KeyCode get(KeyEvent keyEvent) {
		return get(keyEvent.getKeyCode());
	}

	private final int keyCode;

	private KeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}
	
	public String getKeyName() {
		return KeyEvent.getKeyText(keyCode);
	}
}
