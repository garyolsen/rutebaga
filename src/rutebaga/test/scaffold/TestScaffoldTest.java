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
		rutebaga.commons.Log.log(scaffold.get("bob"));
		rutebaga.commons.Log.log(scaffold.get("frank"));
		rutebaga.commons.Log.log(scaffold.get("mark"));
		rutebaga.commons.Log.log(scaffold.get("carol"));
		rutebaga.commons.Log.log(scaffold.get("ruth"));
		rutebaga.commons.Log.log(scaffold.get("cindy"));
	}

}
