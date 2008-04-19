package rutebaga.test.scaffold;

import java.util.logging.Level;
import java.util.logging.Logger;

import rutebaga.commons.math.ValueProvider;
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
		s.build();
		
		Stats stats = new ConcreteStats(null);
		StatisticId defensiveRating = (StatisticId) s.get("statDefRating");
		System.out.println(stats.getValue(defensiveRating));
		
		Entity entity = ((EntityType) s.get("entityDefault")).makeInstance();
		System.out.println(entity.getMovementSpeed());
	}

}
