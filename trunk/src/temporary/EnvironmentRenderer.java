package temporary;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Set;

import rutebaga.commons.math.Vector;
import rutebaga.model.entity.Entity;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.map.Tile;


public class EnvironmentRenderer
{
	/*
	private static final int TILE_SIZE = 32;

	private Environment environment;

	public EnvironmentRenderer(Environment environment)
	{
		super();
		this.environment = environment;
	}

	public void draw(Graphics2D g, Vector offset)
	{
		Set<Vector> allTiles = environment.getSpace();
		Double[] min = new Double[environment.getDimension()];
		Double[] max = new Double[environment.getDimension()];

		for (Vector v : allTiles)
		{
			for (int idx = 0; idx < min.length; idx++)
			{
				double component = v.get(idx);
				if (min[idx] == null || component < min[idx])
					min[idx] = component;
				if (max[idx] == null || component > max[idx])
					max[idx] = component;
			}
		}

		// henceforth, we ignore anything > dimension 2.
		// because we just don't have 5-dimensional screens.

		double minPrim[] = new double[min.length];
		for (int idx = 0; idx < min.length; idx++)
			minPrim[idx] = min[idx];

		if (offset == null)
			offset = new Vector(minPrim, 0, 2);

		draw(g, Tile.class, offset);
		draw(g, Entity.class, offset);
		draw(g, WindTunnel.class, offset);
		draw(g, Bumper.class, offset);
	}

	private void draw(Graphics2D g, Class clazz, Vector offset)
	{
		for (Instance instance : environment.getInstances())
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
	}

	public Environment getEnvironment()
	{
		return environment;
	}

	public void setEnvironment(Environment environment)
	{
		this.environment = environment;
	}
	*/
}

