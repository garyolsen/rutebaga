package rutebaga.appearance;

import java.awt.Image;

import rutebaga.model.environment.Instance;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;
import rutebaga.model.environment.appearance.StaticAppearanceManager;
import rutebaga.model.environment.appearance.Appearance.Orientation;

/**
 * Knows how to represent a something that doesn't animate. There will be an
 * orientation associated.
 * 
 */
public class StaticAppearanceDef implements
		AppearanceManagerDefinition<Instance> {
	private Image image;

	private Orientation orientation;

	private int offsetX;

	private int offsetY;

	public StaticAppearanceDef() {
		super();
	}

	/**
	 * 
	 * @return The Image that is being used in this appearance.
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return The horizontal offset for the image to be drawn.
	 */
	public int getOffsetX() {
		return offsetX;
	}

	/**
	 * @return The vertical offset for the image to be drawn.
	 */
	public int getOffsetY() {
		return offsetY;
	}

	/**
	 * @return The direction that this appearance is facing.
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * Constructs a new StaticAppearanceDef using the one Image provided.
	 * 
	 * @param image
	 *            The image that will always be used with this appearance.
	 */
	public StaticAppearanceDef(Image image) {
		super();
		this.image = image;
	}

	/**
	 * @param instance
	 *            The Instance who's appearance is to be managed.
	 * @return An AppearanceManager that manages the appearance of the provided
	 *         Instance.
	 */
	public AppearanceManager make(Instance instance) {
		Appearance app = new Appearance(image);
		if (orientation != null)
			app.setOrientation(orientation);
		app.setOffsetX(offsetX);
		app.setOffsetY(offsetY);
		return new StaticAppearanceManager(app);
	}

	/**
	 * @param image
	 *            The image that will be displayed by this StaticAppearanceDef.
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @param offsetX
	 *            The horizontal offset of the image to be drawn.
	 */
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * @param offsetY
	 *            The vertical offset of the image to be drawn.
	 */
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * @param orientation
	 *            Set the direction that this StaticAppearanceDef is facing.
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

}
