package rutebaga.view.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;


import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Memory;
import rutebaga.model.entity.Vision;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.TileConvertor;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.rwt.ViewComponent;
import temporary.DrawOrderComparator;

/**
 * Shows the actual gameplay.
 * 
 */
@SuppressWarnings("unchecked")
public class MapComponent extends ViewComponent
{

	private static final int TILE_SIZE = 32;
	private static final ColorAttribute FOG = new ColorAttribute(Color.BLACK);

	// private static LayerComparator layerComparator = new LayerComparator();
	private static DrawOrderComparator<Instance> instanceComparator = new DrawOrderComparator();
	private static DrawOrderComparator<Memory> memoryComparator = new DrawOrderComparator();

	private CharEntity avatar;

	public MapComponent(CharEntity avatar, int width, int height)
	{
		this.avatar = avatar;

		this.setBounds(new Rectangle(width, height));
	}

	public void draw(Drawer draw)
	{
		long time;
		time = System.currentTimeMillis();
		drawMemorySet(draw, avatar.getVision());
		System.out.println("memory set total: " + (System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		drawVisibleSet(draw, avatar.getVision());
		System.out.println("visible set total: " + (System.currentTimeMillis()-time));
		drawAvatar(draw);
	}

	private void drawVisibleSet(Drawer draw, Vision avatarVision)
	{
		ArrayList<Instance> sortedList = new ArrayList<Instance>(avatarVision
				.getActiveSet());
		long time = System.currentTimeMillis();
		Collections.sort(sortedList, instanceComparator);
		System.out.println("sorting instances: " + (System.currentTimeMillis()-time));

		time = System.currentTimeMillis();
		
		long pointCreationTime = 0;
		long drawingTime = 0;
		
		for (Instance instance : sortedList)
		{
			time = System.currentTimeMillis();
			Point p = centerPointOnAvatar(avatar.getCoordinate(), instance
					.getCoordinate());
			pointCreationTime += System.currentTimeMillis() - time;
			
			time = System.currentTimeMillis();
			
			Image image = instance.getAppearance().getImage();

			//FIXME encapsulate -- necessary to watch bounds checking
			if (avatar.getTile().equals(instance.getTile()))
			{
				BufferedImage compImg = Frame.getFrames()[0]
						.getGraphicsConfiguration().createCompatibleImage(
								image.getWidth(null), image.getHeight(null), Transparency.BITMASK);
				Graphics2D g = (Graphics2D) compImg.getGraphics();
				g.drawImage(image, 0, 0, null);
				Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3F);
				g.setComposite(composite);
				g.setPaint(Color.YELLOW);
				g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
				g.dispose();
				image = compImg;
			}

			draw.drawImage(p, image);
			
			drawingTime += System.currentTimeMillis() - time;

		}
		System.out.println("drawing instances: " + (System.currentTimeMillis()-time) + "[points: " + pointCreationTime + "; drawing: " + drawingTime + "]");

	}

	private void drawMemorySet(Drawer draw, Vision avatarVision)
	{
		long time;
		

		time = System.currentTimeMillis();
		draw.setAttribute(FOG);
		draw.drawRectangle(new Point(getX(), getY()), getWidth(), getHeight());
		draw.setAttribute(null);
		System.out.println("drawing fog: " + (System.currentTimeMillis()-time));
		
		Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3F);

		draw.setComposite(composite);
		
		time = System.currentTimeMillis();
		Map<IntVector2D, Set<Memory>> memory = avatarVision.getMemory();
		System.out.println("memory access: " + (System.currentTimeMillis()-time));

		ArrayList<Memory> sortedMemory = new ArrayList<Memory>();
		
		time = System.currentTimeMillis();

		for (IntVector2D v : memory.keySet())
		{
			for (Memory mem : memory.get(v))
			{

				Point p = centerPointOnAvatar(avatar.getCoordinate(), mem
						.getCoordinate());

				if (p.x < 0)
					p.x += TILE_SIZE;
				if (p.y < 0)
					p.y += TILE_SIZE;

				if (p.x > getWidth())
					p.x -= TILE_SIZE;
				if (p.y > getHeight())
					p.y -= TILE_SIZE;

				if (this.getBounds().contains(p))
					sortedMemory.add(mem);
			}
		}
		
		System.out.println("memory point collection: " + (System.currentTimeMillis()-time));

		time = System.currentTimeMillis();
		Collections.sort(sortedMemory, memoryComparator);
		System.out.println("sorting memories: " + (System.currentTimeMillis()-time));

		time = System.currentTimeMillis();
		
		for (Memory mem : sortedMemory)
		{
			Point p = centerPointOnAvatar(avatar.getCoordinate(), mem
					.getCoordinate());
			draw.drawImage(p, mem.getAppearance().getImage());
		}
		
		System.out.println("drawing memories: " + (System.currentTimeMillis()-time));
		
		draw.clearComposite();
	}

	private void drawAvatar(Drawer draw)
	{
		Point p = new Point(getWidth() / 2, getHeight() / 2);
		draw.drawImage(p, avatar.getAppearance().getImage());
	}

	private Point centerPointOnAvatar(Vector2D center, Vector2D other)
	{
		Point centered = new Point();

		TileConvertor conv = avatar.getEnvironment().getTileConvertor();
		center = conv.toRect(center);
		other = conv.toRect(other);

		centered.x = (int) Math
				.round(((other.get(0) - center.get(0)) * TILE_SIZE)
						+ (getWidth() / 2));
		centered.y = (int) Math
				.round(((other.get(1) - center.get(1)) * TILE_SIZE)
						+ (getHeight() / 2));
		return centered;
	}
}
