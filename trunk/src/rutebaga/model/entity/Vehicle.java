package rutebaga.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Vehicle
{

	private List<ReversibleEntityEffect> entityEffects = new ArrayList<ReversibleEntityEffect>();

	public List<ReversibleEntityEffect> getEntityEffects() {
		return entityEffects;
	}
	
	
	public double getMass() {
		return 0.0;
	}
}
