package rutebaga.test.scaffold.testbuilders;

import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;

public class MaleBuilder implements Builder
{
	private static String[] ids =
	{ "frank", "bob", "mark" };

	private static enum Types
	{
		FRANK, BOB, MARK;
	}

	public String[] availableIds()
	{
		return ids;
	}

	public Object create(String id)
	{
		return new TestObject();
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		TestObject cast = (TestObject) object;
		switch (Types.valueOf(id.toUpperCase()))
		{
		case FRANK:
			cast.setName("Frank");
			cast.setAge(20);
			cast.setFriend((TestObject) scaffold.get("carol"));
			break;
		case BOB:
			cast.setName("Bob");
			cast.setAge(19);
			cast.setFriend((TestObject) scaffold.get("frank"));
			break;
		case MARK:
			cast.setName("Mark");
			cast.setAge(21);
			cast.setFriend((TestObject) scaffold.get("mark"));
			break;
		}
	}

}
