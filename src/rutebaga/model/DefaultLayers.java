package rutebaga.model;

public enum DefaultLayers
{
	TERRAIN(0.0), SHADOW(0.05), GROUND(0.1), AIR(0.2), SKY(0.3), OVERLAY(0.9);

	private final double layer;

	public double getLayer()
	{
		return layer;
	}

	private DefaultLayers(double layer)
	{
		this.layer = layer;
	}
}
