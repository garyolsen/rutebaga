package rutebaga.model.environment;

import java.util.HashSet;
import java.util.Set;

import rutebaga.model.environment.InstanceSetIdentifier;

public class InstanceFilter
{
	private Set<InstanceSetIdentifier> acceptedTypes = new HashSet<InstanceSetIdentifier>();
	
	public InstanceFilter(InstanceSetIdentifier ...identifiers)
	{
		for(InstanceSetIdentifier id : identifiers)
			acceptedTypes.add(id);
	}
	
	public InstanceSet filter(InstanceSet set)
	{
		return new ConcreteInstanceSet(set.getSets(), acceptedTypes);
	}
}
