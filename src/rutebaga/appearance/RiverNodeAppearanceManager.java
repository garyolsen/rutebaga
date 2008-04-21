package rutebaga.appearance;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.TileConverter;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;
import rutebaga.model.map.RiverNode;

public class RiverNodeAppearanceManager extends AppearanceManager {

	private AppearanceManagerDefinition[] directions;
	private RiverNode riverNode;
	
	private Integer currentOrdinal;
	private AppearanceManager currentBacking;
	
	public RiverNodeAppearanceManager(RiverNode riverNode) {
		this.riverNode = riverNode;
	}
	
	@Override
	public Appearance getAppearance() {
		if(currentOrdinal == null || currentOrdinal != getDirectionOrdinal())
		{
			currentOrdinal = getDirectionOrdinal();
			currentBacking = directions[getDirectionOrdinal()].make(riverNode);
		}
		return currentBacking.getAppearance();
	}

	@Override
	public void tick() {
		if(currentBacking != null)
			currentBacking.tick();
	}
	
	private int getDirectionOrdinal()
	{
		int total = directions.length;
		double offset = 0.75;
		TileConverter conv = riverNode.getEnvironment().getTileConvertor();
		Vector2D direction = conv.toRect(riverNode.getVector().plus(riverNode.getCoordinate())).minus(conv.toRect(riverNode.getCoordinate()));
		double angle = direction.getAngle() + (2 + offset) * Math.PI;
		angle -= 2 * Math.PI * (int) (angle * 0.5 / Math.PI);
		double proportion = angle * 0.5 / Math.PI;
		return (int) (proportion * total);
	}

	public void setDirections(AppearanceManagerDefinition[] directions) {
		this.directions = directions;
	}

}
