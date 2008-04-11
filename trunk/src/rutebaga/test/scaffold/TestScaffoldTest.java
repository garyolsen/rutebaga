package rutebaga.test.scaffold;

import rutebaga.scaffold.MasterScaffold;
import rutebaga.test.scaffold.testbuilders.MaleBuilder;

public class TestScaffoldTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MasterScaffold scaffold = new MasterScaffold();
		MaleBuilder builder = new MaleBuilder();
		scaffold.registerBuilder(builder);
		scaffold.build();
		System.out.println(scaffold.get("bob"));
		System.out.println(scaffold.get("frank"));
		System.out.println(scaffold.get("mark"));
	}

}
