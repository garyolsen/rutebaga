package rutebaga.scaffold.builders;

import rutebaga.model.entity.MountType;
import rutebaga.scaffold.MasterScaffold;

public class MountTypeBuilder extends EntityTypeBuilder {

	@Override
	public Object create(String id) {
		return new MountType();
	}

	@Override
	public void initialize(String id, Object object, MasterScaffold scaffold) {
		super.initialize(id, object, scaffold);
		
		MountType mountType = (MountType) object;
	}

}
