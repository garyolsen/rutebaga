package rutebaga.view.rwt;

import java.awt.Color;
import java.awt.Font;
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
	
	public View( int width, int height, boolean fullscreen ) {
		setupWindow( width, height, fullscreen );
		setupDispatcher();
		setupDrawer();
	}
	
	private void setupWindow( int width, int height, boolean fullscreen ) {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		GraphicsDevice device = env.getDefaultScreenDevice();
		window = new Frame(device.getDefaultConfiguration());
		window.setSize(width, height);
		window.setIgnoreRepaint(true);
		window.setUndecorated(true);
		
		if( fullscreen )
			device.setFullScreenWindow(window);
		
		window.createBufferStrategy(2);
		
		window.setVisible(true);
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
	
	public synchronized void renderFrame() {
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
	
	public void addViewComponent(ViewComponent vc ) {
		components.add(vc);
	}
	
	private void drawViewComponents( ) {
		for(ViewComponent vc : components)
			vc.draw( drawer );
	}
	
	public static void main(String args[]) {
		View view = new View( 800, 600, true);
		
		
		TextLabelComponent text = new TextLabelComponent("test");
		text.setLocation(100,100);
		text.setFontColor( Color.BLUE );
		text.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		view.addViewComponent(text);
		
		while(true)
			view.renderFrame();
	}
	
}
