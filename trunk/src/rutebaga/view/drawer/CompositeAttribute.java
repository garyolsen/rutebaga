package rutebaga.view.drawer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CompositeAttribute implements Attribute{

	private Set<Attribute> attributes;
	
	/**
	 * Constructs a new empty CompositeAttribute.
	 */
	public CompositeAttribute() {
		attributes = new HashSet<Attribute>();
	}
	
	
	public void apply(Drawer drawer) {
		for(Attribute attr : attributes)
			attr.apply( drawer );
	}
	
	/**
	 * Adds an Attribute to this CompositeAttribute.
	 * @param attr The Attribute to add to this CompositeAttribute.
	 */
	public void addAttribute( Attribute attr ) {
		attributes.add(attr);
	}
	
	/**
	 * Removes an Attribute from this CompositeAttribute.
	 * @param attr The Attribute to remove from this CompositeAttribute.
	 */
	public void removeAttribute( Attribute attr ) {
		attributes.remove(attr);
	}
	
	/**
	 * Returns all the Attributes stored in this CompositeAttribute.
	 * @return A set of Attributes.
	 */
	public Set<Attribute> getAttributes() {
		return Collections.unmodifiableSet( attributes );
	}
	                                        

}
