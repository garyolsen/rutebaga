package rutebaga.view.rwt;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rutebaga.view.drawer.Drawer;

public class ViewCompositeComponent extends ViewComponent{

	public Set<ViewComponent> components;
	
	public ViewCompositeComponent() {
		components = new HashSet<ViewComponent>();
	}
	
	public void addChild(ViewComponent component ) {
		components.add( component );
	}
	
	public ViewComponent removeChild( ViewComponent component ) {
		return components.remove( component ) ? component : null;
	}
	
	public Set<ViewComponent> getChildren() {
		return Collections.unmodifiableSet( components );
	}

	public void draw(Drawer draw) {
		for(ViewComponent component : components)
			component.draw( draw );
	}
	
	
}
