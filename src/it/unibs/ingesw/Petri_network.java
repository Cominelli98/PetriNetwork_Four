package it.unibs.ingesw;

import java.util.ArrayList;

public class Petri_network implements IDNameGiver{
	protected ArrayList<Petri_location> petriLocations;
	protected ArrayList<Petri_transition> petriTransitions;
	protected ArrayList<Link> petriNetLinks;
	protected int petriNetId; 
	protected int fatherNetId;
	private String name;
	static int petriNetworkId = 0;
	
	public Petri_network(Network n, String name) {
		this.petriNetId = ++petriNetworkId;
		petriLocations = Converter.toPetriLocations(n.getLocations(), petriNetId);
		petriTransitions = Converter.toPetriTransitions(n.getTransitions(), petriNetId);
		petriNetLinks = Converter.toPetriLinks(n.getNetLinks(), petriNetId);
		this.fatherNetId = n.getId();
		this.name = name;
	}
	
	public Petri_network(Petri_network pt, String name) {
		this.fatherNetId = pt.getFatherNetId();
		this.name = name;
		this.petriLocations = pt.getLocations();
		this.petriTransitions = pt.getTransitions();
		this.petriNetLinks = pt.getLinks();
		this.petriNetId = pt.getNetId();
	}
	
	public ArrayList<Petri_location> getLocations(){
		return petriLocations;
	}
	
	public ArrayList<Petri_transition> getTransitions(){
		return petriTransitions;
	}
	
	public int getFatherNetId() {
		return fatherNetId;
	};

	@Override
	public String getName() {
		return name;
	}
	
	public int getNetId() {
		return petriNetId;
	}
	
	public StringBuffer getTransitionsList() {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i<petriTransitions.size(); i++) {
			s.append(i + ")" + petriTransitions.get(i).getName() + " costo: " + petriTransitions.get(i).getValue() + "\n");
		}
		return s;
	}
	
	public StringBuffer getLocationsList() {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i<petriLocations.size(); i++) {
			s.append(i + ")" + petriLocations.get(i).getName() + " marcatura: " + petriLocations.get(i).getValue() + "\n");
		}
		return s;
	}
	
	public StringBuffer getLinksList() {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i < petriNetLinks.size(); i++) {
			s.append(i + ")" + nodeNameFromID(petriNetLinks.get(i).getOrigin()) + "---->" + nodeNameFromID(petriNetLinks.get(i).getDestination())+ "\n");
		}
		return s;
	}
	
	public ArrayList<Link> getLinks(){
		return this.petriNetLinks;
	}

	@Override
	public int getId() {
		return petriNetId;
	}
	
	public void reduceToken(int idTransition, int quantity) {
		for(Link l : petriNetLinks) {
			if(l.getTransition() == idTransition && l.getOrientation() == 1) {
				reduceLocationToken(l.getLocation(), quantity);
			}
		}
	}
	
	public void addToken(int idTransition, int quantity) {
		for(Link l : petriNetLinks) {
			if(l.getTransition() == idTransition && l.getOrientation() == -1) {
				addLocationToken(l.getLocation(), quantity);
				}
		}
	}
	
	public String nodeNameFromID(int id) {
		for(Location pl : petriLocations) {
			if(pl.getId() == id)
				return pl.getName();
		}
		for(Transition pt : petriTransitions) {
			if(pt.getId() == id)
				return pt.getName();
		}
		return null;
	}
	
	public void reduceLocationToken(int id, int quantity) {
		for(Petri_location pl : petriLocations) {
			if(pl.getId() == id)
				pl.reduceToken(quantity);
		}
	}
	
	public void addLocationToken(int id, int quantity) {
		for(Petri_location pl : petriLocations) {
			if(pl.getId() == id)
				pl.addToken(quantity);
		}
	}
}
