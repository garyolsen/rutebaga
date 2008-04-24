package rutebaga.scaffold.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
	private AbstractValueProviderFactory factory = DefaultValueProviderFactory
			.getInstance();
	private Parser parser = new ReversePolishParser();

	private Logger logger = Logger.getLogger("StatsBuilder");

	private boolean isDerived(String id)
	{
		return "derived".equals(getProperty(id, "type"));
	}

	public StatsBuilder()
	{
		super();
		super.getGlobalIds().add("globalAllStatsList");
	}

	@Override
	protected String getDefaultFileName()
	{
		return "config/stats";
	}

	public Object create(String id)
	{
		if ("globalAllStatsList".equals(id))
		{
			return new ArrayList();
		}
		String name = getProperty(id, "name");
		if (!isDerived(id))
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
		if ("globalAllStatsList".equals(id))
		{
			ArrayList list = (ArrayList) object;
			for (String statId : super.availableIds())
			{
				if (!"globalAllStatsList".equals(statId))
					list.add(scaffold.get(statId));
			}
			return;
		}
		StatisticId statId = (StatisticId) object;
		rutebaga.commons.Log.log(id);
		if (isDerived(id))
		{
			rutebaga.commons.Log.log("derived stat");
			DerivedStatisticId derId = (DerivedStatisticId) statId;
			ValueProvider<Stats> vp = getValueProvider(id, "value", scaffold);
			derId.setBase(vp);
		}
		Double init = getDouble(id, "default");
		if (init != null)
			statId.setInitialValue(init);
		Boolean hidden = getBoolean(id, "hidden");
		if (hidden != null)
			statId.setHidden(hidden);
	}

}
