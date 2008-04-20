package rutebaga.scaffold.builders;

import rutebaga.model.map.TerrainType;
import rutebaga.model.map.TileType;
import rutebaga.scaffold.MasterScaffold;

public class TileTypeBuilder extends InstanceBuilder
{

	@Override
	protected String getDefaultFileName() {
		return "config/tiles";
	}

	public Object create(String id) {
		return new TileType();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);
		
		TileType type = (TileType) object;

		type.setTerrain((TerrainType) getObjectFor(id, "terrain", scaffold));
	}
}
