package rutebaga.model.environment;

import rutebaga.model.DefaultLayers;
import rutebaga.model.Targetable;

public class Decal extends Instance implements Targetable<Instance>
{
	private double layer = DefaultLayers.AIR.getLayer();
	private Integer lifetime;
	private Instance target;

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
		{
			lifetime--;
			if(target != null && target.existsInUniverse())
			{
				move(target.getCoordinate());
			}
		}
		if (lifetime < 0)
			remove();
	}

	@Override
	public void setMass(double mass) {
		// can't set decal's mass
	}
	
	public void setTarget(Instance instance)
	{
		this.target = instance;
	}

}
