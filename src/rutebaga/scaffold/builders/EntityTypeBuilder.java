package rutebaga.scaffold.builders;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import rutebaga.commons.math.BidirectionalValueProvider;
import rutebaga.commons.math.ValueProvider;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityType;
import rutebaga.model.entity.Team;
import rutebaga.model.entity.stats.StatisticId;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.scaffold.MasterScaffold;

public class EntityTypeBuilder extends InstanceBuilder
{
	private static String[] directionStrings =
	{ "North", "NorthEast", "SouthEast", "South", "SouthWest", "NorthWest" };

	@Override
	protected String getDefaultFileName()
	{
		return "config/entity";
	}

	public Object create(String id)
	{
		return new EntityType();
	}

	@SuppressWarnings("unchecked")
	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		super.initialize(id, object, scaffold);
		EntityType type = (EntityType) object;

		type.setMovementSpeed((ValueProvider<Entity>) getValueProvider(id,
				"moveSpd", scaffold));
		type.setDeadStrategy((ValueProvider<Entity>) getValueProvider(id,
				"deadStrat", scaffold));
		type
				.setSkillPtStrat((BidirectionalValueProvider<Entity>) getValueProvider(
						id, "skillPtStrat", scaffold));
		type.setWallet((BidirectionalValueProvider<Entity>) getValueProvider(
				id, "wallet", scaffold));
		type.setCooldownProvider(getValueProvider(id, "cooldown", scaffold));

		// type.setBargainSkillAmount((ValueProvider<Entity>)
		// getValueProvider(id, "bargain", scaffold));

		String standingId = getProperty(id, "standing");
		String walkingId = getProperty(id, "walking");

		Integer offsetX = getInteger(id, "offsetX");
		Integer offsetY = getInteger(id, "offsetY");
		offsetX = offsetX == null ? 0 : offsetX;
		offsetY = offsetY == null ? 0 : offsetY;

		type.setStanding(getAnimatedAppearances(standingId, scaffold, offsetX,
				offsetY));
		type.setWalking(getAnimatedAppearances(walkingId, scaffold, offsetX,
				offsetY));
		type.setRadius(this.getInteger(id, "vRadius"));

		type.setTeam((Team) getObjectFor(id, "team", scaffold));

		type.setDecayTime(this.contains(id, "vDecayTime") ? this.getInteger(id,
				"vDecayTime") : 0);

		Object[] abilityTypes = getObjectArray(id, "abilities", "[\\s\\t]",
				scaffold);
		for (Object abType : abilityTypes)
		{
			type.getAbilityTypes().add(abType);
		}

		List<String> initialStats = getInnerList(id, "initialstats");
		for (String line : initialStats)
		{
			Matcher m = ConfigFileBuilder.pattern.matcher(line);
			if (!m.matches())
				continue;
			StatisticId statId = (StatisticId) scaffold.get(m
					.group(ConfigFileBuilder.NAME_GP));
			double value = Double.valueOf(m.group(ConfigFileBuilder.VALUE_GP));
			System.out.println("initializing " + statId + " to " + value);
			type.getInitialStats().put(statId, value);
		}
	}

	private Appearance[][] getAnimatedAppearances(String name,
			MasterScaffold scaffold, int offsetX, int offsetY)
	{
		List<Appearance[]> list = new ArrayList<Appearance[]>();
		for (String dirString : directionStrings)
		{
			list.add(getAppearancesForDirection(name, dirString, scaffold,
					offsetX, offsetY));
		}
		return list.toArray(new Appearance[list.size()][]);
	}

	private Appearance[] getAppearancesForDirection(String name,
			String direction, MasterScaffold scaffold, int offsetX, int offsetY)
	{
		boolean stop = false;
		ArrayList<Appearance> list = new ArrayList<Appearance>();
		for (int i = 1; !stop; i++)
		{
			String nStr = String.valueOf(i);
			if (nStr.length() == 1)
				nStr = "0" + nStr;
			String scaffId = name + direction + nStr;
			if (scaffold.contains(scaffId))
			{
				list.add(new Appearance((Image) scaffold.get(scaffId)));
				list.get(list.size() - 1).setOffsetX(offsetX);
				list.get(list.size() - 1).setOffsetY(offsetY);
			}
			else
				stop = true;
		}
		return list.toArray(new Appearance[list.size()]);
	}

}
