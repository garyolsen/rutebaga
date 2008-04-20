package rutebaga.view.game;

import java.util.Observable;
import java.util.Observer;

import rutebaga.controller.command.list.ElementalList;
import rutebaga.controller.command.list.ListElement;
import rutebaga.view.rwt.ButtonComponent;
import rutebaga.view.rwt.ViewComponent;
import rutebaga.view.rwt.ViewCompositeComponent;

public class ViewCompositeComponentWrapper extends ViewCompositeComponent implements Observer {

	private ElementalList list;
	
	public ViewCompositeComponentWrapper(ElementalList list ) {
		
		this.list = list;
		
		if(list.getObservable() != null)
			list.getObservable().addObserver(this);
		
		
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

	public void update(Observable o, Object arg) {
		updateChildren();
	}
}
