package rutebaga.view.rwt;

import java.util.ArrayList;
import java.util.List;

import rutebaga.view.drawer.Drawer;

/**
 * Captures user keyboard input for display on screen. The captured information
 * can potentially be used somewhere else by whomever has attached a
 * {@link TextFieldListener} to the TextFieldComponent.
 * <p>
 * Responsibilities are to
 * <ul>
 * <li>
 * 
 * If they TextField has key focus, capture key inputs and display them within
 * the TextField.
 * <li>Whenever something is being typed into the TextField, notify its
 * listeners of the changes.
 * <li>Store the current state of the string within the TextField.
 * </ul>
 * 
 * 
 * @author Ryan
 * 
 */
public class TextFieldComponent extends ViewComponent
{

	public List<TextFieldListener> listeners = new ArrayList<TextFieldListener>();
	
	public void draw(Drawer draw)
	{
		// TODO Auto-generated method stub

	}
	
	public void addTextFieldListener(TextFieldListener listener) {
		listeners.add(listener);
	}

}
