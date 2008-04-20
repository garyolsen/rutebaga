package rutebaga.scaffold.builders;

import rutebaga.scaffold.MasterScaffold;
import rutebaga.model.map.GrassTerrain;
import rutebaga.model.map.MountainTerrain;
import rutebaga.model.map.TerrainType;
import rutebaga.model.map.Tile;
import rutebaga.model.map.WaterTerrain;

public class TerrainTypeBuilder extends ConfigFileBuilder
{
	
	@Override
	protected String getDefaultFileName() {
		return "config/terrains";
	}

	public Object create(String id) {
		TerrainType terrain;
		if (getProperty(id, "name").equals("Grass"))
		{
			return new GrassTerrain();
		}
		else if (getProperty(id, "name").equals("Water"))
		{
			return new WaterTerrain();
		}
		else if (getProperty(id, "name").equals("Mountain"))
		{
			return new MountainTerrain();
		}
		
		//default
		return new GrassTerrain();
	}

	public void initialize(String id, Object object, MasterScaffold scaffold) {
		// no initialization needed for TerrainTypes
	}

}
