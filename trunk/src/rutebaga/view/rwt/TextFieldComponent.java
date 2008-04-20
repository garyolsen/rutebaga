package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import rutebaga.view.drawer.ClippingAttribute;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.drawer.FontAttribute;

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

	private StringBuffer contents = new StringBuffer();
	
	private ColorAttribute interior = new ColorAttribute(Color.WHITE);
	private ColorAttribute exterior = new ColorAttribute(Color.BLACK);
	private ColorAttribute textColor = new ColorAttribute(Color.BLACK);
	private FontAttribute font = new FontAttribute(new Font("Arial", Font.PLAIN, 10));
	private ClippingAttribute clip = new ClippingAttribute(new Rectangle());
	
	private int cursorDuration = 500; //in milliseconds
	private long lastTick;
	private boolean cursorOn = true;
	
	public List<TextFieldListener> listeners = new ArrayList<TextFieldListener>();
	
	public TextFieldComponent() {
		this.setBounds(new Rectangle(100,20));
	}
	
	public void draw(Drawer draw)
	{
		Rectangle rect = getBounds().getBounds();
		rect.x = getX();
		rect.y = getY();
		
		clip.setClipping(rect);
		draw.setAttribute(clip);
		draw.setAttribute(exterior);
		draw.drawRectangle(getLocation(), getWidth(), getHeight());
		
		draw.setAttribute(interior);
		Point p = getLocation();
		p.x += 2;
		p.y += 2;
		draw.drawRectangle(p, getWidth()-4, getHeight()-4);
		
		draw.setAttribute(textColor);
		draw.setAttribute(font);
		p.x += 2;
		p.y += font.getFont().getSize();
		draw.drawString(p, contents.toString());
		
		if(cursorOn) {
			int width = draw.getFontMetrics().stringWidth(contents.toString() );
			
			p.x += width;
			p.y += 2;
			
			Point end = new Point(p.x, p.y);
			p.y -= font.getFont().getSize();
			
			draw.drawLine(p, end);
			
			if(lastTick == 0)
				lastTick = System.currentTimeMillis();
		} else {
			if(lastTick == 0)
				lastTick = System.currentTimeMillis();
		}
		
		if(System.currentTimeMillis() - lastTick > cursorDuration ) {
			cursorOn = !cursorOn;
			lastTick = 0;
		}
		
		clip.setClipping(null);
		draw.setAttribute(clip);
	}
	
	public void addTextFieldListener(TextFieldListener listener) {
		listeners.add(listener);
	}
	
	protected boolean processKeyEvent( KeyEvent e ) {
		
		if(e.getID() == KeyEvent.KEY_TYPED) {
			if(Character.isLetterOrDigit(e.getKeyChar())) {
				contents.append(e.getKeyChar());
			}
		} else if(e.getID() == KeyEvent.KEY_PRESSED) {
			if(e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if(contents.length() > 0)
					contents = contents.deleteCharAt(contents.length() - 1);
			} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				contents.append(" ");
			}
		}
		
		updateListeners();
		return true;
	}
	
	private void updateListeners() {
		for(TextFieldListener listener : listeners)
			listener.fieldChanged(contents.toString());
	}

}
