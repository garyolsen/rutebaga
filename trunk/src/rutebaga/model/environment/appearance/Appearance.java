package rutebaga.model.environment.appearance;

import java.awt.Image;
import java.awt.Point;

public class Appearance
{
	public static enum Orientation
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

		public void transformPoint(Point point, Point dimensions)
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

	private Image image;
	private Orientation orientation = Orientation.S;

	public Appearance(Image image)
	{
		this.image = image;
	}

	public Appearance()
	{
		// TODO Auto-generated constructor stub
	}

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}

	public Orientation getOrientation()
	{
		return orientation;
	}

	public void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}

}
