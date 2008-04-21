package rutebaga.view.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.Memory;
import rutebaga.model.entity.Vision;
import rutebaga.model.environment.ConcreteInstanceSet;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSet;
import rutebaga.model.environment.TileConverter;
import rutebaga.model.map.Tile;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.rwt.ViewComponent;
import temporary.LocationLayerComparator;


/**
 * Shows the actual gameplay.
 * 
 */
@SuppressWarnings("unchecked")
public class MapComponent extends ViewComponent implements TargetInstanceObserver
{

	private static final int TILE_SIZE = 64;
	private static final ColorAttribute FOG = new ColorAttribute(new Color(0,0,0, 150));
	private static final Color TARGETED = new Color(255, 0, 0, 170);
	private static final Color MOUSE_TARGETED = new Color(0, 0, 255, 255);
	
	private BufferedImage targetImage;
	
	private Instance currentlyTargeted;
	private Instance mouseOverTile;

	// private static LayerComparator layerComparator = new LayerComparator();
	private static LocationLayerComparator<AppearanceInstance> appearanceComparator = new LocationLayerComparator<AppearanceInstance>();

	private Entity avatar;

	public MapComponent(TargetInstanceObservable observable, Entity avatar, int width, int height)
	{
		this.avatar = avatar;
		
		observable.addObserver(this);

		this.setBounds(new Rectangle(width, height));
	}

	public void draw(Drawer draw)
	{
		long time;
		time = System.currentTimeMillis();
		drawMemorySet(draw, avatar.getVision());
		rutebaga.commons.Log.log("memory set total: " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();
		drawVisibleSet(draw, avatar.getVision());
		rutebaga.commons.Log.log("visible set total: " + (System.currentTimeMillis() - time));
		// drawAvatar(draw);

	}

	private void drawVisibleSet(Drawer draw, Vision avatarVision)
	{
		ArrayList<AppearanceInstance> sortedList = new ArrayList<AppearanceInstance>();
		
		AppearanceInstance targetInstance = null;
		AppearanceInstance mouseTargetInstance = null;
		
		for (Instance instance : avatarVision.getActiveSet())
		{
			Point p = centerPointOn(avatar, instance
					.getCoordinate(), getWidth(), getHeight());
			
			if(currentlyTargeted != null && instance == currentlyTargeted) {
				targetInstance = new AppearanceInstance(instance.getAppearance(), p,
						instance.getLayer());
				
				sortedList.add(targetInstance);
			} else if(mouseOverTile != null && instance == mouseOverTile) {
				mouseTargetInstance = new AppearanceInstance(instance.getAppearance(), p,
						instance.getLayer());
				
				sortedList.add(mouseTargetInstance);
			} else {
				sortedList.add(new AppearanceInstance(instance.getAppearance(), p,
					instance.getLayer()));
			}
		}
		long time = System.currentTimeMillis();

		Point p = centerPointOn(avatar, avatar
				.getCoordinate(), getWidth(), getHeight());
		sortedList.add(new AppearanceInstance(avatar.getAppearance(),
				p, avatar.getLayer()));
		Collections.sort(sortedList, appearanceComparator);
		rutebaga.commons.Log.log("sorting instances: " + (System.currentTimeMillis() - time));

		time = System.currentTimeMillis();

		long pointCreationTime = 0;
		long drawingTime = 0;

		for (AppearanceInstance instance : sortedList)
		{
			time = System.currentTimeMillis();
			p = instance.getLocation();
			pointCreationTime += System.currentTimeMillis() - time;

			time = System.currentTimeMillis();

			Image image = instance.getAppearance().getImage();
				

			// FIXME encapsulate -- necessary to watch bounds checking
			// if (avatar.getTile().equals(instance.getTile()))
			// {
			// BufferedImage compImg = Frame.getFrames()[0]
			// .getGraphicsConfiguration().createCompatibleImage(
			// image.getWidth(null), image.getHeight(null),
			// Transparency.BITMASK);
			// Graphics2D g = (Graphics2D) compImg.getGraphics();
			// g.drawImage(image, 0, 0, null);
			// Composite composite = AlphaComposite.getInstance(
			// AlphaComposite.SRC_ATOP, 0.3F);
			// g.setComposite(composite);
			// g.setPaint(Color.YELLOW);
			// g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
			// g.dispose();
			// image = compImg;
			// }
			if(targetInstance != null && targetInstance == instance) {
				Image imagePaint = getTargetImage(image);
				Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.7F);
				
				Graphics2D g2d = (Graphics2D) imagePaint.getGraphics();
				g2d.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
				g2d.setComposite(composite);
				g2d.setColor(TARGETED);
				g2d.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
				g2d.dispose();
				
				draw.drawImage(p, imagePaint);
				
			} else if(mouseTargetInstance != null && mouseTargetInstance == instance) {
				Image imagePaint = getTargetImage(image);
				Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.7F);
				
				Graphics2D g2d = (Graphics2D) imagePaint.getGraphics();
				g2d.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
				g2d.setComposite(composite);
				g2d.setColor(MOUSE_TARGETED);
				g2d.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
				g2d.dispose();
				
				draw.drawImage(p, imagePaint);
			} else {
				draw.drawImage(p, image);
			}
			

			drawingTime += System.currentTimeMillis() - time;

		}
		rutebaga.commons.Log.log("drawing instances: "
				+ (System.currentTimeMillis() - time) + "[points: "
				+ pointCreationTime + "; drawing: " + drawingTime + "]");

	}
	
	private Image getTargetImage(Image image) {
		if(targetImage == null || (targetImage.getWidth() != image.getWidth(null) || targetImage.getHeight() != image.getHeight(null))) {
			targetImage =  new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB_PRE);
		}
		Graphics g = targetImage.getGraphics();
		g.setColor(new Color(0,0,0,0)); 
		g.drawRect(0, 0, targetImage.getWidth(), targetImage.getHeight());
		g.dispose();
		return targetImage;
	}

	private void drawMemorySet(Drawer draw, Vision avatarVision)
	{
		long time;

		//Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3F);

		//draw.setComposite(composite);

		time = System.currentTimeMillis();
		Map<IntVector2D, Set<Memory>> memory = avatarVision.getMemory();
		rutebaga.commons.Log.log("memory access: "
				+ (System.currentTimeMillis() - time));

		ArrayList<AppearanceInstance> sortedMemory = new ArrayList<AppearanceInstance>();

		time = System.currentTimeMillis();

		for (IntVector2D v : memory.keySet())
		{
			for (Memory mem : memory.get(v))
			{

				Point p = centerPointOn(avatar, mem.getCoordinate(), getWidth(), getHeight());
				
				AppearanceInstance instance = new AppearanceInstance(mem
						.getAppearance(), p, mem.getLayer());
				
				p = new Point(instance.getLocation().x, instance.getLocation().y);
				
				if (p.x < 0)
					p.x += TILE_SIZE;
				if (p.y < 0)
					p.y += TILE_SIZE;

				if (p.x > getWidth())
					p.x -= TILE_SIZE;
				if (p.y > getHeight())
					p.y -= TILE_SIZE;

				if (this.getBounds().contains(p))
					sortedMemory.add(instance);
			}
		}

		rutebaga.commons.Log.log("memory point collection: "
				+ (System.currentTimeMillis() - time));

		time = System.currentTimeMillis();
		Collections.sort(sortedMemory, appearanceComparator);
		rutebaga.commons.Log.log("sorting memories: "
				+ (System.currentTimeMillis() - time));

		time = System.currentTimeMillis();

		for (AppearanceInstance mem : sortedMemory)
		{
			draw.drawImage(mem.getLocation(), mem.getAppearance().getImage());
		}



		draw.setAttribute(FOG);
		draw.drawRectangle(new Point(getX(), getY()), getWidth(), getHeight());
		draw.setAttribute(null);
		draw.clearComposite();
		
		rutebaga.commons.Log.log("drawing memories: "
				+ (System.currentTimeMillis() - time));
	}

	@SuppressWarnings("unused")
	private void drawAvatar(Drawer draw)
	{
		Point p = new Point(getWidth() / 2, getHeight() / 2);
		draw.drawImage(p, avatar.getAppearance().getImage());
	}

	public static Point centerPointOn(Instance centerInstance, Vector2D other, int width, int height)
	{
		TileConverter convertor = centerInstance.getEnvironment().getTileConvertor();
		
		Vector2D center = centerInstance.getCoordinate();
		
		Point centered = new Point();
		center = convertor.toRect(center);
		other = convertor.toRect(other);

		centered.x = (int) ((((other.get(0) - center.get(0)) * TILE_SIZE) + (width / 2)));
		centered.y = (int) ((((other.get(1) - center.get(1)) * TILE_SIZE) + (height / 2)));
		return centered;
	}
	
	public static Vector2D reverseCenter(Instance centerInstance, Point other, int width, int height)
	{
		TileConverter convertor = centerInstance.getEnvironment().getTileConvertor();
		Vector2D center = centerInstance.getCoordinate();
		
		center = convertor.toRect(center);
		
		double x = other.x;
		x -= (width/2);
		x /= TILE_SIZE;
		x += center.get(0);
		
		double y = other.y;
		y -= (height/2);
		y /= TILE_SIZE;
		y += center.get(1);
		
		return convertor.fromRect(new Vector2D(x, y));
	}

	public void update(TargetInstanceObservable o, Instance arg) {
		currentlyTargeted = arg;
	}
	
	protected boolean processMouseEvent(MouseEvent event) {
		trackMouseLocation(event.getPoint());
		
		return false;
	}
	
	protected boolean processMouseMotionEvent( MouseEvent event ) {
		if (event.getID() == MouseEvent.MOUSE_EXITED)
			mouseOverTile = null;
		trackMouseLocation(event.getPoint());
		
		return false;
	}
	
	private void trackMouseLocation(Point p) {
		Environment environment = avatar.getEnvironment();
		TileConverter convertor = environment.getTileConvertor();
		
		
		Vector2D vector = reverseCenter(avatar, p, getWidth(), getHeight());
		
		IntVector2D tileCoord = convertor.tileOf(vector);

		InstanceSet set = new ConcreteInstanceSet();
		set.addAll(environment.instancesAt(tileCoord));
	
		for(Tile tile : set.getTiles()) {
			if(tile != null)
				mouseOverTile = tile;
		}
		
	}
}
