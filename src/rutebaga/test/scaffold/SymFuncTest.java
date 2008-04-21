package rutebaga.test.scaffold;

import java.util.HashMap;
import java.util.Map;

import rutebaga.commons.math.rel.EvaluatorVisitor;
import rutebaga.commons.math.rel.ParseTreeNode;
import rutebaga.commons.math.rel.ReversePolishParser;
import rutebaga.commons.math.rel.StringVisitor;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.scaffold.MasterScaffold;
import rutebaga.scaffold.builders.DefaultValueProviderFactory;

public class SymFuncTest
{
	public static void main(String ... args)
	{
		MasterScaffold s = TestScaffold.getInstance();
		s.build();
		
		Entity entity = ((EntityType) s.get("entityDefault")).create();
		
		ReversePolishParser parse = new ReversePolishParser();
		parse.setFactory(DefaultValueProviderFactory.getInstance());
		parse.setScaffold(s);
		
		String expression = "max 0 * - damage &VP_@entityStats_VP_stat_statArmorRating gate rand 0 30 &VP_@entityStats_VP_stat_statDefRating";
		
		ParseTreeNode root = parse.parse(expression);
		
		StringVisitor strV = new StringVisitor();
		root.accept(strV);
		System.out.println(strV.getString());
		
		Map<String, Double> symT = new HashMap<String, Double>(); 
		EvaluatorVisitor evalV = new EvaluatorVisitor(entity, symT);
		
		symT.put("damage", 3.0);
		
		root.accept(evalV);
		
		System.out.println(evalV.getValue());
		
		System.out.println(entity.getStats());
	}
}
