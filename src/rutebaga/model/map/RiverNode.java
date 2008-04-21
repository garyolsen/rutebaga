package rutebaga.model.map;

import java.util.Collection;

import rutebaga.commons.math.Vector2D;
import rutebaga.model.DefaultLayers;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;

public class RiverNode extends Instance<RiverNode> {

	private RiverNode previous, next;
	private double factor;
	private double current;
	private Vector2D vector;
	
	public RiverNode(InstanceType<RiverNode> type)
	{
		super(type);
	}
	
	public RiverNode(InstanceType<RiverNode> type, double factor, Vector2D vector) {
		super(type);
		this.factor = factor;
		this.vector = vector;
	}
	
	public Vector2D getVector() {
		return vector;
	}

	@Override
	public boolean blocks(Instance other) {
		return false;
	}

	@Override
	public double getLayer() {
		return DefaultLayers.SHADOW.getLayer();
	}

	@Override
	public double getMass() {
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public InstanceSetIdentifier getSetIdentifier() {
		return InstanceSetIdentifier.EFFECT;
	}

	@Override
	public void tick() {
		Collection<Instance> instances = this.getCoexistantInstances();
//		System.out.println(instances);
		if (next != null)
		{
			//current = previous.getCurrent()*factor;
			for (Instance instance : instances) {
//				System.out.println(getTile().minus(previous.getTile()));
				instance.applyImpulse( new Vector2D(next.getTile()).minus(getTile()).times(factor) );
			}
		}
	}
	
	public double getCurrent() {
		return current;
	}
	
	public void setNext(RiverNode r) {
		this.next = r;
	}
	
	public RiverNode getNext() {
		return next;
	}
	
	public void setPrevious(RiverNode r) {
		this.previous = r;
	}
	
	public RiverNode getPrevious() {
		return previous;
	}
	
	public boolean hasNext() {
		return (this.next != null);
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public void setVector(Vector2D vector) {
		this.vector = vector;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	@Override
	public void setMass(double mass) {
		//can't set a river node's mass
	}
	
}
