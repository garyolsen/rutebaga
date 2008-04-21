package rutebaga.model.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SkillLevelManager
{
	private Map<AbilityCategory, Double> levels = new ConcurrentHashMap<AbilityCategory, Double>();
	
	public double getLevel(AbilityCategory category)
	{
		if(!levels.containsKey(category))
		{
			levels.put(category, 0.0);
		}
		return levels.get(category);
	}
	
	public void assign(AbilityCategory category, double pts)
	{
		double newVal = getLevel(category) + pts;
		levels.put(category, newVal);
	}
}
