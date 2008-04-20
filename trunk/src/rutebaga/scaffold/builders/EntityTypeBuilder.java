package rutebaga.scaffold.builders;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.scaffold.MasterScaffold;

public class EntityTypeBuilder extends InstanceBuilder
{
	private static String[] directionStrings = { "North", "NorthEast", "SouthEast", "South", "SouthWest", "NorthWest" }; 

	@Override
	protected String getDefaultFileName()
	{
		return "config/entity";
	}

	public Object create(String id)
	{
		return new EntityType();
	}

	@SuppressWarnings("unchecked")
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);
		EntityType type = (EntityType) object;
	
		type.setMovementSpeed((ValueProvider<Entity>) getValueProvider(id,
				"moveSpd", scaffold));
		
		String standingId = getProperty(id, "standing");
		String walkingId = getProperty(id, "walking");
		
		type.setStanding(getAnimatedAppearances(standingId, scaffold));
		type.setWalking(getAnimatedAppearances(walkingId, scaffold));
		type.setRadius(this.getInteger(id, "vRadius"));
		
		Object[] abilityTypes = getObjectArray(id, "abilities", "[\\s\\t]", scaffold);
		for(Object abType : abilityTypes)
		{
			type.getAbilityTypes().add(abType);
		}
	}
	
	private Appearance[][] getAnimatedAppearances(String name, MasterScaffold scaffold)
	{
		List<Appearance[]> list = new ArrayList<Appearance[]>();
		for(String dirString : directionStrings)
		{
			list.add(getAppearancesForDirection(name, dirString, scaffold));
		}
		return list.toArray(new Appearance[list.size()][]);
	}
	
	private Appearance[] getAppearancesForDirection(String name, String direction, MasterScaffold scaffold)
	{
		boolean stop = false;
		ArrayList<Appearance> list = new ArrayList<Appearance>();
		for(int i=1; !stop; i++)
		{
			String nStr = String.valueOf(i);
			if(nStr.length() == 1) nStr = "0" + nStr;
			String scaffId = name + direction + nStr;
			if(scaffold.contains(scaffId))
			{
				list.add(new Appearance((Image) scaffold.get(scaffId)));
			}
			else
				stop = true;
		}
		return list.toArray(new Appearance[list.size()]);
	}

}
