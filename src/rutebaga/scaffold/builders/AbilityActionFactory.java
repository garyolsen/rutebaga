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
	private static Pattern pattern = Pattern.compile("[\\s\\t]*(\\w+)[\\s\\t]*(.*)");
	private static int NAME_IDX = 1;
	private static int EXPR_IDX = 2;
	
	private static Pattern damage_pattern = Pattern.compile("(\\w+)[\\s\\t]*(.*)");
	private static int DAMAGE_DAMAGE_ID = 1;
	private static int DAMAGE_EXPR = 2;
	
	public AbilityAction get(String description, MasterScaffold scaffold)
	{
		Matcher m = pattern.matcher(description);
		String type = m.group(NAME_IDX);
		String expr = m.group(EXPR_IDX);
		m = null;
		
		if("damage".equals(type))
		{
			m = damage_pattern.matcher(expr);
			String damageId = m.group(DAMAGE_DAMAGE_ID);
			String damageExpr = m.group(DAMAGE_EXPR);
			
			Damage damage = (Damage) scaffold.get(damageId);
			
			ValueProvider<Entity> vp = DefaultValueProviderFactory.getInstance().get(damageExpr, scaffold);
			
			return new DamageAction(vp, damage);
		}
		
		return null;
	}
}
