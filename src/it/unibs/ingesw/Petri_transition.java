package it.unibs.ingesw;

public class Petri_transition extends Transition implements GenericNode{
	private int cost;
	
	public Petri_transition(Transition t, int petriNetId) {
		super(petriNetId, t.getId(), t.getName());
		this.cost = 1;
	}
	
	/*public Petri_transition(Transition t, int petriNetId, int cost) {
		super(petriNetId, t.getNodeId(), t.getName());
		this.cost = cost;
	}*/
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getValue() {
		return this.cost;
	}
}
