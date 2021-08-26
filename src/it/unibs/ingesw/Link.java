package it.unibs.ingesw;

public class Link {
	
	private int IDNode1;
	private int IDNode2;		
	private int netId;
	private int orientation;
	
	public Link (int id1, int id2, int netId, int orientation) {
		this.IDNode1 = id1;
		this.IDNode2 = id2;
		this.netId = netId;
		this.orientation = orientation;
	}

	/*public <T extends GenericNode> T getOrigin() {
		if (this.orientation == 1)
			return (T) location;
		return (T) transition;
	}*/
	
	public int getOrigin(){
		if(orientation == 1)
			return IDNode1;
		return IDNode2;
	}


	/*public void setOrigin(Node origin) {
		this.origin = origin;
	}*/

	/*public <T extends GenericNode > T getDestination() {
		if(this.orientation == 1)
			return (T) transition;
		return (T) location;
	}*/
	
	public int getDestination() {
		if(orientation == 1)
			return IDNode2;
		return IDNode1;
	}
	

	/*public void setDestination(Node destination) {
		this.destination = destination;
	}
	*/
	
	public int getLocation() {
		return IDNode1;
	}
	
	public int getTransition() {
		return IDNode2;
	}
	
	public int getOrientation() {
		return orientation;
	}

}
