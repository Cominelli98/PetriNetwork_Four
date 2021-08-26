package it.unibs.ingesw;


public class Priority_network extends Petri_network{
	
	private Petri_network petrus;
	static int priorityNetID = 0;
	private int priority_NetID;
	
	public Priority_network(Petri_network pn, Network n, String name) {
		super(n, name);
		
		
		/*petriLocations = pn.getLocations();
		petriTransitions = pn.getTransitions();
		petriNetLinks = pn.getLinks();*/
		this.fatherNetId = pn.getFatherNetId();
		this.name = name;
		this.priority_NetID = ++priorityNetID;
		this.petriNetId = pn.getId();
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
