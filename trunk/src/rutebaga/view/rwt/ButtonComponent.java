package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import rutebaga.view.drawer.*;

public class ButtonComponent extends ViewComponent {
	
	private String label;
	private boolean toggle = false;
	
	private CompositeAttribute untoggled, toggled;
	private FontAttribute font ;

	public ButtonComponent() {
		font = new FontAttribute( new Font("Arial", Font.PLAIN, 10));
		
		untoggled = new CompositeAttribute();
		untoggled.addAttribute( font );
		untoggled.addAttribute( new ColorAttribute( Color.LIGHT_GRAY ) );
		
		toggled = new CompositeAttribute();
		toggled.addAttribute( font );
		toggled.addAttribute( new ColorAttribute( Color.DARK_GRAY ) );
	}
	
	public ButtonComponent(String label) {
		this();
		this.label = label;
	}
	
	public void draw(Drawer draw) {
		if(toggle)
			drawToggled( draw );
		else
			drawUntoggled( draw );
	}
	
	private void drawUntoggled( Drawer draw ) {
		draw.setAttribute( untoggled );
		//draw.drawRectangle(getLocation(), this.getBounds().getWidth(), this.getBounds().getHeight());
		
		Point centeredText = new Point();
		FontMetrics fm = draw.getFontMetrics();
	
		//centeredText.x = this.getLocation() + (this.getBounds().getWidth() / 2) - ( fm.stringWidth(label) / 2 );
		//centeredText.x = this.getLocation() + (this.getBounds().getWidth() / 2) + ( font.getFont().getSize() / 2 );
		
		draw.drawString( centeredText , label);
	}
	
	private void drawToggled( Drawer draw ) {
		
	}
	
	protected void processMouseEvent( MouseEvent event ) {
		
	}

}
