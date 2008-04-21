package rutebaga.model.map;

import java.util.Collection;

import rutebaga.appearance.AppearanceManagerDefinition;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.DefaultLayers;
import rutebaga.model.environment.ConcreteInstanceType;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;

public class River {

	private RiverNode head;
	private RiverNode tail;
	private Vector2D location;
	private RiverNodeType type = new RiverNodeType();
	private AppearanceManagerDefinition appearanceManager;
	
	public River() {
		
		head = type.makeInstance();
		head.setFactor(1.0);
		head.setVector(new Vector2D(-1,-1));
		tail = head;
	}
	
	public void addNodeAtTail(double force, Vector2D vector) {
		RiverNode newNode = type.create();
		newNode.setFactor(force);
		newNode.setVector(vector);
		tail.setNext(newNode);
		newNode.setPrevious(tail);
		newNode.setAppearanceManager(appearanceManager.make(newNode));
		tail = newNode;
	}
	
	public void setLocation(Vector2D location) {
		this.location = location;
	}
	
	public void addToEnvironment(Environment e) {
		RiverNode current = head;
		IntVector2D offset = new IntVector2D(0,0);
		while (current != null)
		{
			//System.out.println("Added river node at " + location.plus(offset));
			
			e.add(current, location.plus(offset));
			current = current.getNext();
			if (current != null)
				offset = offset.plus(current.getVector());
		}
	}
	
	
	private class RiverNodeType extends ConcreteInstanceType<RiverNode>
	{

		@Override
		protected RiverNode create() {
			return new RiverNode(this);
		} 
		
		@Override
		protected void initialize(RiverNode instance)
		{
			super.initialize(instance);
		}
	}
	
	private class RiverNode extends Instance<RiverNode> {

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
			//System.out.println(instances);
			if (next != null)
			{
				//current = previous.getCurrent()*factor;
				for (Instance instance : instances) {
//					System.out.println(getTile().minus(previous.getTile()));
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
		
	}

	public AppearanceManagerDefinition getAppearanceManager() {
		return appearanceManager;
	}

	public void setAppearanceManager(AppearanceManagerDefinition appearanceManager) {
		this.appearanceManager = appearanceManager;
	}
}
