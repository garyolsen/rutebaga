package rutebaga.scaffold.builders;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.imageio.ImageTypeSpecifier;

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
		try
		{
			configuration = Frame.getFrames()[0].getGraphicsConfiguration();
		}
		catch (Exception e)
		{

		}
	}

	public Image makeImage(int w, int h)
	{
		if (configuration != null)
			return configuration.createCompatibleImage(w, h, transparency);
		else
			return new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	}
}
