package rutebaga.model.map;

/**
 * Defines the behavior of a type of Terrain. There should be TerrainTypes
 * analogous to
 * <ul><li>Mountains<li>Water<li>Grass</ul> 
 * @author Gary
 * 
 */
public interface TerrainType {

	/**
	 * Returns a unique String that describes this TerrainType.
	 * @return The name of this TerrainType.
	 */
	public String getName();

}
