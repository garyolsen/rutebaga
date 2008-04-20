package rutebaga.scaffold.builders;


public class DefaultBuilder extends ChainedBuilder
{

	public DefaultBuilder()
	{
		super();
		register(new EntityTypeBuilder());
		register(new ImageBuilder());
		register(new ImageSliceBuilder());
		register(new GameConfigBuilder());
		register(new StatsBuilder());
		register(new NamedValueProviderBuilder());
		register(new SlotTypeBuilder());
		register(new ItemTypeBuilder());
		register(new EntityEffectBuilder());
	}

}
