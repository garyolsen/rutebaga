package rutebaga.scaffold.builders;

import java.awt.Image;
import java.util.ArrayList;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.appearance.AnimatedAppearanceManager;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.Appearance.Orientation;
import rutebaga.model.map.TerrainType;
import rutebaga.model.map.Tile;
import rutebaga.model.map.TileType;
import rutebaga.scaffold.MasterScaffold;

public class TileTypeBuilder extends InstanceBuilder
{

	@Override
	protected String getDefaultFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(String id) {
		return new TileType();
	}
	
	@SuppressWarnings("unchecked")
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);
		
		TileType type = (TileType) object;
		String animationId = getProperty(id, "animation");
		
		type.setAnimation(getAppearances(animationId, scaffold));
		type.setTerrain((TerrainType) getObjectFor(id, "terrain", scaffold));
	}
	
	private Appearance[] getAppearances(String name, MasterScaffold scaffold)
	{
		boolean stop = false;
		ArrayList<Appearance> list = new ArrayList<Appearance>();
		for(int i=1; !stop; i++)
		{
			String nStr = String.valueOf(i);
			if(nStr.length() == 1) nStr = "0" + nStr;
			String scaffId = name  + nStr;
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
