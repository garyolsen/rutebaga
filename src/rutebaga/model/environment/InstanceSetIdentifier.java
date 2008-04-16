package rutebaga.model.environment;

import java.util.HashSet;

import rutebaga.model.entity.Entity;
import rutebaga.model.item.Item;
import rutebaga.model.map.Tile;

public enum InstanceSetIdentifier
{
	ENTITY(new HashSet<Entity>()),
	ITEM(new HashSet<Item>()),
	EFFECT(new HashSet<Instance>()),
	TILE(new HashSet<Tile>());

	private final HashSet<? extends Instance> prototype;

	private InstanceSetIdentifier(HashSet<? extends Instance> prototype)
	{
		this.prototype = prototype;
	}

	HashSet<? extends Instance> getPrototype()
	{
		return prototype;
	}
}
