package rutebaga.controller.command.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import rutebaga.model.entity.Ability;

public class AbilityListElementSource implements ListElementSource<Ability> {

	private Collection<Ability> abilities;
	
	public AbilityListElementSource(Collection<Ability> abilities) {
		this.abilities = new ArrayList<Ability>();
		this.abilities.addAll(abilities);
		ArrayList<Ability> doNotWant = new ArrayList<Ability>();
		for (Ability ability: abilities)
			if (!ability.exists())
				doNotWant.add(ability);
		this.abilities.removeAll(doNotWant);
	}
	
	public int contentSize() {
		return abilities.size();
	}

	public String getLabel() {
		return "Abilities";
	}

	public boolean hasChanged(Object object) {
		return false;
	}

	public Iterator<Ability> iterator() {
		return abilities.iterator();
	}

}
