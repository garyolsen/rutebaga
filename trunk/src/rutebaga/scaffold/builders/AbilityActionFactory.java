package rutebaga.scaffold.builders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rutebaga.commons.math.ValueProvider;
import rutebaga.commons.math.rel.ReversePolishParser;
import rutebaga.model.entity.AbilityAction;
import rutebaga.model.entity.Damage;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.ability.DamageAction;
import rutebaga.model.entity.ability.SpawnAction;
import rutebaga.model.entity.ability.SpawnAction.Location;
import rutebaga.model.entity.ability.SpawnAction.TargetMethod;
import rutebaga.model.environment.InstanceType;
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

		if ("damage".equals(type))
		{
			// format:
			// damage	[dam name] [dam expr]
			m = pattern.matcher(expr);
			m.matches();
			String damageId = m.group(NAME_IDX);
			String damageExpr = m.group(EXPR_IDX);

			Damage damage = (Damage) scaffold.get(damageId);

			ValueProvider<Entity> vp = DefaultValueProviderFactory
					.getInstance().parse(damageExpr, scaffold);

			return new DamageAction(vp, damage);
		}
		else if ("spawn".equals(type))
		{
			// format:
			// spawn	[location] [targetmethod] [instancetype]
			
			String[] parts = expr.split("[\\s\\t]");
			
			String strLocation = parts[0];
			String strTargetMethod = parts[1];
			String strInstanceType = parts[2];
			
			Location location = Location.valueOf(strLocation.toUpperCase());
			TargetMethod targetMethod = TargetMethod.valueOf(strTargetMethod.toUpperCase());
			InstanceType instanceType = (InstanceType) scaffold.get(strInstanceType);
			
			SpawnAction action = new SpawnAction();
			action.setLocation(location);
			action.setTargetMethod(targetMethod);
			action.setType(instanceType);
			
			return action;
		}

		return null;
	}
}
