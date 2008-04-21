package rutebaga.appearance;

import java.awt.Image;

import rutebaga.model.environment.Instance;
import rutebaga.model.environment.appearance.AnimatedAppearanceManager;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;
import rutebaga.model.environment.appearance.Appearance.Orientation;

/**
 * Knows how to represent an animation. An animation is associated with an
 * orientation and a sequence of images.
 * 
 */
public class AnimatedAppearanceDef implements
		AppearanceManagerDefinition<Instance> {
	private Image[] images;

	private int wait;

	private Orientation orientation;

	private int offsetX;

	private int offsetY;

	/**
	 * 
	 * @return The images that make up the frames of animation.
	 */
	public Image[] getImages() {
		return images;
	}

	/**
	 * @return The amount this animation is being moved horizontally.
	 */
	public int getOffsetX() {
		return offsetX;
	}

	/**
	 * @return The amount this animation is being moved vertically.
	 */
	public int getOffsetY() {
		return offsetY;
	}

	/**
	 * @return The direction this animation is facing.
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * @return The amount of ticks between animation frames.
	 */
	public int getWait() {
		return wait;
	}

	/**
	 * @param instance
	 * @return An AppearanceManager using this AnimatedAppearanceDef to
	 *         represent the provided Instance.
	 */
	public AppearanceManager make(Instance instance) {
		Appearance[] appearances = new Appearance[images.length];
		for (int i = 0; i < images.length; i++) {
			Image image = images[i];
			Appearance appearance = new Appearance(image);
			if (orientation != null)
				appearance.setOrientation(orientation);
			appearance.setOffsetX(offsetX);
			appearance.setOffsetY(offsetY);
			appearances[i] = appearance;
		}
		AnimatedAppearanceManager manager = new AnimatedAppearanceManager(
				appearances, wait);
		return manager;
	}

	/**
	 * @param images
	 *            The series of images that will animate.
	 */
	public void setImages(Image[] images) {
		this.images = images;
	}

	/**
	 * @param offsetX
	 *            The horizontal offset for each frame of this animation.
	 */
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * @param offsetY
	 *            The vertical offset for each frame of this animation.
	 */
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * @param orientation
	 *            The direction that this animation is facing.
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * @param wait
	 *            The amount of ticks between animation frames.
	 */
	public void setWait(int wait) {
		this.wait = wait;
	}

}
