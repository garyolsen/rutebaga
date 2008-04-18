package rutebaga.test.profiling;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import legacy.GraphicsManager;

public class GraphicsProfiler
{
	public static void main(String... args)
	{
		GraphicsManager man = new GraphicsManager();
		man.init(800, 600);

		Image image = man.createVolatileImage(800, 600);
		Image tile;
		try
		{
			tile = ImageIO.read(new File("TestImages/cheese.png"));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		Image[] blocks = new Image[10];
		for (int idx = 0; idx < blocks.length; idx++)
		{
			blocks[idx] = man.createVolatileImage(800, 600);
		}

		// draw 196 images per block (a total of 1960)
		for (int i = 0; i < blocks.length; i++)
		{

			Graphics g = blocks[i].getGraphics();
			for (int x = 0; x < 700; x += 50)
			{
				for (int y = 0; y < 700; y += 50)
				{
					g.drawImage(tile, x+i*3, y+i*7, null);
				}
			}
			g.dispose();
		}
		
		int TEST_LENGTH = 20;
		long time;
		
		time = System.currentTimeMillis();
		// testing: blitting the blocks
		for(int i=0; i < TEST_LENGTH; i++)
		{
			Graphics g = image.getGraphics();
			for(int block=0; block<blocks.length; block++)
			{
				g.drawImage(blocks[block], 0, 0, null);
			}
			g.dispose();
		}
		System.out.println("Blitting blocks: " + (System.currentTimeMillis()-time)/TEST_LENGTH);
		
		time = System.currentTimeMillis();
		for(int test=0; test< TEST_LENGTH; test++)
		{
			Graphics g = image.getGraphics();
			for (int i = 0; i < blocks.length; i++)
			{
				for (int x = 0; x < 700; x += 50)
				{
					for (int y = 0; y < 700; y += 50)
					{
						g.drawImage(tile, x+i*3, y+i*7, null);
					}
				}
			}
			g.dispose();
		}
		System.out.println("Blitting images: " + (System.currentTimeMillis()-time)/TEST_LENGTH);
		
		System.exit(0);
	}
}
