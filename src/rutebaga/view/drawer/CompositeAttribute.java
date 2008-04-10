package rutebaga.view.drawer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CompositeAttribute implements Attribute{

	public Set<Attribute> attributes;
	
	public CompositeAttribute() {
		attributes = new HashSet<Attribute>();
	}
	
	
	public void apply(Drawer drawer) {
		for(Attribute attr : attributes)
			attr.apply( drawer );
	}
	
	public void addAttribute( Attribute attr ) {
		attributes.add(attr);
	}
	
	public void removeAttribute( Attribute attr ) {
		attributes.remove(attr);
	}
	
	public Set<Attribute> getAttributes() {
		return Collections.unmodifiableSet( attributes );
	}
	                                        

}
