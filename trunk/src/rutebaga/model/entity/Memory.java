package rutebaga.model.entity;

import rutebaga.commons.Vector;
import rutebaga.model.environment.Appearance;

public class Memory
{
	private Appearance appearance;
	private Vector coordinate;

	public Memory(Appearance appearance, Vector coordinate)
	{
		super();
		this.appearance = appearance;
		this.coordinate = coordinate;
	}

	public Appearance getAppearance()
	{
		return appearance;
	}

	public void setAppearance(Appearance appearance)
	{
		this.appearance = appearance;
	}

	public Vector getCoordinate()
	{
		return coordinate;
	}

	public void setCoordinate(Vector coordinate)
	{
		this.coordinate = coordinate;
	}
}
