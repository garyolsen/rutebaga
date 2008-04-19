package rutebaga.scaffold.builders;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;

public class ImageSliceBuilder implements Builder, ReaderProcessor
{
	// id --> filename
	private Map<String, String> filenames = new HashMap<String, String>();
	// id --> location
	private Map<String, Point> locations = new HashMap<String, Point>();
	// filename --> dimensions
	private Map<String, Point> dimensions = new HashMap<String, Point>();
	// filename -- image
	private Map<String, Image> images = new HashMap<String, Image>();

	private String currentFileName;
	private int currentX;
	private int currentY;
	private int currentW;
	private int currentH;

	private ImageProvider imageProvider;

	public ImageSliceBuilder(ImageProvider provider)
	{
		this.imageProvider = provider;
		String filename = "config/slicedimages";
		new FileProcessor().execute(this, filename);

		for (String name : this.filenames.keySet())
		{
			rutebaga.commons.Log.log(name + ": " + locations.get(name).getX() + ", "
					+ locations.get(name).getY());
		}
	}

	public ImageSliceBuilder()
	{
		this(new BufferedImageProvider());
	}

	public String[] availableIds()
	{
		return filenames.keySet().toArray(new String[0]);
	}

	public Object create(String id)
	{
		try
		{
			String filename = filenames.get(id);
			Image parent = images.get(filename);
			if (parent == null)
			{
				parent = ImageIO.read(new File(filename));
				images.put(filename, parent);
			}
			Point dimensions = this.dimensions.get(filename);
			Point location = this.locations.get(id);
			int w = (int) dimensions.getX();
			int h = (int) dimensions.getY();
			int sX = (int) location.getX();
			int sY = (int) location.getY();
			Image image = imageProvider.makeImage((int) dimensions.getX(),
					(int) dimensions.getY());
			Graphics g = image.getGraphics();
			rutebaga.commons.Log.log("Pulling " + id + " off the scaffold with " + w + " " + h + " " + sX + " " + sY);
			g.drawImage(parent, 0, 0, w, h, sX, sY, sX+w, sY+h, null);
			g.dispose();
			return image;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{

	}

	public void processLine(String line)
	{
		if (line == null)
			return;
		String[] parts = line.split("[\\t ]+");
		for (String part : parts)
		{
			rutebaga.commons.Log.log("ITEM: " + part);
		}
		rutebaga.commons.Log.log();
		if (parts.length == 4 && parts[0].equals("file"))
		{
			currentFileName = parts[1];
			Point dimensions = new Point(Integer.parseInt(parts[2]), Integer
					.parseInt(parts[3]));
			this.dimensions.put(currentFileName, dimensions);
			currentX = 0;
			currentY = 0;
			currentW = (int) dimensions.getX();
			currentH = (int) dimensions.getY();
		}
		else
		{
			for (String str : parts)
			{
				Point location = new Point(currentX, currentY);
				locations.put(str, location);
				filenames.put(str, currentFileName);
				currentX += currentW;
			}
			if (parts.length > 0 && !(parts.length == 0))
			{
				currentY += currentH;
				currentX = 0;
			}
		}
	}

}
