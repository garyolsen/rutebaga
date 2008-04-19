package rutebaga.scaffold.builders;

import rutebaga.test.scaffold.EntityTypeBuilder;

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
	}

}
