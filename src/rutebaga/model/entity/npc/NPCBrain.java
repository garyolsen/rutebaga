package rutebaga.model.entity.npc;

/**
 * Interface for an NPCBrain.
 * 
 * @author nicholasstamas
 *
 */
public interface NPCBrain {

	public void tick( NPCEntity npc );
	public void speak( NPCEntity npc );
	public void barter( NPCEntity npc );
	public void makeHostile( NPCEntity npc );
	public void makeFriendly( NPCEntity npc );
	
}
