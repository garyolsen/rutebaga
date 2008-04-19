package rutebaga.model.item;

public class SlotType
{
	private String name;
	private Integer globalMax;

	public SlotType(String name)
	{
		super();
		this.name = name;
	}

	public int getGlobalMax()
	{
		return globalMax;
	}

	public String getName()
	{
		return name;
	}

	public void setGlobalMax(int globalMax)
	{
		this.globalMax = globalMax;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getQtyAvailable(int total)
	{
		return globalMax == null ? total : Math.min(globalMax, total);
	}
}
