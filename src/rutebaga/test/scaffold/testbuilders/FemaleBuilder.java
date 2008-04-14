package rutebaga.test.scaffold.testbuilders;

import rutebaga.scaffold.Builder;
import rutebaga.scaffold.MasterScaffold;

public class FemaleBuilder implements Builder
{
	private static String[] ids =
	{ "carol", "ruth", "cindy" };

	private static enum Types
	{
		CAROL, RUTH, CINDY;
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
		case CAROL:
			cast.setName("Carol");
			cast.setAge(22);
			cast.setFriend((TestObject) scaffold.get("frank"));
			break;
		case RUTH:
			cast.setName("Ruth");
			cast.setAge(23);
			cast.setFriend((TestObject) scaffold.get("cindy"));
			break;
		case CINDY:
			cast.setName("Cindy");
			cast.setAge(24);
			cast.setFriend((TestObject) scaffold.get("carol"));
			break;
		}
	}

}
