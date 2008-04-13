package temporary;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.VolatileImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import legacy.GraphicsManager;

import rutebaga.model.entity.Entity;
import rutebaga.model.environment.Instance;
import rutebaga.model.map.Tile;

public class TestImageConverter
{
	private static Map<Class, Image> images = new HashMap<Class, Image>();
	
	public static GraphicsManager manager;

	private static boolean init = false;

	private static void init()
	{
		try
		{
			Image cheese = ImageIO.read(new File("TestImages/cheese.png"));
			Image grass = ImageIO.read(new File("TestImages/grass.jpg"));
			Image treasure = ImageIO.read(new File("TestImages/treasure.png"));
			
			images.put(Entity.class, cheese);
			images.put(Tile.class, grass);
			images.put(WindTunnel.class, treasure);
			images.put(Bumper.class, treasure);
			
			for(Class clazz : images.keySet())
			{
				Image buffImg = images.get(clazz);
				Image volImg = manager.createImage(buffImg.getWidth(null), buffImg.getHeight(null));
				Graphics g = volImg.getGraphics();
				g.drawImage(buffImg, 0, 0, null);
				g.dispose();
				images.put(clazz, volImg);
			}
			init = true;
		}
		catch (Throwable t)
		{
			throw new RuntimeException(t);
		}
	}

	public static Image getImageFor(Instance instance)
	{
		if(!init) init();
		for (Class clazz : images.keySet())
		{
			if (clazz.isAssignableFrom(instance.getClass()))
				return images.get(clazz);
		}
		return null;
	}
}
