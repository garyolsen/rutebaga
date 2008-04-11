package rutebaga.scaffold;

public interface Builder
{
	Object create(String id);
	void initialize(String id, Object object, MasterScaffold scaffold);
}
