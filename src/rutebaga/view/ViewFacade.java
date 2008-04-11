package rutebaga.view;


import rutebaga.commons.Vector;
import rutebaga.controller.Command;
import rutebaga.controller.ElementalList;
import rutebaga.view.rwt.ContextMenu;
import rutebaga.view.rwt.TextFieldListener;
import rutebaga.view.rwt.View;

public class ViewFacade {
	
	private View view;

	public void constructView( int width, int height ) {
		view = new View( width, height);
	}
	
	public void constructFullscreenView() {
		view = new View(800, 600);
		view.setFullscreen();
	}
	
	public void renderFrame() {
		if(view != null)
			view.renderFrame();
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
