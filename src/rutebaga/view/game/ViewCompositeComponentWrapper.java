package rutebaga.view.game;

import rutebaga.controller.command.list.ElementalList;
import rutebaga.controller.command.list.ListElement;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.rwt.ButtonComponent;
import rutebaga.view.rwt.ViewComponent;
import rutebaga.view.rwt.ViewCompositeComponent;

public class ViewCompositeComponentWrapper extends ViewCompositeComponent {

	private ElementalList list;
	
	public ViewCompositeComponentWrapper(ElementalList list ) {
		
		this.list = list;
		
		updateChildren();
	}
	
	private void updateChildren() {
		for(ViewComponent vc : getChildren() ) {
			this.removeChild(vc);
		}
		
		for(ListElement element : list) {
			ButtonComponent button = new ButtonComponent(element.getLabel());
			button.setCommand(element.getCommand());
			
			this.addChild(button);
		}
	}

	@Override
	public void draw(Drawer draw) {
		if (list.hasChanged(this))
			updateChildren();
		super.draw(draw);
	}
	
	
}
