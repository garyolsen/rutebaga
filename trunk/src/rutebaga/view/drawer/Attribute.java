package rutebaga.view.drawer;

/**
 * @author Ryan
 * 
 * The Attribute interface provides the ability for
 * ViewComponents to specify drawing information 
 * without having to directly call the Drawer's 
 * individual methods. These Attributes can be 
 * preserved within the Drawer so that Attributes can 
 * continue to effect any subsequent draw(…) calls 
 * to the Drawer.
 */

public interface Attribute {

	public void apply( Drawer drawer );
}
