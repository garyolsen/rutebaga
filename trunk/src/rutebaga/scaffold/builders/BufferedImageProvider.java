package rutebaga.scaffold.builders;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;

public class BufferedImageProvider implements ImageProvider
{
	private GraphicsConfiguration configuration;
	private int transparency = Transparency.BITMASK;
	
	public BufferedImageProvider(GraphicsConfiguration configuration)
	{
		super();
		this.configuration = configuration;
	}

	public BufferedImageProvider()
	{
		configuration = Frame.getWindows()[0].getGraphicsConfiguration();
	}

	public Image makeImage(int w, int h)
	{
		return configuration.createCompatibleImage(w, h, transparency);
	}
}
