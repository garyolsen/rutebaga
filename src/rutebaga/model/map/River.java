package rutebaga.model.map;

import java.util.Collection;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.DefaultLayers;
import rutebaga.model.environment.Environment;
import rutebaga.model.environment.Instance;
import rutebaga.model.environment.InstanceSetIdentifier;
import rutebaga.model.environment.InstanceType;

public class River {

	private RiverNode head;
	private Vector2D location;
	
	public River() {
		head = new RiverNode(null, 1.0, new Vector2D(-1,-1));
	}
	
	public void addNodeAtTail(double force, Vector2D vector) {
		RiverNode current = head;
		while (current.hasNext())
		{
			current = current.getNext();
		}
		RiverNode newNode = new RiverNode(null, force, vector);
		current.setNext(newNode);
		newNode.setPrevious(current);
	}
	
	public void setLocation(Vector2D location) {
		this.location = location;
	}
	
	public void addToEnvironment(Environment e) {
		RiverNode current = head;
		Vector2D offset = new Vector2D(0,0);
		while (current.hasNext())
		{
			e.add(current, location.plus(offset));
			current = current.getNext();
			offset = offset.plus(current.getVector());
		}
	}
	
	private class RiverNode<T extends RiverNode<T>> extends Instance<T> {

		private RiverNode previous, next;
		private double factor;
		private double current;
		private Vector2D vector;
		
		public RiverNode(InstanceType<T> type, double factor, Vector2D vector) {
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
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void tick() {
			Collection<Instance> instances = this.getCoexistantInstances();
			if (previous != null)
			{
				current = previous.getCurrent()*factor;
				for (Instance instance : instances) {
					instance.applyImpulse( getTile().minus(previous.getTile()).times(current) );
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
		
	}
}
