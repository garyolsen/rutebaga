package rutebaga.scaffold.builders;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rutebaga.scaffold.MasterScaffold;

public class ImageBuilder extends ConfigFileBuilder
{
	private ImageProvider imageProvider = new BufferedImageProvider();
	
	public Object create(String id)
	{
		try
		{
			Image image = ImageIO.read(new File(getProperty(id, "path")));
			Image transferred = imageProvider.makeImage(image.getWidth(null), image.getHeight(null));
			Graphics g = transferred.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			return transferred;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getDefaultFileName()
	{
		return "config/images";
	}

}
