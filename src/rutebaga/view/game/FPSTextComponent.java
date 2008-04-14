package rutebaga.view.game;

import rutebaga.view.drawer.Drawer;
import rutebaga.view.rwt.TextLabelComponent;

/**
 * Provides the current frame rate by counting the number of times it gets called each second.
 * @author Ryan
 *
 */
public class FPSTextComponent extends TextLabelComponent {

	private long lastTime;
	private int frames = 0;
	
	/**
	 * Constructs a new FPSTextComponent, intially showing 0 FPS.
	 */
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
