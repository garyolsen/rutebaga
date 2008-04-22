package rutebaga.appearance;

import java.awt.Component;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.entity.Entity;
import rutebaga.model.entity.Mount;
import rutebaga.model.environment.TileConverter;
import rutebaga.model.environment.appearance.Appearance;
import rutebaga.model.environment.appearance.AppearanceManager;

/**
 * Knows what appearnce to associate with its entity on any given clock tick.
 * 
 * 
 */
public class MountAppearanceManager extends AppearanceManager {
	private class Standing extends State {
		private int currentFrame;

		private int directions = standing.length;

		@Override
		Appearance getAppearance() {
			if(mount.isMounted()) {
				Appearance dirApps[] = mountedStanding[getDirectionOrdinal(directions)];
				return dirApps[currentFrame % dirApps.length];
			} else {
				Appearance dirApps[] = standing[getDirectionOrdinal(directions)];
				return dirApps[currentFrame % dirApps.length];
			}
		}

		@Override
		void tick() {
			// System.out.println("standing");
			if (mount.isWalking()) {
				changeState(new Walking());
				currentState.tick();
			}
			// currentFrame++;
		}

	}

	private abstract class State {
		void changeState(State state) {
			MountAppearanceManager.this.currentState = state;
		}

		abstract Appearance getAppearance();

		abstract void tick();
	}

	private class Walking extends State {
		private int currentFrame;

		private int directions = standing.length;

		int wait = 0;

		@Override
		Appearance getAppearance() {
			if(mount.isMounted()) {
				Appearance dirApps[] = mountedWalking[getDirectionOrdinal(directions)];
				if(mount.getVelocity().getMagnitude() < 0.005)
					return dirApps[0];
				return dirApps[((currentFrame / (getWalkingWait() + 1))
						% (dirApps.length-1))+1];
			} else {
				Appearance dirApps[] = walking[getDirectionOrdinal(directions)];
				if(mount.getVelocity().getMagnitude() < 0.005)
					return dirApps[0];
				return dirApps[((currentFrame / (getWalkingWait() + 1))
						% (dirApps.length-1))+1];
			}
		}

		@Override
		void tick() {
			// System.out.println("walking");
			if (!mount.isWalking()) {
				changeState(new Standing());
				currentState.tick();
			}
			currentFrame++;
		}

	}

	private Mount mount;

	private Appearance[][] walking;

	private Appearance[][] standing;
	
	private Appearance[][] mountedWalking;

	private Appearance[][] mountedStanding;

	private State currentState;

	/**
	 * Constructs an EntityAppearanceManager to manage the appearance of the
	 * passed Entity.
	 */
	public MountAppearanceManager(Mount mount) {
		super();
		this.mount = mount;
	}

	@Override
	public Appearance getAppearance() {
		if (currentState == null)
			tick();
		return currentState.getAppearance();
	}

	/**
	 * @return The Entity who's appearance is being managed.
	 */
	public Mount getMount() {
		return mount;
	}

	/**
	 * @return The Appearances associated with standing animations for all
	 *         orientations.
	 */
	public Appearance[][] getStanding() {
		return standing;
	}

	/**
	 * @return The Appearances associated with walk animations for all
	 *         orientations.
	 */
	public Appearance[][] getWalking() {
		return walking;
	}
	/**
	 * @return The Appearances associated with standing animations for all
	 *         orientations.
	 */
	public Appearance[][] getMountedStanding() {
		return mountedStanding;
	}

	/**
	 * @return The Appearances associated with walk animations for all
	 *         orientations.
	 */
	public Appearance[][] getMountedWalking() {
		return mountedWalking;
	}

	/**
	 * @param The
	 *            Appearances associated with standing animations for all
	 *            orientations.
	 */
	public void setStanding(Appearance[][] standing) {
		this.standing = standing;
	}

	/**
	 * @param walking
	 *            The Appearances associated with walk animations for all
	 *            orientations.
	 */
	public void setWalking(Appearance[][] walking) {
		this.walking = walking;
	}

	/**
	 * @param The
	 *            Appearances associated with standing animations for all
	 *            orientations.
	 */
	public void setMountedStanding(Appearance[][] standing) {
		this.mountedStanding = standing;
	}

	/**
	 * @param walking
	 *            The Appearances associated with walk animations for all
	 *            orientations.
	 */
	public void setMountedWalking(Appearance[][] walking) {
		this.mountedWalking = walking;
	}


	@Override
	public void tick() {
		if (currentState != null)
			currentState.tick();
		else
			currentState = new Standing();
	}

	private int getDirectionOrdinal(int total) {
		double offset = 0.75;
		TileConverter conv = mount.getEnvironment().getTileConvertor();
		Vector2D direction = conv.toRect(
				mount.getFacing().plus(mount.getCoordinate())).minus(
				conv.toRect(mount.getCoordinate()));
		double angle = direction.getAngle() + (2 + offset) * Math.PI;
		angle -= 2 * Math.PI * (int) (angle * 0.5 / Math.PI);
		double proportion = angle * 0.5 / Math.PI;
		return (int) (proportion * total);
	}

	private int getWalkingWait() {
		Vector2D velocity = mount.getVelocity();
		double mag = velocity.getMagnitude();
		return (int) (0.5 / mag);
	}

}

