package rutebaga.scaffold.builders;

import rutebaga.commons.math.ValueProviderPointer;
import rutebaga.scaffold.MasterScaffold;

public class NamedValueProviderBuilder extends ConfigFileBuilder
{
	private AbstractValueProviderFactory factory = DefaultValueProviderFactory
			.getInstance();

	@Override
	protected String getDefaultFileName()
	{
		return "config/valueproviders";
	}

	public Object create(String id)
	{
		return new ValueProviderPointer();
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		ValueProviderPointer ptr = (ValueProviderPointer) object;
		String expr = getProperty(id, "value");
		ptr.setRef(factory.parse(expr, scaffold));
	}

}
