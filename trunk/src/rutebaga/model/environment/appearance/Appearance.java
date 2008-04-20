package rutebaga.model.environment.appearance;

import java.awt.Image;
import java.awt.Point;

public class Appearance
{
	public enum Orientation
	{
		NW,
		N,
		NE,
		W,
		C,
		E,
		SW,
		S,
		SE;

		private void transformPoint(Point point, Point dimensions)
		{
			int w = dimensions.x;
			int h = dimensions.y;

			if (this == S || this == SE || this == SW)
			{
				// BOTTOM-ALIGNED
				point.y -= h;
			}
			else if (this == C || this == W || this == E)
			{
				// MIDDLE-ALIGNED
				point.y -= h / 2;
			}

			if (this == E || this == SE || this == NE)
			{
				// RIGHT-ALIGNED
				point.x -= w;
			}
			else if (this == C || this == N || this == S)
			{
				point.x -= w / 2;
			}
		}
	}

	private int offsetX;
	private int offsetY;

	private Image image;
	private Orientation orientation = Orientation.S;

	public Appearance()
	{
		// TODO Auto-generated constructor stub
	}

	public Appearance(Image image)
	{
		this.image = image;
	}

	public Image getImage()
	{
		return image;
	}

	public int getOffsetX()
	{
		return offsetX;
	}

	public Orientation getOrientation()
	{
		return orientation;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}

	public void setOffsetX(int offset)
	{
		this.offsetX = offset;
	}

	public void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}
	
	public void transformPoint(Point p, Point dimensions)
	{
		this.orientation.transformPoint(p, dimensions);
		p.x += this.offsetX;
		p.y += this.offsetY;
	}

	public int getOffsetY()
	{
		return offsetY;
	}

	public void setOffsetY(int offsetY)
	{
		this.offsetY = offsetY;
	}

}
