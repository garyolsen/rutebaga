package rutebaga.test.scaffold.testbuilders;

public class TestObject
{
	private String name;
	private int age;
	private TestObject friend;

	public TestObject(String name, int age)
	{
		super();
		this.name = name;
		this.age = age;
	}

	public TestObject(String name, int age, TestObject friend)
	{
		super();
		this.name = name;
		this.age = age;
		this.friend = friend;
	}

	public TestObject()
	{
	}

	public int getAge()
	{
		return age;
	}

	public TestObject getFriend()
	{
		return friend;
	}

	public String getName()
	{
		return name;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public void setFriend(TestObject friend)
	{
		this.friend = friend;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "TestObject named " + name + " that is " + age
				+ " years old and is friends with "
				+ (friend == null ? null : friend.name);
	}
}
