package rutebaga.model.map;

import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.appearance.Appearance;

public class TileType<T extends Tile> extends ConcreteInstanceType<T> {

	private Appearance[] animation;
	private TerrainType terrain;

	public TerrainType getTerrain() {
		return terrain;
	}

	public void setTerrain(TerrainType terrain) {
		this.terrain = terrain;
	}

	public Appearance[] getAnimation() {
		return animation;
	}

	public void setAnimation(Appearance[] animation) {
		this.animation = animation;
	}

	@Override
	protected T create() {
		Tile tile = new Tile(this);
		tile.setTerrainType(terrain);
		return (T) tile;
	}

}
