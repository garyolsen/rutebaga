package rutebaga.model.map;

import java.util.Iterator;

import rutebaga.appearance.AppearanceManagerDefinition;
import rutebaga.appearance.RiverNodeAppearanceDef;
import rutebaga.appearance.RiverNodeAppearanceManager;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;

public class RiverType<T extends River> {

	private Vector2D location;
	private Vector2D[] locations;
	private double force;
	private RiverNodeAppearanceDef appearanceDef;

	protected T create() {
		return (T) new River(this);
	}

	public T make() {
		T t = create();
		initialize(t);
		return t;
	}

	public void initialize(T river) {
		river.setAppDef(appearanceDef);
		river.setLocation(location);
		river.setForce(force);
		for (Vector2D nodeLocation : locations) {
			river.addNodeAtTail(nodeLocation);
		}
	}

	public Vector2D getLocation() {
		return location;
	}

	public void setLocation(Vector2D location) {
		this.location = location;
	}

	public Vector2D[] getLocations() {
		return locations;
	}

	public void setLocations(Vector2D[] locations) {
		this.locations = locations;
	}

	public double getForce() {
		return force;
	}

	public void setForce(double force) {
		this.force = force;
	}

	public RiverNodeAppearanceDef getAppearanceDef() {
		return appearanceDef;
	}

	public void setAppearanceDef(RiverNodeAppearanceDef appearanceDef) {
		this.appearanceDef = appearanceDef;
	}

}
