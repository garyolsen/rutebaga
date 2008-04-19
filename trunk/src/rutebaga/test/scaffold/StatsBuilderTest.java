package rutebaga.test.scaffold;

import java.util.logging.Level;
import java.util.logging.Logger;

import rutebaga.model.entity.stats.ConcreteStats;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.entity.stats.Stats;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.StatsBuilder;

public class StatsBuilderTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		StatsBuilder b = new StatsBuilder();
		MasterScaffold s = new MasterScaffold();
		s.registerBuilder(b);
		s.build();
		
		Stats stats = new ConcreteStats(null);
		StatisticId defensiveRating = (StatisticId) s.get("statDefRating");
		System.out.println(stats.getValue(defensiveRating));
	}

}
