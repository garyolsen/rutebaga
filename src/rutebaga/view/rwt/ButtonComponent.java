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
	
	private Command command;
	
	private CompositeAttribute untoggled, toggled, text;
	
	private ColorAttribute untoggledColor = new ColorAttribute(Color.GRAY);
	private ColorAttribute toggledColor = new ColorAttribute(Color.DARK_GRAY);
	
	private FontAttribute font;

	public ButtonComponent() {
		this("");

	}
	
	public ButtonComponent(String label) {
		this.label = label;
		
		
		font = new FontAttribute( new Font("Arial", Font.PLAIN, 10));
		
		untoggled = new CompositeAttribute();
		untoggled.addAttribute( untoggledColor );
		
		toggled = new CompositeAttribute();
		toggled.addAttribute( toggledColor );
		
		text = new CompositeAttribute();
		text.addAttribute( font );
		text.addAttribute( new ColorAttribute( Color.BLACK ) );
		
		
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
		
		draw.setAttribute( untoggled );
		draw.drawShape(getLocation(), getBounds());
		
	
		centeredText.x = (int)getBounds().getBounds().getX() + this.getLocation().x + (getWidth() / 2) - ( fm.stringWidth(label) / 2 );
		centeredText.y = (int)getBounds().getBounds().getY() + this.getLocation().y + (getHeight() / 2) + ( font.getFont().getSize() / 2 );
		
		draw.setAttribute( text );
		draw.drawString( centeredText , label);
		
	}
	
	private void drawToggled( Drawer draw ) {
		draw.setAttribute( toggled );
		draw.drawShape(getLocation(), getBounds());
		
		Point centeredText = new Point();
		FontMetrics fm = draw.getFontMetrics();
	
		centeredText.x = (int)getBounds().getBounds().getX() + this.getLocation().x + (getWidth() / 2) - ( fm.stringWidth(label) / 2 );
		centeredText.y = (int)getBounds().getBounds().getY() + this.getLocation().y + (getHeight() / 2) + ( font.getFont().getSize() / 2 );
		
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

}
