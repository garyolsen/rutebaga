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
	private int disabledCount = 0;
	private TargetSource targetSource;

	private boolean abilityIsOk(Ability ability)
	{
		return ability.isFeasible() && ability.exists()
				&& ability.canActOn(targetSource.getTarget());
	}
	
	private boolean includeInList(Ability ability)
	{
		return abilityIsDisabled(ability) || abilityIsOk(ability);
	}

	private boolean abilityIsDisabled(Ability ability)
	{
		return ability.exists() && ability.canActOn(targetSource.getTarget())
				&& !ability.isFeasible();
	}

	private void count(Ability ability)
	{
		if (!abilityIsOk(ability))
		{
			if (abilityIsDisabled(ability))
				disabledCount++;
		}
		else
			++feasibleCount;
	}

	public AbilityListElementSource(Collection<Ability> abilities,
			TargetSource targetSource)
	{
		this.targetSource = targetSource;
		ArrayList<Ability> doNotWant = new ArrayList<Ability>();
		for (Ability ability : abilities)
		{
			count(ability);
			if (!includeInList(ability))
				doNotWant.add(ability);
		}
		this.abilities = new ArrayList<Ability>();
		this.abilities.addAll(abilities);
		this.abilities.removeAll(doNotWant);
	}

	public int contentSize()
	{
		return feasibleCount + disabledCount;
	}

	public String getLabel()
	{
		return "Abilities";
	}

	public boolean hasChanged(Object object)
	{
		int oldFeasibleCount = feasibleCount;
		int oldDisabledCount = disabledCount;
		feasibleCount = 0;
		disabledCount = 0;
		for (Ability ability : abilities)
			count(ability);
		return (oldFeasibleCount != feasibleCount || oldDisabledCount != disabledCount);
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
						&& !includeInList(current));
			if (current != null && !includeInList(current))
				current = null;
			return rval;
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}

	}

}
