package rutebaga.view.game;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
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
import rutebaga.model.environment.LayerComparator;
import rutebaga.view.drawer.ColorAttribute;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.rwt.ViewComponent;

/**
 * Shows the actual gameplay.
 * 
 */
public class MapComponent extends ViewComponent
{

	private static final int TILE_SIZE = 32;
	private static final ColorAttribute FOG = new ColorAttribute(new Color(220, 220, 220, 50));
	
	private static LayerComparator layerComparator = new LayerComparator();
	
	private CharEntity avatar;
	
	public MapComponent( CharEntity avatar, int width, int height) {
		this.avatar = avatar;
		
		this.setBounds(new Rectangle(width, height));
	}

	public void draw(Drawer draw) {
		drawMemorySet(draw, avatar.getVision());
		drawVisibleSet(draw, avatar.getVision());
		drawAvatar(draw);
	}
	
	private void drawVisibleSet(Drawer draw, Vision avatarVision) {	
		ArrayList<Instance> sortedList = new ArrayList<Instance>(avatarVision.getActiveSet());
		
		
		Collections.sort(sortedList, layerComparator);
		
		for(Instance instance : sortedList) {
			
			Point p = centerPointOnAvatar(avatar.getCoordinate(), instance.getCoordinate());
			
			Image image = instance.getAppearance().getImage();
			

			draw.drawImage(p , image);
		}
	
	}
	
	private void drawMemorySet(Drawer draw, Vision avatarVision) {
		Map<IntVector2D, Set<Memory>> memory = avatarVision.getMemory();
		
		ArrayList<Memory> sortedMemory = new ArrayList<Memory>();
		
		for(IntVector2D v : memory.keySet()) {
			for(Memory mem : memory.get(v)) {
				
				Point p = centerPointOnAvatar( avatar.getCoordinate(), mem.getCoordinate() );
				
				if(p.x < 0 ) 
					p.x += TILE_SIZE;
				if(p.y < 0)
					p.y += TILE_SIZE;
				
				if(p.x > getWidth())
					p.x -= TILE_SIZE;
				if(p.y > getHeight())
					p.y -= TILE_SIZE;
				
				if(this.getBounds().contains(p))
					sortedMemory.add(mem);
			}
		}
		
		Collections.sort(sortedMemory, layerComparator);
		
		for(Memory mem : sortedMemory) {
			Point p = centerPointOnAvatar(avatar.getCoordinate(), mem.getCoordinate());
			draw.drawImage(p, mem.getAppearance().getImage());
		}
		
		
		draw.setAttribute(FOG);
		draw.drawRectangle(new Point(getX(),getY()), getWidth(), getHeight());
		draw.setAttribute(null);
	}
	
	private void drawAvatar(Drawer draw ) {
		Point p = new Point(getWidth()/2, getHeight()/2);
		draw.drawImage(p, avatar.getAppearance().getImage());
	}
	
	private Point centerPointOnAvatar(Vector2D center, Vector2D other) {
		Point centered = new Point();
		
		centered.x = (int) (((other.get(0) - center.get(0)) * TILE_SIZE) + (getWidth()/2));
		centered.y = (int) (((other.get(1) - center.get(1)) * TILE_SIZE) + (getHeight()/2));
		return centered;
	}
}
