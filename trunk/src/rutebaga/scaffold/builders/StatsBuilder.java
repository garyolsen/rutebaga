package rutebaga.scaffold.builders;

import java.util.logging.Level;
import java.util.logging.Logger;

import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.rel.ParseTreeNode;
import rutebaga.commons.math.rel.Parser;
import rutebaga.commons.math.rel.ReversePolishParser;
import rutebaga.model.entity.stats.DerivedStatisticId;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.entity.stats.Stats;
import rutebaga.scaffold.MasterScaffold;

public class StatsBuilder extends ConfigFileBuilder
{
	private AbstractValueProviderFactory factory = DefaultValueProviderFactory.getInstance();
	private Parser parser = new ReversePolishParser();
	
	private Logger logger = Logger.getLogger("StatsBuilder");
	
	private boolean isDerived(String id)
	{
		return "derived".equals(getProperty(id, "type"));
	}

	@Override
	protected String getDefaultFileName()
	{
		return "config/stats";
	}

	public Object create(String id)
	{
		String name = getProperty(id, "name");
		if(!isDerived(id))
		{
			return new StatisticId(name);
		}
		else
		{
			return new DerivedStatisticId(name, null);
		}
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		StatisticId statId = (StatisticId) object;
		rutebaga.commons.Log.log(id);
		if(isDerived(id))
		{
			rutebaga.commons.Log.log("derived stat");
			DerivedStatisticId derId = (DerivedStatisticId) statId;
			ValueProvider<Stats> vp = getValueProvider(id, "value", scaffold);
			derId.setBase(vp);
		}
		else
		{
			rutebaga.commons.Log.log("concrete stat " + getProperty(id, "default"));
			Double init = getDouble(id, "default");
			if(init != null)
				statId.setInitialValue(init);
		}
	}

}
