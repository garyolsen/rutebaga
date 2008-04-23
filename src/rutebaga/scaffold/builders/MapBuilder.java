package rutebaga.scaffold.builders;

import java.util.List;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.EnvironmentAppAttr;
import rutebaga.model.environment.Hex2DTileConvertor;
import rutebaga.model.environment.Rect2DTileConvertor;
import rutebaga.model.environment.TileConverter;
import rutebaga.model.map.Tile;
import rutebaga.model.map.TileType;
import rutebaga.scaffold.MasterScaffold;

public class MapBuilder extends ConfigFileBuilder
{

	@Override
	protected String getDefaultFileName()
	{
		return "config/maps";
	}

	public Object create(String id)
	{
		String type = getProperty(id, "type");
		TileConverter converter = type.equals("hex") ? new Hex2DTileConvertor()
				: new Rect2DTileConvertor();
		Environment environment = new Environment(converter);
		return environment;
	}

	public void initialize(String id, Object object, MasterScaffold scaffold)
	{
		Environment environment = (Environment) object;
		
		int w = getInteger(id, "tilewidth");
		int h = getInteger(id, "tileheight");
		
		environment.setAppearanceAttr(new EnvironmentAppAttr(w, h));
		
		List<String> tileList = getInnerList(id, "tiles");
		int x=0, y=0;
		for(String list : tileList)
		{
			if(list == null || list.equals(""))
				continue;
			String[] parts = list.split("[\\s\\t]");
			boolean didSomething = false;
			for(String part : parts)
			{
				if(part != null && !part.equals(""))
				{
					if(!didSomething)
					{
						didSomething = true;
						y++;
					}
					TileType<?> type = (TileType) scaffold.get(part);
					Tile tile = type.makeInstance();
					environment.add(tile, new Vector2D(x, y));
					x++;
				}
			}
			x=0;
		}
	}

}
