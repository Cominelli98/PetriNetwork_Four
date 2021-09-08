package it.unibs.ingesw;

import java.util.ArrayList;

public final class Converter {
	
	
	public static Petri_location toPetri (Location l, int petriNetId) {
		return new Petri_location(l, petriNetId);
	}
	
	public static ArrayList<Petri_location> toPetriLocations (ArrayList<Location> l, int petriNetId){
		
		ArrayList<Petri_location> temp = new ArrayList<>();
		for (Location location : l) {
			temp.add(toPetri(location, petriNetId));
		}
		return temp;
	}
	
	public static Petri_transition toPetri (Transition t, int petriNetId) {
		return new Petri_transition(t, petriNetId);
	}
	
	public static ArrayList<Petri_transition> toPetriTransitions (ArrayList<Transition> t, int petriNetId){
		
		ArrayList<Petri_transition> temp = new ArrayList<>();
		for (Transition transition : t) {
			temp.add(toPetri(transition, petriNetId));
		}
		return temp;
	}
	
	
	public static Link toPetri (Link l, int petriNetId) {
		Link newLink = new Link(l.getLocation(), l.getTransition(), petriNetId, l.getOrientation());
		return newLink;
	}
	
	
	
	public static ArrayList<Link> toPetriLinks (ArrayList<Link> links, int petriNetId){
		ArrayList<Link> temp = new ArrayList<>();
		for(Link link : links) {
			temp.add(toPetri(link, petriNetId));
		}
		return temp;
	}
	
}
