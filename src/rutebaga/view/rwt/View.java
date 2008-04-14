package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.util.HashSet;
import java.util.Set;

import rutebaga.view.drawer.*;

public class View {

	private Frame window;
	private EventDispatcher dispatcher;
	private Graphics2DDrawer drawer;
	
	private BufferStrategy strategy;
	
	private Set<ViewComponent> components = new HashSet<ViewComponent>();
	
	/**
	 * Constructs a view with the provided dimensions.
	 * @param width Horizontal size of the View.
	 * @param height Vertical size of the View.
	 */
	public View( int width, int height) {
		setupWindow( width, height );
		setupDispatcher();
		setupDrawer();
	}
	
	private void setupWindow( int width, int height) {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		GraphicsDevice device = env.getDefaultScreenDevice();
		window = new Frame(device.getDefaultConfiguration());
		window.setSize(width, height);
		window.setIgnoreRepaint(true);
		window.setUndecorated(true);
		
			
		
		window.setVisible(true);
		window.createBufferStrategy(2);
	}
	
	/**
	 * Instructs the View to enter fullscreen mode.
	 */
	public void setFullscreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		GraphicsDevice device = env.getDefaultScreenDevice();
		device.setFullScreenWindow(window);
	}
	
	private void setupDispatcher() {
		dispatcher = new EventDispatcher();
		
		window.addKeyListener(dispatcher);
		window.addMouseListener(dispatcher);
		window.addMouseMotionListener(dispatcher);
	}
	
	private void setupDrawer() {
		strategy = window.getBufferStrategy();
		drawer = new Graphics2DDrawer((Graphics2D) strategy.getDrawGraphics());
	}
	
	/**
	 * Refreshes the contents of the View.
	 */
	public void renderFrame() {
		if(!strategy.contentsLost()) {
			Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();
			
			g2d.setBackground(Color.BLACK);
			g2d.fillRect(0, 0, window.getBounds().width, window.getBounds().height);
			
			drawer.setGraphcis2D( g2d );
			
			drawViewComponents();
			
			strategy.show();
			drawer.getGraphics2D().dispose();
		} else {
			strategy = window.getBufferStrategy();
		}
	}
	
	/**
	 * Add a {@link ViewComponent} to this View.
	 * @param vc ViewComponent to add to this View.
	 */
	public void addViewComponent(ViewComponent vc ) {
		components.add(vc);
	}
	
	private void drawViewComponents( ) {
		for(ViewComponent vc : components)
			vc.draw( drawer );
	}
	
}
