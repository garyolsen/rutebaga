package rutebaga.model.map;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import rutebaga.appearance.AppearanceManagerDefinition;
import rutebaga.appearance.RiverNodeAppearanceDef;
import rutebaga.commons.math.IntVector2D;
import rutebaga.commons.math.Vector2D;
import rutebaga.model.environment.Environment;

public class River<T extends River<T>> {

	private RiverNode head;
	private RiverNode tail;
	private Vector2D location;
	private double force = 0.5;
	private RiverNodeType riverNodeType = new RiverNodeType();
	private RiverNodeAppearanceDef appDef;
	
	public RiverNodeAppearanceDef getAppDef() {
		return appDef;
	}

	public void setAppDef(RiverNodeAppearanceDef appDef) {
		this.appDef = appDef;
	}

	public River(RiverType<T> type) {
		head = riverNodeType.makeInstance();
		head.setFactor(1.0);
		head.setVector(new Vector2D(-1,-1));
		tail = head;
	}
	
	public River(RiverType<T> type, double defaultFactor) {
		head = riverNodeType.makeInstance();
		head.setFactor(1.0);
		head.setVector(new Vector2D(-1,-1));
		tail = head;
		this.force = defaultFactor;
	}
	
	public void addNodeAtTail(double force, Vector2D vector) {
		RiverNode newNode = riverNodeType.create();
		newNode.setFactor(force);
		newNode.setVector(vector);
		tail.setNext(newNode);
		newNode.setPrevious(tail);
		newNode.setAppearanceManager(appDef.make(newNode));
		tail = newNode;
	}
	
	public void addNodeAtTail(Vector2D vector) {
		RiverNode newNode = riverNodeType.create();
		newNode.setFactor(force);
		newNode.setVector(vector);
		tail.setNext(newNode);
		newNode.setPrevious(tail);
		newNode.setAppearanceManager(appDef.make(newNode));
		tail = newNode;
	}
	
	public void reverse() {
		RiverNode current;
		if (head != null) {
			current = head;
			while(current != null)
			{
				current.getVector().opposite();
				if (current.getNext() != null)
					current = current.getNext();
			}	
		}
	}
	
	public Iterator<RiverNode> getNodeIterator() {
		List<RiverNode> nodeList = new LinkedList<RiverNode>();
		RiverNode current;
		if (head != null) {
			current = head;
			while(current != null)
			{
				nodeList.add(current);
				if (current.getNext() != null)
					current = current.getNext();
				else
					current = null;
			}	
		}
		return nodeList.iterator();
	}
	
	public void setLocation(Vector2D location) {
		this.location = location;
	}
	
	public void addToEnvironment(Environment e) {
		RiverNode current = head;
		IntVector2D offset = new IntVector2D(0,0);
		while (current != null)
		{	
			e.add(current, location.plus(offset));
			current = current.getNext();
			if (current != null)
				offset = offset.plus(current.getVector());
		}
	}

	public double getForce() {
		return force;
	}

	public void setForce(double force) {
		this.force = force;
	}

}
