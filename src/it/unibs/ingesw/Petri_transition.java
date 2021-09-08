package it.unibs.ingesw;

public class Petri_transition extends Transition implements GenericNode{
	private int cost;
	private int priority;
	
	public Petri_transition(Transition t, int petriNetId) {
		super(petriNetId, t.getId(), t.getName());
		this.cost = 1;
		this.priority = -1;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getValue() {
		return this.cost;
	}
}
