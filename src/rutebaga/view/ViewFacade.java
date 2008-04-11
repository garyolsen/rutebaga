package rutebaga.view;

import rutebaga.commons.Vector;
import rutebaga.controller.Command;
import rutebaga.controller.ElementalList;
import rutebaga.view.rwt.ContextMenu;
import rutebaga.view.rwt.TextFieldListener;

public class ViewFacade {

	public void constructView( int width, int height ) {
		
	}
	
	public void renderFrame() {
		
	}
	
	public void createTitleScreen( ElementalList list ) {
		
	}
	
	public void createAvatarCreationScreen( TextFieldListener listener, ElementalList list, Command accept, Command cancel ) {
		
	}
	
	public ContextMenu createRootContextMenu( ElementalList list, Vector vector ) {
		return null;
	}
	
	public ContextMenu createSubContextMenu( ElementalList list ) {
		return null;
	}
	
	public ContextMenu createScrollMenu( ElementalList list, int pageSize ) {
		return null;
	}
	
	public ContextMenu createDialogMenu( ElementalList list, Vector vector ) {
		return null;
	}
	
	public void createWarningBox( ElementalList list ) {
		
	}
	
	public void closeContextMenu( ContextMenu menu ) {
		
	}
}
