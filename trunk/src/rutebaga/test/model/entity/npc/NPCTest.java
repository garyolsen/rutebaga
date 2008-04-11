package rutebaga.test.model.entity.npc;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCEntityType;

public class NPCTest {

	public static void main(String args[])
	{
		NPCEntityType npcType = new NPCEntityType();
		NPCEntity npc = npcType.make();
		
		npc.speak();
		npc.barter();
		
		npc.takeHostileGesture(new Entity());
		
		npc.speak();
		npc.barter();
		
		npc.takeFriendlyGesture(new Entity());
		
		npc.speak();
		npc.barter();
	}
}
