package rutebaga.view.drawer;

import java.awt.Point;

public class TranslationAttribute implements Attribute {

	private Point translate;
	
	public TranslationAttribute(Point p) {
		this.translate = p;
	}

	public void apply(Drawer drawer) {
		drawer.setTranslation(translate);
	}
}
