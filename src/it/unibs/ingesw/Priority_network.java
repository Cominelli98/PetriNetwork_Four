package it.unibs.ingesw;

public class Priority_network extends Petri_network{
	
	private Petri_network petrus;
	
	public Priority_network(Petri_network pn, Network n, String name) {
		super(n, name);
		
		
		/*petriLocations = pn.getLocations();
		petriTransitions = pn.getTransitions();
		petriNetLinks = pn.getLinks();*/
		this.fatherNetId = pn.getFatherNetId();
		this.name = name;
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

}
