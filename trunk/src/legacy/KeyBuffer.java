package legacy;

import java.awt.event.*;

public class KeyBuffer implements KeyListener {
	private KeyState[] keyStates;
	private boolean[] keyEvents;
	private Integer lastKey;
	
	public KeyBuffer(){
		keyStates = new KeyState[256];
		keyEvents = new boolean[256];
	}
	
	public KeyBuffer(int size){
		keyStates = new KeyState[size];
		keyEvents = new boolean[size];
	}
	
	private boolean verifyCodeRange(int keyCode)
	{
		return (keyCode >= 0 && keyCode < keyStates.length);
	}
	
	public void keyPressed(KeyEvent event){
		int code = event.getKeyCode();
		if(verifyCodeRange(code))		
			keyEvents[code] = true;
		
		lastKey = new Integer(code);
	}
	
	public void keyReleased(KeyEvent event){
		int code = event.getKeyCode();
		if(verifyCodeRange(code))
			keyEvents[code] = false;
	}
	
	public void keyTyped(KeyEvent event){
		// Do nothing
	}
	
	public void poll()
	{
		for(int i = 0; i < keyStates.length; i++){
			if(keyEvents[i]){
				if(keyStates[i] == KeyState.Released)
					keyStates[i] = KeyState.PressedOnce;
				else
					keyStates[i] = KeyState.Pressed;
			}else
				keyStates[i] = KeyState.Released;
		}
		
	}

	public boolean checkState(int keyCode, KeyState state){
		switch (state){
		case	Pressed:
			return (keyStates[keyCode] == KeyState.Pressed || keyStates[keyCode] == KeyState.PressedOnce);
		case	PressedOnce:
			return (keyStates[keyCode] == KeyState.PressedOnce);
		case	Released:
			return (keyStates[keyCode] == KeyState.Released);
		default:
			return false;
		}
	}
	
	public boolean isPressed(int keyCode){
		return (keyStates[keyCode] == KeyState.Pressed || keyStates[keyCode] == KeyState.PressedOnce);
	}
	
	public boolean isPressedOnce(int keyCode){
		return (keyStates[keyCode] == KeyState.PressedOnce);
	}
	
	public boolean isReleased(int keyCode){
		return (keyStates[keyCode] == KeyState.Released);		
	}
	
	public Integer getLastKey(){
		return lastKey;
	}
	
	public void clearLastKey(){
		lastKey = null;
	}
}
