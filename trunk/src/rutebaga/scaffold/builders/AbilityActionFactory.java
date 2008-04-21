package rutebaga.scaffold.builders;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.rel.ReversePolishParser;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.entity.Damage;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.ability.DamageAction;
import rutebaga.scaffold.MasterScaffold;

public class AbilityActionFactory
{
	private static Pattern pattern = ConfigFileBuilder.pattern;
	private static int NAME_IDX = ConfigFileBuilder.NAME_GP;
	private static int EXPR_IDX = ConfigFileBuilder.VALUE_GP;
	
	public AbilityAction get(String description, MasterScaffold scaffold)
	{
		System.out.println(description);
		Matcher m = pattern.matcher(description);
		m.matches();
		String type = m.group(NAME_IDX);
		String expr = m.group(EXPR_IDX);
		m = null;
		
		if("damage".equals(type))
		{
			m = pattern.matcher(expr);
			m.matches();
			String damageId = m.group(NAME_IDX);
			String damageExpr = m.group(EXPR_IDX);
			
			Damage damage = (Damage) scaffold.get(damageId);
			
			ValueProvider<Entity> vp = DefaultValueProviderFactory.getInstance().parse(damageExpr, scaffold);
			
			return new DamageAction(vp, damage);
		}
		
		return null;
	}
}
