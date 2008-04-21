package rutebaga.model.environment;

import rutebaga.model.DefaultLayers;

public class Decal extends Instance
{
	private double layer = DefaultLayers.AIR.getLayer();
	private Integer lifetime;

	public Decal(InstanceType type)
	{
		super(type);
	}

	@Override
	public boolean blocks(Instance other)
	{
		return false;
	}

	@Override
	public double getLayer()
	{
		return layer;
	}

	public Integer getLifetime()
	{
		return lifetime;
	}

	@Override
	public double getMass()
	{
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier()
	{
		return InstanceSetIdentifier.EFFECT;
	}

	@Override
	public boolean isMobile()
	{
		return false;
	}

	public void setLayer(double layer)
	{
		this.layer = layer;
	}

	public void setLifetime(Integer lifetime)
	{
		this.lifetime = lifetime;
	}

	@Override
	public void tick()
	{
		if (lifetime != null)
			lifetime--;
		if (lifetime < 0)
			remove();
	}

	@Override
	public void setMass(double mass) {
		// can't set decal's mass
	}

}
