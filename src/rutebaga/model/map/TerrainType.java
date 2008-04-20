package rutebaga.model.map;

/**
 * Defines the behavior of a type of Terrain. There should be TerrainTypes
 * analogous to
 * <ul>
 * <li>Mountains
 * <li>Water
 * <li>Grass
 * </ul>
 * 
 * @author Gary
 * 
 */
public class TerrainType {

	private String name;

	public TerrainType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
