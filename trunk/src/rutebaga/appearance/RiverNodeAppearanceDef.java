package rutebaga.appearance;

import java.awt.Image;

import rutebaga.model.environment.Instance;
import rutebaga.model.environment.appearance.AnimatedAppearanceManager;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;
import rutebaga.model.environment.appearance.Appearance.Orientation;
import rutebaga.model.map.RiverNode;

public class RiverNodeAppearanceDef implements AppearanceManagerDefinition<RiverNode> {

	private AppearanceManagerDefinition[] backing;

	public AppearanceManagerDefinition[] getBacking() {
		return backing;
	}

	public void setBacking(AppearanceManagerDefinition[] backing) {
		this.backing = backing;
	}

	public RiverNodeAppearanceManager make(RiverNode riverNode)
	{
		RiverNodeAppearanceManager manager = new RiverNodeAppearanceManager(riverNode);
		manager.setDirections(backing);
		return manager;
	}


}
