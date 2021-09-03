package it.unibs.ingesw;

import java.util.ArrayList;

public class Priority_network extends Petri_network implements IDNameGiver{
	
	static int priorityNetID = 0;
	private int priority_NetID;
	
	public Priority_network(Petri_network pn, String name) {
		super(pn, name);
		this.priority_NetID = ++priorityNetID;
	}
	
	public StringBuffer getTransitionsList() {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i<petriTransitions.size(); i++) {
			s.append(i + ")" + petriTransitions.get(i).getName() 
					+ " costo: " + petriTransitions.get(i).getValue() 
					+ " priorità: " + petriTransitions.get(i).getPriority() + "\n");
		}
		return s;
	}
	
	public int getPetriNetID() {
		return this.petriNetId;
	}
	
	public int getPriorityNetID() {
		return this.priority_NetID;
	}
	
	public int getId() {
		return this.priority_NetID;
	}

}
