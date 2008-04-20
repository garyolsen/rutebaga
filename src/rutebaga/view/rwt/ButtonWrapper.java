package rutebaga.view.rwt;

import rutebaga.controller.command.list.ListElement;
import rutebaga.view.drawer.Drawer;
import rutebaga.view.rwt.*;

public class ButtonWrapper extends ButtonComponent {

	private ListElement element;
	
	public ButtonWrapper(ListElement element) {
		super(element.getLabel());
		this.element = element;
		
		this.setCommand(element.getCommand());
	}
	
	public void draw(Drawer draw) {
		this.setCommand(element.getCommand());
		this.setLabel(element.getLabel());
		
		super.draw(draw);
	}
	
}
