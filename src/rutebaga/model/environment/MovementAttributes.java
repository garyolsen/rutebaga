package rutebaga.model.environment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rutebaga.commons.UIDProvider;
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

	public MovementAttributes()
	{
		
	}
	
	public MovementAttributes(boolean asBlacklist)
	{
		MovementAttributeSet set = asBlacklist ? new BlackList() : new WhiteList();
		add(set);
	}
	
	public boolean able(TerrainType terrain)
	{
		// if no MovementAttributeSets are in collection, return true
		if ( movementSet.isEmpty() )
			return true;
		
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

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("MovementAttributes:\n");
		for(MovementAttributeSet set : movementSet.values())
		{
			sb.append(set).append("\t\t");
		}
		sb.append("\n");
		return sb.toString();
	}

	public void add(MovementAttributeSet set, Object token)
	{
		movementSet.put(token, set);
	}
	
	public void add(MovementAttributeSet set)
	{
		movementSet.put( UIDProvider.getUID(), set );
	}

	public void release(Object token)
	{
		if (movementSet.containsKey(token))
		{
			movementSet.remove(token);
		}
	}

}
