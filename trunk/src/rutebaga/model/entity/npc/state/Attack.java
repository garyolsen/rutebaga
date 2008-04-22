package rutebaga.model.entity.npc.state;

import java.util.Collection;
import java.util.Random;

import rutebaga.model.entity.Ability;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.npc.NPCEntity;
import rutebaga.model.entity.npc.NPCState;

/**
 * @author nicholasstamas
 * 
 */
public class Attack extends NPCState
{

	Random rand = new Random();
	int wait;
	
	@Override
	public NPCState barter(NPCEntity npc)
	{
		return this;
	}

	@Override
	public NPCState makeFriendly(NPCEntity npc)
	{
		return NPCState.wander;
	}

	@Override
	public NPCState makeHostile(NPCEntity npc)
	{
		return this;
	}

	@Override
	public NPCState speak(NPCEntity npc, Entity entity)
	{
		entity.recieveSpeech(npc, "I'm attacking you! Be very afraid!");
		return this;
	}

	@Override
	public NPCState tick(NPCEntity npc)
	{
		if (npc.isDead())
			return NPCState.dead;
		else
		{
			if (npc.targetInRange() && npc.targetInSight())
			{
				if (rand.nextDouble() < 0.01)
				{
					Collection<Ability> abilities = npc.getAbilities();
					int index = rand.nextInt(npc.getAbilities().size());
					int counter = 0;
					for ( Ability ability : abilities ) {
						if (counter == index) {
							if ( ability.isFeasible() )
							{
								ability.act(npc.getTarget());
								break;
							}
						}
						counter++;
					}
				}
			return this;
			}
		}
		return NPCState.chase;
	}

}
