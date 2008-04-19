package rutebaga.test.model.stat;

import rutebaga.commons.UIDProvider;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.stats.ConcreteStats;
import rutebaga.model.entity.stats.DerivedStatisticId;
import rutebaga.model.entity.stats.StatModification;
import rutebaga.model.entity.stats.StatValueProvider;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.entity.stats.Stats;

public class StatsTest
{

	public static void main(String[] args)
	{
		StatisticId agility = new StatisticId("Agility");
		agility.setInitialValue(10.0);
		StatisticId level = new StatisticId("Level");
		level.setInitialValue(4.0);
		
		ValueProvider<Stats> defRatingVP = new StatValueProvider(agility);
		defRatingVP = defRatingVP.times(4).plus(new StatValueProvider(level));
		StatisticId defRating = new DerivedStatisticId("Defensive Rating", defRatingVP);
		
		Stats stats = new ConcreteStats(null);
		
		rutebaga.commons.Log.log(stats.getValue(defRating));
		
		stats.modifyStat(agility, 5);
		
		rutebaga.commons.Log.log(stats.getValue(defRating));
		
		StatModification reversible = new StatModification(level, 1.0);
		
		Object id;
		id = UIDProvider.getUID();
		stats.modifyStat(reversible, id);
		
		rutebaga.commons.Log.log(stats.getValue(defRating));
		
		stats.undo(id);
		
		rutebaga.commons.Log.log(stats.getValue(defRating));
		
		id = UIDProvider.getUID();
		stats.modifyStat(new StatModification(defRating, 5.0), id);
		
		rutebaga.commons.Log.log(stats.getValue(defRating));
		
		stats.undo(id);
		
		rutebaga.commons.Log.log(stats.getValue(defRating));
	}

}
