package rutebaga.test.scaffold;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import rutebaga.commons.Log;
import rutebaga.commons.math.ValueProvider;
import rutebaga.game.builders.CustomAbilityBuilder;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.stats.ConcreteStats;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.entity.stats.Stats;
import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;
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
		s.registerBuilder(new CustomAbilityBuilder());
		s.build();
		
		SortedSet<String> keys = new TreeSet<String>();
		keys.addAll(s.getKeys());
		
		for(String key : keys)
		{
			System.out.println(key + "\t\t" + s.get(key));
		}
		
		Stats stats = new ConcreteStats(null);
		StatisticId defensiveRating = (StatisticId) s.get("statDefRating");
		rutebaga.commons.Log.log(stats.getValue(defensiveRating));
		
		Entity entity = ((EntityType<?>) s.get("entityDefault")).makeInstance();
		rutebaga.commons.Log.log(entity.getMovementSpeed());
		
		System.out.println(entity.getAbilities().size());
	}

}
