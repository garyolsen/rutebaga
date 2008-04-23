package rutebaga.model.environment;

public class EnvironmentAppAttr
{
	private int tileWidth;
	private int tileHeight;

	public EnvironmentAppAttr(int w, int h)
	{
		this.tileWidth = w;
		this.tileHeight = h;
	}

	public int getTileWidth()
	{
		return tileWidth;
	}

	public void setTileWidth(int tileWidth)
	{
		this.tileWidth = tileWidth;
	}

	public int getTileHeight()
	{
		return tileHeight;
	}

	public void setTileHeight(int tileHeight)
	{
		this.tileHeight = tileHeight;
	}
}
