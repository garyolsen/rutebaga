package rutebaga.view.rwt;

import rutebaga.view.drawer.Drawer;

public abstract class ComponentDecorator extends ViewComponent {
	
	private final ViewComponent decorated;

	public ComponentDecorator( ViewComponent decorated ) {
		this.decorated = decorated;
	}
	
	public void draw(Drawer draw) {
		decorated.draw( draw );
	}

}
