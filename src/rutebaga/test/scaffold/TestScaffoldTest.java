package rutebaga.test.scaffold;

import rutebaga.scaffold.MasterScaffold;
import rutebaga.test.scaffold.testbuilders.FemaleBuilder;
import rutebaga.test.scaffold.testbuilders.MaleBuilder;

public class TestScaffoldTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MasterScaffold scaffold = new MasterScaffold();
		scaffold.registerBuilder(new MaleBuilder());
		scaffold.registerBuilder(new FemaleBuilder());
		scaffold.build();
		System.out.println(scaffold.get("bob"));
		System.out.println(scaffold.get("frank"));
		System.out.println(scaffold.get("mark"));
		System.out.println(scaffold.get("carol"));
		System.out.println(scaffold.get("ruth"));
		System.out.println(scaffold.get("cindy"));
	}

}
