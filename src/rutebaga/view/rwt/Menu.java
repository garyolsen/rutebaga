package rutebaga.view.rwt;

import java.util.List;

public interface Menu {

	List<? extends MenuItem> getItems();
	
	MenuItem getSelectedItem();
}
