package rutebaga.scaffold.builders;

import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.ability.EntityEffectAction;
import rutebaga.model.entity.effect.FlagEffect;
import rutebaga.model.entity.effect.SlotEffect;
import rutebaga.model.entity.effect.StatEffect;
import rutebaga.model.entity.effect.VPStatEffect;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.item.SlotType;
import rutebaga.scaffold.MasterScaffold;

public class EntityEffectBuilder extends ConfigFileBuilder
{
	private enum Type
	{
		STAT, SLOT, FLAG, ENTITYEFFECT;
	}

	@Override
	protected String getDefaultFileName()
	{
		return "config/entityeffects";
	}

	public Object create(String id)
	{
		String typeStr = getProperty(id, "type");
		Type type = Type.valueOf(typeStr.toUpperCase());
		switch(type)
		{
		case STAT:
			return new VPStatEffect();
		case SLOT:
			return new SlotEffect(null, 0);
		case FLAG:
			return new FlagEffect();
		}
		return null;
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		String typeStr = getProperty(id, "type");
		Type type = Type.valueOf(typeStr.toUpperCase());
		switch(type)
		{
		case STAT:
			VPStatEffect vpEff = (VPStatEffect) object;
			ValueProvider<Entity> vp = getValueProvider(id, "value", scaffold);
			vpEff.setAugend(vp);
			vpEff.setStatId((StatisticId) getObjectFor(id, "stat", scaffold));
			break;
		case SLOT:
			SlotEffect sleff = (SlotEffect) object;
			sleff.setQty(getInteger(id, "value"));
			sleff.setType((SlotType) getObjectFor(id, "slot", scaffold));
			break;
		case FLAG:
			FlagEffect fleff = (FlagEffect) object;
			fleff.setFlag(getProperty(id, "flag"));
			fleff.setValueProvider(getValueProvider(id, "value", scaffold));
			break;
		}
	}

}
