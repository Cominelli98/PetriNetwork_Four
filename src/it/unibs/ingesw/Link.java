package it.unibs.ingesw;

public class Link {
	
	private Location location;
	private Transition transition;		
	private int netId;
	private int orientation;
	
	public Link (Location l, Transition t, int netId, int orientation) {
		this.location = l;
		this.transition = t;
		this.netId = netId;
		this.orientation = orientation;
	}

	public <T extends GenericNode> T getOrigin() {
		if (this.orientation == 1)
			return (T) location;
		return (T) transition;
	}


	/*public void setOrigin(Node origin) {
		this.origin = origin;
	}*/

	public <T extends GenericNode > T getDestination() {
		if(this.orientation == 1)
			return (T) transition;
		return (T) location;
	}

	/*public void setDestination(Node destination) {
		this.destination = destination;
	}
	*/
	
	public Location getLocation() {
		return location;
	}
	
	public Transition getTransition() {
		return transition;
	}
	
	public int getOrientation() {
		return orientation;
	}

}
