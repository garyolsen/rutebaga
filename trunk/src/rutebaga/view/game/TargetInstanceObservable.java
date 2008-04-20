package rutebaga.view.game;

import java.util.ArrayList;
import java.util.List;

import rutebaga.model.environment.Instance;

public class TargetInstanceObservable {
	
	private List<TargetInstanceObserver> observers = new ArrayList<TargetInstanceObserver>();
	
	private boolean hasChanged = false;

	public void addObserver( TargetInstanceObserver observer ) {
		observers.add(observer);
	}
	
	public void notifyAllObservers(Instance updated) {
		if(hasChanged())
		for(TargetInstanceObserver observer : observers)
			observer.update(this, updated);
	}
	
	public void setChanged() {
		hasChanged = true;
	}
	
	public boolean hasChanged() {
		return hasChanged;
	}
	
	
}
