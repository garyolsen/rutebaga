package rutebaga.view.drawer;

/**
 * 
 * The Attribute interface provides the ability for
 * ViewComponents to specify drawing information 
 * without having to directly call the Drawer's 
 * individual methods. These Attributes can be 
 * preserved within the Drawer so that Attributes can 
 * continue to effect any subsequent draw() calls 
 * to the Drawer.
  * @author Ryan
*/

public interface Attribute {

	/**
	 * Applies color, font, and clipping preferences embodied
	 * in this Attribute to the Drawer.	
	 * 
	 * @param drawer
	 */
	public void apply( Drawer drawer );
}
