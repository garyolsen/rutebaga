package rutebaga.controller.command.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import rutebaga.model.entity.Ability;
import rutebaga.model.environment.Instance;

public class AbilityListElementSource implements ListElementSource<Ability>
{

	private Collection<Ability> abilities;
	private int feasibleCount = 0;
	private TargetSource targetSource;
	
	private boolean abilityIsOk(Ability ability)
	{
		return ability.isFeasible() && ability.exists() && ability.canActOn(targetSource.getTarget());
	}

	public AbilityListElementSource(Collection<Ability> abilities, TargetSource targetSource)
	{
		this.targetSource = targetSource;
		ArrayList<Ability> doNotWant = new ArrayList<Ability>();
		for (Ability ability : abilities)
			if (!abilityIsOk(ability))
				doNotWant.add(ability);
			else
				++feasibleCount;
		this.abilities = new ArrayList<Ability>();
		this.abilities.addAll(abilities);
		this.abilities.removeAll(doNotWant);
	}

	public int contentSize()
	{
		return feasibleCount;
	}

	public String getLabel()
	{
		return "Abilities";
	}

	public boolean hasChanged(Object object)
	{
		int oldFeasibleCount = feasibleCount;
		feasibleCount = 0;
		for (Ability ability : abilities)
			if (abilityIsOk(ability))
				++feasibleCount;
		return (oldFeasibleCount != feasibleCount);
	}

	public Iterator<Ability> iterator()
	{
		return new AbilitiesIterator();
	}

	private class AbilitiesIterator implements Iterator<Ability>
	{
		private Iterator<Ability> backing = abilities.iterator();
		private Ability current;
		
		{
			next();
		}

		public boolean hasNext()
		{
			return current != null;
		}

		public Ability next()
		{
			Ability rval = current;
			current = null;
			if (backing.hasNext())
				do
				{
					current = backing.next();
				}
				while (backing.hasNext()
						&& !abilityIsOk(current));
			if(current != null && !abilityIsOk(current))
				current = null;
			return rval;
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}

	}

}
