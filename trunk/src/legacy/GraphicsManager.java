package legacy;

import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * Sets up and provides access to the screen.
 * 
 * @author Gary LosHuertos
 * 
 */
public class GraphicsManager
{
	private java.awt.Frame frame;
	private BufferStrategy bufferStrategy;

	private int width;

	private int height;

	public Image createImage(int width, int height)
	{
		return this.getGraphicsConfiguration().createCompatibleImage(width,
				height, Transparency.TRANSLUCENT);
	}

	public Image createVolatileImage(int width, int height)
	{
		return this.getGraphicsConfiguration().createCompatibleImage(width,
				height, Transparency.TRANSLUCENT);
	}

	/**
	 * Draws the context of the active graphics context to the screen and flips
	 * the backbuffer, if it is being used.
	 * 
	 * This is the method that should be called when a frame has been rendered.
	 */
	public void draw()
	{
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		bufferStrategy.show();
		g.clearRect(0, 0, width, height);
		g.dispose();
	}

	public GraphicsConfiguration getGraphicsConfiguration()
	{
		return frame.getGraphicsConfiguration();
	}

	/**
	 * @return a new graphics context that can be drawn to
	 */
	public Graphics2D getGraphicsContext()
	{
		return (Graphics2D) bufferStrategy.getDrawGraphics();
	}

	/**
	 * @return the height of the screen (in pixels)
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @return the width of the screen (in pixels)
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Creates a full screen exclusive window and prepares it with a backbuffer,
	 * if it is being used.
	 * 
	 * @param width
	 *            the desired resolution's width
	 * @param height
	 *            the desired resolution's height
	 */
	public void init(int width, int height)
	{

		this.width = width;
		this.height = height;

		System.setProperty("sun.java2d.translaccel", "true");
		System.setProperty("sun.java2d.opengl", "true");
		System.setProperty("sun.java2d.accthreshold", "0");

		frame = new java.awt.Frame();
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.setVisible(true);

		GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();

		device.setFullScreenWindow(frame);

		DisplayMode oldMode = device.getDisplayMode();

		try
		{
			device.setDisplayMode(new DisplayMode(width, height, 32, 60));
		}
		catch (Exception t)
		{
			device.setDisplayMode(oldMode);
			t.printStackTrace();
			System.exit(0);
		}

		frame.createBufferStrategy(2);
		bufferStrategy = frame.getBufferStrategy();

		System.out.println("INITIALIZED GRAPHICS MANAGER");
		System.out.println("MULTIBUFFER: "
				+ bufferStrategy.getCapabilities().isMultiBufferAvailable());
		System.out.println("PAGE FLIPPING: "
				+ bufferStrategy.getCapabilities().isPageFlipping());
	}

	/**
	 * Registers a key listener with the active window.
	 * 
	 * @param keyListener
	 *            the key listener
	 */
	public void registerKeyListener(KeyListener keyListener)
	{
		frame.addKeyListener(keyListener);
	}

}
