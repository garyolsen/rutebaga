package temporary;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Set;

import rutebaga.commons.math.Vector;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.Memory;
import rutebaga.model.environment.Instance;
import rutebaga.model.map.Tile;

public class EntityRenderer
{

	/*
	private static final int TILE_SIZE = 32;

	private Entity entity;

	public EntityRenderer(Entity entity)
	{
		super();
		this.entity = entity;
	}

	public void draw(Graphics2D g, Vector offset)
	{
		Class[] classes =
		{ Tile.class, Entity.class, WindTunnel.class, Bumper.class };
		for (Class clazz : classes)
		{
			drawMemory(g, clazz, offset);
		}
		for (Class clazz : classes)
		{
			draw(g, clazz, offset);
		}
	}

	private void draw(Graphics2D g, Class clazz, Vector offset)
	{
		Shape oldClip = g.getClip();
		Vector screenOffset = entity.getCoordinate().minus(offset);
		double radius = 9;
		g.setClip(new Ellipse2D.Double(screenOffset.get(0) * TILE_SIZE
				- (radius) * TILE_SIZE, screenOffset.get(1) * TILE_SIZE
				- (radius) * TILE_SIZE, radius * 2 * TILE_SIZE, radius * 2
				* TILE_SIZE));
		for (Instance instance : entity.getVision().getActiveSet())
		{
			if (clazz.isAssignableFrom(instance.getClass()))
			{
				Image image = TestImageConverter.getImageFor(instance);
				if (image != null)
				{
					Vector coordinate = new Vector(instance.getCoordinate()
							.asArray(), 0, 2);
					Vector draw = coordinate.minus(offset).times(TILE_SIZE);
					g.drawImage(image, (int) draw.get(0), (int) draw.get(1),
							null);
				}
			}
		}
		{
			Instance instance = entity;
			if (clazz.isAssignableFrom(instance.getClass()))
			{
				Image image = TestImageConverter.getImageFor(instance);
				if (image != null)
				{
					Vector coordinate = new Vector(instance.getCoordinate()
							.asArray(), 0, 2);
					Vector draw = coordinate.minus(offset).times(TILE_SIZE);
					g.drawImage(image, (int) draw.get(0), (int) draw.get(1),
							null);
				}
			}
		}
		g.setClip(oldClip);
	}

	private void drawMemory(Graphics2D g, Class clazz, Vector offset)
	{
		Composite old = g.getComposite();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.7F);
		g.setComposite(ac);
		g.setPaint(Color.blue);

		for (Set<Memory> memories : entity.getVision().getMemory().values())
		{
			for (Memory memory : memories)
			{
				Instance instance = memory.getAppearance().getInstance();
				if (clazz.isAssignableFrom(instance.getClass()))
				{
					Image image = TestImageConverter.getImageFor(instance);
					if (image != null)
					{
						Vector coordinate = new Vector(memory.getCoordinate()
								.asArray(), 0, 2);
						Vector draw = coordinate.minus(offset).times(TILE_SIZE);
						g.drawImage(image, (int) draw.get(0),
								(int) draw.get(1), null);
						g.fillRect((int) draw.get(0), (int) draw.get(1),
								TILE_SIZE, TILE_SIZE);
					}
				}
			}
		}

		g.setComposite(old);
	}
	*/
}

