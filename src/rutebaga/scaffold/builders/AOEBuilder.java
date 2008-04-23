package rutebaga.scaffold.builders;

import java.util.List;

import rutebaga.commons.logic.Rule;
import rutebaga.commons.logic.ValueProviderRule;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.effect.AreaEffectType;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.scaffold.MasterScaffold;

public class AOEBuilder extends InstanceBuilder
{

	@Override
	protected String getDefaultFileName()
	{
		return "config/areaeffects";
	}

	public Object create(String id)
	{
		return new AreaEffectType();
	}

	@Override
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);

		Object[] effects = getObjectArray(id, "effects", "[\\s\\t]", scaffold);

		AreaEffectType type = (AreaEffectType) object;

		for (Object effect : effects)
			type.getEffects().add((EntityEffect) effect);
		
		Boolean remove = getBoolean(id, "remove");
		if(remove != null)
			type.setRemove(true);

		List<String> ruleVPs = getInnerList(id, "entityrules");
		for(String vpDesc : ruleVPs)
		{
			ValueProvider vp = DefaultValueProviderFactory.getInstance().parse(vpDesc, scaffold);
			type.getRules().add(new ValueProviderRule(vp));
		}
		
		List<String> activeRuleVPs = getInnerList(id, "activerules");
		for(String vpDesc : activeRuleVPs)
		{
			ValueProvider vp = DefaultValueProviderFactory.getInstance().parse(vpDesc, scaffold);
			type.getActiveRules().add(new ValueProviderRule(vp));
		}
	}

}
