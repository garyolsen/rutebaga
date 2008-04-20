package rutebaga.model.map;

import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.appearance.AnimatedAppearanceManager;
import rutebaga.model.environment.appearance.Appearance;

public class TileType<T extends Tile> extends ConcreteInstanceType<T> {

	private TerrainType terrain;

	public TerrainType getTerrain() {
		return terrain;
	}

	public void setTerrain(TerrainType terrain) {
		this.terrain = terrain;
	}

	@Override
	protected T create() {
		Tile tile = new Tile(this);
		tile.setTerrainType(terrain);
		return (T) tile;
	}

}
