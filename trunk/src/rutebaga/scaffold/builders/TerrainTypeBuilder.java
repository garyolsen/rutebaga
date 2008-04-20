package rutebaga.scaffold.builders;

import rutebaga.scaffold.MasterScaffold;
import rutebaga.model.environment.Instance;
import rutebaga.model.map.TerrainType;


public class TerrainTypeBuilder extends ConfigFileBuilder
{
	
	@Override
	protected String getDefaultFileName() {
		return "config/terrains";
	}

	public Object create(String id) {
		return new TerrainType(getProperty(id, "name"));
	}

	public void initialize(String id, Object object, MasterScaffold scaffold) {
		// no initialization needed for TerrainTypes
	}

}
