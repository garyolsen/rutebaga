package rutebaga.view.rwt;


import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import rutebaga.view.drawer.*;

public class TextLabelComponent extends ViewComponent {
	
	private String label = "";
	private FontAttribute font;
	private ColorAttribute color;
	private CompositeAttribute composite;

	public TextLabelComponent( String label ) {
		if(label != null)
			this.label = label;
		
		font = new FontAttribute(new Font("Arial", Font.PLAIN, 10));
		color = new ColorAttribute( Color.BLACK );
		composite = new CompositeAttribute();
		composite.addAttribute( font );
		composite.addAttribute( color );
		
		this.setBounds(new Rectangle());
	}
	
	public void draw( Drawer draw ) {
		draw.setAttribute( composite );
		
		Point p = getLocation();
		p.y += font.getFont().getSize();
		draw.drawString(p, label);
		
		this.setBounds(getX(), getY(), draw.getFontMetrics().stringWidth(label), font.getFont().getSize());
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
