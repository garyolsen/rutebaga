package rutebaga.controller.command.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import rutebaga.model.entity.Ability;

public class AbilityListElementSource implements ListElementSource<Ability> {

	private Collection<Ability> abilities;
	private int feasibleCount = 0;
	
	public AbilityListElementSource(Collection<Ability> abilities) {
		ArrayList<Ability> doNotWant = new ArrayList<Ability>();
		for (Ability ability: abilities)
			if (!ability.exists())
				doNotWant.add(ability);
			else if (ability.isFeasible())
				++feasibleCount;
		this.abilities = new ArrayList<Ability>();
		this.abilities.addAll(abilities);
		this.abilities.removeAll(doNotWant);
	}
	
	public int contentSize() {
		return abilities.size();
	}

	public String getLabel() {
		return "Abilities";
	}

	public boolean hasChanged(Object object) {
		int oldFeasibleCount = feasibleCount;
		feasibleCount = 0;
		for (Ability ability: abilities)
			if (ability.isFeasible())
				++feasibleCount;
		return (oldFeasibleCount != feasibleCount);
	}

	public Iterator<Ability> iterator() {
		return abilities.iterator();
	}

}
