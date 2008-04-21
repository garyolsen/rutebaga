package rutebaga.scaffold.builders;

import java.awt.Image;

import rutebaga.appearance.AppearanceManagerDefinition;
import rutebaga.appearance.RiverNodeAppearanceDef;
import rutebaga.appearance.StaticAppearanceDef;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.Appearance.Orientation;
import rutebaga.model.map.RiverType;
import rutebaga.scaffold.MasterScaffold;

public class RiverBuilder extends ConfigFileBuilder {

	@Override
	protected String getDefaultFileName() {
		return "config/river";
	}

	public Object create(String id) {
		RiverType riverType = new RiverType();
		return riverType;
	}

	public void initialize(String id, Object object, MasterScaffold scaffold) {
		RiverType riverType = (RiverType) object;
		Vector2D location = getVector2D(id, "location");
		double force = getDouble(id, "force");
		String[] locationVectors = getInnerList(id, "nodes").toArray(new String[0]);
		Vector2D[] locations = new Vector2D[locationVectors.length]; 
		int i = 0;
		for (String s : locationVectors)
		{
			locations[i] = getVector2D(s);
			i++;
		}
		String[] images = getInnerList(id, "images").toArray(new String[0]);
		AppearanceManagerDefinition[] appearances = new AppearanceManagerDefinition[images.length]; 
		for (int j = 0; j < images.length; j++)
		{
			AppearanceManagerDefinition managerDef = AppearanceDefFactory.getInstance().get(images[j], scaffold);
			appearances[j] = managerDef;
		}
		RiverNodeAppearanceDef def = new RiverNodeAppearanceDef();
		def.setBacking(appearances);
		riverType.setAppearanceDef(def);
		riverType.setLocation(location);
		riverType.setForce(force);
		riverType.setLocations(locations);
	}

}
