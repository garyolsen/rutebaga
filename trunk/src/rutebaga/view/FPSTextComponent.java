package rutebaga.view;

import rutebaga.view.drawer.Drawer;
import rutebaga.view.rwt.TextLabelComponent;

public class FPSTextComponent extends TextLabelComponent {

	private long lastTime;
	private int frames = 0;
	
	public FPSTextComponent() {
		super("0");
		
		lastTime = System.currentTimeMillis();
	}
	
	public void draw( Drawer draw ) {
		long now = System.currentTimeMillis();
		if(now - lastTime < 1000) {
			frames++;
		} else {
			this.setLabel( "" + frames );
			frames = 0;
			lastTime += 1000;
		}
		
		super.draw( draw );
	}

}
