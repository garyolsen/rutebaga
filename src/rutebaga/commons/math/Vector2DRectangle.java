package rutebaga.commons.math;

import java.util.ArrayList;
import java.util.Collection;

public class Vector2DRectangle
{
	private Vector2D lowerBound;
	private Vector2D dimensions;

	public Vector2DRectangle(Vector2D lowerBound, Vector2D dimensions)
	{
		this.lowerBound = lowerBound;
		this.dimensions = dimensions;
	}

	public Vector2D getLowerBound()
	{
		return lowerBound;
	}

	public Vector2D getDimensions()
	{
		return dimensions;
	}

	public Collection<Vector2D> locationSet(double scale)
	{
		ArrayList<Vector2D> rval = new ArrayList<Vector2D>();
		
		double maxX = lowerBound.getX() + dimensions.getX();
		double maxY = lowerBound.getY() + dimensions.getY();
		
		for(double x=lowerBound.getX(); x < maxX; x += scale)
		{
			for(double y = lowerBound.getY(); y < maxY; y += scale)
			{
				rval.add(new Vector2D(x, y));
			}
		}

		return rval;
	}
	
	public Collection<IntVector2D> intLocationSet()
	{
		ArrayList<IntVector2D> rval = new ArrayList<IntVector2D>();
		
		int maxX = (int) Math.round(lowerBound.getX() + dimensions.getX());
		int maxY = (int) Math.round(lowerBound.getY() + dimensions.getY());
		
		for(int x=(int) Math.round(lowerBound.getX()); x < maxX; x ++)
		{
			for(int y = (int) Math.round(lowerBound.getY()); y < maxY; y ++)
			{
				rval.add(new IntVector2D(x, y));
			}
		}
		
		return rval;
	}
}
