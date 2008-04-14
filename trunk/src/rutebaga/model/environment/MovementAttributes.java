package rutebaga.model.environment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rutebaga.model.map.TerrainType;

/**
 * Manages an {@link Instance}'s {@link MovementAttributeSet}s. These
 * MovementAttributeSets define the what TerrainType's an Instance is allowed
 * onto.
 * 
 * @author nicholasstamas
 * 
 */
public class MovementAttributes
{

	private Map<Object, MovementAttributeSet> movementSet = new HashMap<Object, MovementAttributeSet>();

	public boolean able(TerrainType terrain)
	{
		Iterator<MovementAttributeSet> masIterator = movementSet.values()
				.iterator();

		while (masIterator.hasNext())
		{
			MovementAttributeSet mas = masIterator.next();

			if (mas.able(terrain))
			{
				return true;
			}
		}

		return false;
	}

	public void add(MovementAttributeSet set, Object token)
	{
		movementSet.put(token, set);
	}

	public void release(Object token)
	{
		if (movementSet.containsKey(token))
		{
			movementSet.remove(token);
		}
	}

}
