package rutebaga.model.entity.npc;

import rutebaga.model.entity.*;

/**
 * NPCEntity instance class.
 * 
 * @author nicholasstamas
 *
 */
public class NPCEntity {
	
	private NPCEntityType type;
	
	private Entity target;
	private NPCBrain brain;
	
	protected NPCEntity(NPCEntityType type) {
		this.type = type;
	}
	
	public void tick() {
		type.tick(this);
	}
	
	public boolean targetInSight() {
		return type.targetInSight(this);
	}
	
	public boolean targetInRange() {
		return type.targetInRange(this);
	}
	
	public void setTarget( Entity target ) {
		this.target = target;
	}
	
	public void speak() {
		type.speak(this);
	}
	
	public void takeHostileGesture( Entity entity ) {
		type.takeHostileGesture(this, entity);
	}
	
	public void takeFriendlyGesture( Entity entity ) {
		type.takeFriendlyGesture(this, entity);
	}
	
	public void barter() {
		type.barter(this);
	}

	public Entity getTarget() {
		return target;
	}
	
	public void setBrain(NPCBrain brain)
	{
		this.brain = brain;
	}
	
	public NPCBrain getBrain()
	{
		return this.brain;
	}
}
