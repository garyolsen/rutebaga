package rutebaga.view.rwt;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.Transparency;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rutebaga.view.drawer.Graphics2DDrawer;

public class View
{

	private Frame window;
	private EventDispatcher dispatcher;
	private Graphics2DDrawer drawer;

	private BufferStrategy strategy;

	private List<ViewComponent> components = new CopyOnWriteArrayList<ViewComponent>();

	private static ImageCapabilities accel = new ImageCapabilities(true);

	/**
	 * Constructs a view with the provided dimensions.
	 * 
	 * @param width
	 *            Horizontal size of the View.
	 * @param height
	 *            Vertical size of the View.
	 */
	public View(int width, int height)
	{
		setupWindow(width, height);
		setupDispatcher();
		setupDrawer();
	}

	private void setupWindow(int width, int height)
	{
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();

		GraphicsDevice device = env.getDefaultScreenDevice();
		window = new Frame(device.getDefaultConfiguration());
		window.setSize(width, height);
		window.setIgnoreRepaint(true);
		window.setUndecorated(true);

		window.setVisible(true);
		window.createBufferStrategy(2);

		setFullscreen();

		DisplayMode old = device.getDisplayMode();
		DisplayMode[] modes = device.getDisplayModes();
		List<DisplayMode> rectModes = new LinkedList<DisplayMode>();
		for (DisplayMode mode : modes)
		{
			int w = mode.getWidth();
			int h = mode.getHeight();
			if ((3 * w) / h == 4 && mode.getBitDepth() >= 16 )
			{
				rectModes.add(mode);
			}
		}
		Collections.sort(rectModes, new Comparator<DisplayMode>() {

			public int compare(DisplayMode o1, DisplayMode o2)
			{
				return Math.abs(o1.getWidth()-800);
			}
		
		});
		boolean ok = false;
		for(DisplayMode mode : rectModes)
		{
			try
			{
				device.setDisplayMode(mode);
				ok = true;
			}
			catch( Exception e)
			{
				
			}
			if(ok) break;
		}
		if(!ok) device.setDisplayMode(old);
	}

	/**
	 * Instructs the View to enter fullscreen mode.
	 */
	public void setFullscreen()
	{
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();

		GraphicsDevice device = env.getDefaultScreenDevice();

		device.setFullScreenWindow(window);

	}

	private void setupDispatcher()
	{
		dispatcher = new EventDispatcher();

		window.addKeyListener(dispatcher);
		window.addMouseListener(dispatcher);
		window.addMouseMotionListener(dispatcher);
	}

	private void setupDrawer()
	{
		strategy = window.getBufferStrategy();
		drawer = new Graphics2DDrawer((Graphics2D) strategy.getDrawGraphics());
	}

	/**
	 * Refreshes the contents of the View.
	 */
	public void renderFrame()
	{
		if (!strategy.contentsLost())
		{
			Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();

			g2d.setBackground(Color.BLACK);
			g2d.fillRect(0, 0, window.getBounds().width,
					window.getBounds().height);

			drawer.setGraphics2D(g2d);

			drawViewComponents();

			strategy.show();
			drawer.getGraphics2D().dispose();
		}
		else
		{
			strategy = window.getBufferStrategy();
		}
	}

	/**
	 * Add a {@link ViewComponent} to this View.
	 * 
	 * @param vc
	 *            ViewComponent to add to this View.
	 */
	public void addViewComponent(ViewComponent vc)
	{
		components.add(vc);
		dispatcher.registerComponent(vc);
	}

	public List<ViewComponent> getViewComponents()
	{
		return Collections.unmodifiableList(components);
	}

	public void removeViewComponent(ViewComponent vc)
	{
		components.remove(vc);
		dispatcher.deregisterComponent(vc);
	}

	public void removeAllViewComponents(List<ViewComponent> vcs)
	{
		components.removeAll(vcs);

		for (ViewComponent vc : vcs)
			dispatcher.deregisterComponent(vc);
	}

	private void drawViewComponents()
	{
		for (ViewComponent vc : components)
			vc.draw(drawer);
	}

	public void addKeyListener(KeyListener kl)
	{
		dispatcher.addKeyListener(kl);
	}

	public void removeKeyListener(KeyListener kl)
	{
		dispatcher.removeKeyListener(kl);
	}

	public void addMouseListener(MouseListener ml)
	{
		dispatcher.addMouseListener(ml);
	}

	public void removeMouseListener(MouseListener ml)
	{
		dispatcher.addMouseListener(ml);
	}

	public int getWidth()
	{
		return window.getWidth();
	}

	public int getHeight()
	{
		return window.getHeight();
	}

	public Image makeVolatileImage(int w, int h)
	{

		return window.getGraphicsConfiguration().createCompatibleImage(w, h, Transparency.BITMASK);

	}
}
