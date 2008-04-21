package rutebaga.controller.command;

public class FixedLabelDeterminer implements LabelDeterminer {
	
	private final String label;
	
	public FixedLabelDeterminer(String label) {
		if (label == null)
			throw new NullPointerException();
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
