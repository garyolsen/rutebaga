package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;

import rutebaga.controller.command.Command;
import rutebaga.view.drawer.*;

public class ButtonComponent extends ViewComponent {
	
	private String label;
	private boolean toggle = false;
	private boolean hover = false;
	
	private Command command;
	
	private CompositeAttribute untoggled, toggled, text, shadow, normalText, normalShadow, highlightedText, highlightedShadow;
	
	private ColorAttribute untoggledColor = new ColorAttribute(Color.GRAY);
	private ColorAttribute toggledColor = new ColorAttribute(Color.DARK_GRAY);
	private ColorAttribute hoverColor = new ColorAttribute(Color.LIGHT_GRAY);
	
	private FontAttribute font;
	private FontAttribute normalFont;
	private FontAttribute highlightedFont;

	public ButtonComponent() {
		this("");

	}
	
	public ButtonComponent(String label) {
		this.label = label;
		
		
		font = normalFont = new FontAttribute( new Font("Arial", Font.BOLD, 12));
		highlightedFont = new FontAttribute( new Font("Arial", Font.BOLD, 18));
		
		untoggled = new CompositeAttribute();
		untoggled.addAttribute( untoggledColor );
		
		toggled = new CompositeAttribute();
		toggled.addAttribute( toggledColor );
		
		text = normalText = new CompositeAttribute();
		text.addAttribute( font );
		text.addAttribute( new ColorAttribute( Color.WHITE ) );
		
		shadow = normalShadow = new CompositeAttribute();
		shadow.addAttribute( font );
		shadow.addAttribute( new ColorAttribute( Color.BLACK ) );
	
		highlightedText = new CompositeAttribute();
		highlightedText.addAttribute( highlightedFont );
		highlightedText.addAttribute( new ColorAttribute( Color.WHITE ) );
		
		highlightedShadow = new CompositeAttribute();
		highlightedShadow.addAttribute( highlightedFont );
		highlightedShadow.addAttribute( new ColorAttribute( Color.BLACK ) );
	
		
		this.setBounds( 0, 0, 100, 40 );
		
	}
	
	public void draw(Drawer draw) {
		if(toggle)
			drawToggled( draw );
		else
			drawUntoggled( draw );
	}
	
	private void drawUntoggled( Drawer draw ) {
		
		Point centeredText = new Point();
		FontMetrics fm = draw.getFontMetrics();
		
		draw.setAttribute( hover ? hoverColor : untoggled );
		draw.drawShape(getLocation(), getBounds());
		
		centeredText.x = (int)getBounds().getBounds().getX() + this.getLocation().x + (getWidth() / 2) - ( fm.stringWidth(label) / 2 );
		centeredText.y = (int)getBounds().getBounds().getY() + this.getLocation().y + (getHeight() / 2) + ( font.getFont().getSize() / 2 );
		
		draw.setAttribute( shadow );
		draw.drawString( centeredText , label);

		--centeredText.x;
		--centeredText.y;
		
		draw.setAttribute( text );
		draw.drawString( centeredText , label);
		
		
		
	}
	
	private void drawToggled( Drawer draw ) {
		draw.setAttribute( hover ? hoverColor : toggled );
		draw.drawShape(getLocation(), getBounds());
		
		Point centeredText = new Point();
		FontMetrics fm = draw.getFontMetrics();
	
		centeredText.x = (int)getBounds().getBounds().getX() + this.getLocation().x + (getWidth() / 2) - ( fm.stringWidth(label) / 2 );
		centeredText.y = (int)getBounds().getBounds().getY() + this.getLocation().y + (getHeight() / 2) + ( font.getFont().getSize() / 2 );
		
		draw.setAttribute(shadow);
		draw.drawString( centeredText , label);

		--centeredText.x;
		--centeredText.y;

		draw.setAttribute(text);
		draw.drawString( centeredText , label);
	}
	
	protected boolean processMouseEvent( MouseEvent event ) {
		if(command != null ) {
			if(event.getID() == MouseEvent.MOUSE_PRESSED) {
				toggle = true;
			} else if(event.getID() == MouseEvent.MOUSE_RELEASED) {
				toggle = false;
			} else if(event.getID() == MouseEvent.MOUSE_CLICKED) {
				if(command != null && command.isFeasible())
					command.execute();
			}
		}
		
		return true;
	}

	@Override
	protected boolean processMouseMotionEvent(MouseEvent event) {
		if (event.getID() == MouseEvent.MOUSE_ENTERED)
			hover = true;
		else if (event.getID() == MouseEvent.MOUSE_EXITED)
			hover = false;
		else
			return super.processMouseMotionEvent(event);
		return true;
	}
	
	public void setLabel(String label ) {
		this.label = label;
	}
	
	public void setCommand( Command command )  {
		this.command = command;
	}
	
	public void setButtonShape( Shape shape ) {
		this.setBounds(shape);
	}
	
	public void setUntoggledColor( Color color ) {
		untoggledColor.setColor(color);
	}
	
	public void setToggledColor( Color color ) {
		toggledColor.setColor(color);
	}

	public void setHighlighted(boolean highlight) {
		if (highlight) {
			font = highlightedFont;
			text = highlightedText;
			shadow = highlightedShadow;
		}
		else {
			font = normalFont;
			text = normalText;
			shadow = normalShadow;
		}
	}
	
	public boolean isHighlighted() {
		return font == highlightedFont;
	}
}
