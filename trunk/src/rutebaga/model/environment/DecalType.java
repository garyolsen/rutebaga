package rutebaga.model.environment;

import rutebaga.model.DefaultLayers;

public class DecalType extends ConcreteInstanceType<Decal>
{
	private double layer = DefaultLayers.AIR.getLayer();
	private Integer lifetime;

	@Override
	protected Decal create()
	{
		return new Decal(this);
	}

	@Override
	protected void initialize(Decal instance)
	{
		super.initialize(instance);
		instance.setLayer(layer);
		instance.setLifetime(lifetime);
		instance.blackListTerrainTypes();
	}

	public double getLayer()
	{
		return layer;
	}

	public void setLayer(double layer)
	{
		this.layer = layer;
	}

	public Integer getLifetime()
	{
		return lifetime;
	}

	public void setLifetime(Integer lifetime)
	{
		this.lifetime = lifetime;
	}

}
