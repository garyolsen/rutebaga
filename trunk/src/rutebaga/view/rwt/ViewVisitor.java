package rutebaga.view.rwt;

public interface ViewVisitor {

	public void visit(ViewComponent vc);
	public void visit(ViewCompositeComponent vcc);
	
}
