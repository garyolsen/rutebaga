package rutebaga.scaffold.builders;

import rutebaga.model.entity.Team;
import rutebaga.scaffold.MasterScaffold;

public class TeamBuilder extends ConfigFileBuilder {

	@Override
	protected String getDefaultFileName() {
		return "config/team";
	}

	public Object create(String id) {
		return new Team(getProperty(id, "name"));
	}

	public void initialize(String id, Object object, MasterScaffold scaffold) {
		// no initialization necessary
	}

}
