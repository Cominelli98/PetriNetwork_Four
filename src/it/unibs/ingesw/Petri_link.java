package it.unibs.ingesw;

public class Petri_link{
	//separato per possibile priorità da aggiungere
	private Petri_location p_location;
	private Petri_transition p_transition;
	private int petriNetId;
	private int orientation;
	
	public Petri_link(Petri_location pl, Petri_transition pt, int petriNetId, int orientation) {
		this.p_location = pl;
		this.p_transition = pt;
		this.petriNetId = petriNetId;
		this.orientation = orientation;
	}
	
	public Petri_location getLocation() {
		return p_location;
	}
	
	public Petri_transition getTransition() {
		return p_transition;
	}
	
	public int getOrientation() {
		return orientation;
	}
	
	public <T extends GenericNode> T getOrigin() {
		if (this.orientation == 1)
			return (T) p_location;
		return (T) p_transition;
	} 
	
	public <T extends GenericNode > T getDestination() {
		if(this.orientation == 1)
			return (T) p_transition;
		return (T) p_location;
	}
	
	public void reduceToken(int reduction) {
		p_location.reduceToken(reduction);
	}
	
	public void addToken(int toAdd) {
		p_location.addToken(toAdd);
	}
}
