package rutebaga.view.rwt;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;

import rutebaga.view.drawer.*;

public class TextLabelComponent extends ViewComponent {
	
	private enum TextAlignment {
		LEFT_ALIGNMENT,
		CENTER_ALIGNMENT,
		RIGHT_ALIGNMENT
	}
	
	public static final TextAlignment LEFT_ALIGNMENT = TextAlignment.LEFT_ALIGNMENT;
	public static final TextAlignment CENTER_ALIGNMENT = TextAlignment.CENTER_ALIGNMENT;
	public static final TextAlignment RIGHT_ALIGNMENT = TextAlignment.RIGHT_ALIGNMENT;
	
	private String label = "";
	private FontAttribute font;
	private ColorAttribute color;
	private CompositeAttribute composite;
	private TextAlignment alignment;

	public TextLabelComponent(String label) {
		this(label, null);
	}
	
	public TextLabelComponent( String label, TextAlignment align ) {
		if(label != null)
			this.label = label;
		
		alignment = align == null ? LEFT_ALIGNMENT : align;
		
		font = new FontAttribute(new Font("Arial", Font.PLAIN, 10));
		color = new ColorAttribute( Color.BLACK );
		composite = new CompositeAttribute();
		composite.addAttribute( font );
		composite.addAttribute( color );
		
		this.setBounds(new Rectangle());
	}
	
	public void draw( Drawer draw ) {
		
		draw.setAttribute( composite );
		FontMetrics metrics = draw.getFontMetrics();
		
		Point p = new Point();
		p.y = getY() + getHeight() / 2 - metrics.getMaxDescent() / 2 + metrics.getMaxAscent() / 2;
		switch (alignment) {
		case LEFT_ALIGNMENT:
			p.x = getX();
			break;
		case RIGHT_ALIGNMENT:
			p.x = getX() + getWidth() - metrics.stringWidth(label) / 2;
			break;
		case CENTER_ALIGNMENT:
			p.x = getX() + getWidth() / 2 - metrics.stringWidth(label) / 2;
			break;
		}
		
		this.setBounds(getX(), getY(), metrics.stringWidth(label), getHeight());
		
		draw.drawString(p, label);
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
