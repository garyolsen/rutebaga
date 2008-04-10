package rutebaga.view.drawer;

import java.awt.Font;

public class FontAttribute implements Attribute{
	
	private Font font;

	public FontAttribute() {
		
	}
	
	public FontAttribute( Font font ) {
		this.font = font;
	}
	
	public void apply(Drawer drawer) {
		drawer.setFont( font );
	}
	
	public void setFont( Font font ) {
		this.font = font;
	}
	
	public Font getFont() {
		return font;
	}

}
