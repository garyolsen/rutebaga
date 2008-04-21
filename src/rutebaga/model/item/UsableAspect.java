package rutebaga.model.item;

import java.util.LinkedList;
import java.util.List;
import rutebaga.model.entity.EntityEffect;

public class UsableAspect
{
	List<EntityEffect> effects = new LinkedList<EntityEffect>();
	
	public UsableAspect() { }
	
	public void addEffect(EntityEffect effect) {
		effects.add(effect);
	}
	
	public List<EntityEffect> getUsableEffects() {
		return effects;
	}
	
}
