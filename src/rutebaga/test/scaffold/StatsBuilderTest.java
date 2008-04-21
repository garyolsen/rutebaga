package rutebaga.test.scaffold;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import rutebaga.commons.Log;
import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.rel.StringVisitor;
import rutebaga.game.builders.CustomAbilityBuilder;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Damage;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.stats.ConcreteStats;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.entity.stats.Stats;
import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.AbilityTypeBuilder;
import rutebaga.scaffold.builders.DefaultBuilder;
import rutebaga.scaffold.builders.StatsBuilder;

public class StatsBuilderTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Builder b = new DefaultBuilder();
		MasterScaffold s = new MasterScaffold();
		s.registerBuilder(b);
//		s.registerBuilder(new CustomAbilityBuilder());
		s.registerBuilder(new AbilityTypeBuilder());
		s.build();

		SortedSet<String> keys = new TreeSet<String>();
		keys.addAll(s.getKeys());

		for (String key : keys)
		{
			Object obj = s.get(key);
			if(obj == null)
				continue;
			System.out.println(key + "\t\t" + obj.getClass().getSimpleName()
					+ "\t\t" + obj);
		}

		Damage damage = (Damage) s.get("dmgTpDemi");
		StringVisitor v = new StringVisitor();
		damage.getMagnitudeProvider().getTreeRoot().accept(v);
		System.out.println(v.getString());
		
		List<StatisticId> list = (List<StatisticId>) s.get("globalAllStatsList");
		for(Object id : list.toArray())
		{
			System.out.println(id);
		}
	}

}
