package rutebaga.view.rwt;


import java.awt.Color;
import java.awt.Font;

import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.CompositeAttribute;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.drawer.FontAttribute;

public class TextLabelComponent extends ViewComponent {
	
	private String label;
	private FontAttribute font;
	private ColorAttribute color;
	private CompositeAttribute composite;
	
	private TextLabelComponent() {
		font = new FontAttribute(new Font("Arial", Font.PLAIN, 10));
		color = new ColorAttribute( Color.BLACK );
		composite = new CompositeAttribute();
		composite.addAttribute( font );
		composite.addAttribute( color );
	}

	public TextLabelComponent( String label ) {
		this();
		this.label = label;
	}
	
	public void draw( Drawer draw ) {
		draw.setAttribute( composite );
		draw.drawString(this.getLocation(), label);
	}
	
	public void setFont( Font font ) {
		this.font.setFont( font );
	}
	
	public void setFontColor( Color color ) {
		this.color.setColor( color );
	}
	
	public void setLabel( String label ) {
		this.label = label;
	}

}
