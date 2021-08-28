package it.unibs.ingesw;

import java.util.ArrayList;
import java.util.Iterator;

public class Simulatore {

	private Petri_network rete;
	
	public Simulatore (Petri_network rete) {
		this.rete = rete;

	}
	
	public Simulatore (Priority_network pnp) {
		this.rete = pnp;
	}
	
	public void nextStep(){
		ArrayList<Petri_transition> transizioniAttivabili = topPriorityTransitions();
		int possibili = transizioniAttivabili.size();
		if (possibili == 0) 
			System.out.println("BLOCCO CRITICO");
		else if (possibili == 1) {
			stampAttivabili();
			System.out.println("C'è un'unica transizione attivabile, prossimo step:");
			modificaToken(transizioniAttivabili.get(0));
			Menu_Visua.printPetriNet(rete);
			}
		else {
			//stampa a video, richiede la scelta, esegue
			System.out.println("È possibile attivare le seguenti transizioni: \n Quale vuoi attivare?");
			stampAttivabili();
			int scelta = Utility.readLimitedInt(0, transizioniAttivabili.size() -1);
			modificaToken(transizioniAttivabili.get(scelta));
			Menu_Visua.printPetriNet(rete);
		}
	}
	
	
	private void stampAttivabili() {
		ArrayList<Petri_transition> transizioni = topPriorityTransitions();
		System.out.println("Lista transizioni attivabili:");
		for (int i=0; i<transizioni.size(); i++) {
			System.out.println(i + ") " + transizioni.get(i).getName());
		}
	}
	
	//TUTTE LE TRANSIZIONI ATTIVABILI CON PRIORITA MASSIMA
	private ArrayList<Petri_transition> topPriorityTransitions(){
		ArrayList<Petri_transition> risultato = new ArrayList<>();
		int topP = 1;
		for (Petri_transition pt : transAttivabili()) {
			if(pt.getPriority() > topP) {
				topP = pt.getPriority();
				risultato.clear();
				risultato.add(pt);
			}
			else if(pt.getPriority() == topP)
				risultato.add(pt);
		}
		return risultato;
	}
	
	
	//TUTTE LE TRANSIZIONI ATTIVABILI PERCHE HANNO TOKEN SUFFICIENTI LE LOCATION VICINE
	private ArrayList<Petri_transition> transAttivabili(){
		ArrayList<Petri_transition> risultato = new ArrayList<>();
		
		for (Petri_transition pt : rete.getTransitions()) {
			if(attivabile(pt))
				risultato.add(pt);
		}
		return risultato;
	}
	
	private boolean attivabile (Petri_transition pt) {
		boolean exist = checkIfOneLinkExistWithTrans(pt);
		int x;
		//prima di tutto controlliamo se almeno un link ha come destinazione la transizione
		if(!exist)
			return false;
		for (int i = 0; i< rete.getLinks().size(); i++) {
			if (rete.getLinks().get(i).getDestination() == pt.getId()) {
				x = getIndexOfLocation(rete.getLinks().get(i).getOrigin());
				if(rete.getLocations().get(x).getValue() < pt.getValue())
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param pt la transizione da andare a controllare
	 * @return un booleano che dice se almeno un link ha come destinazione la transizione
	 */
	private boolean checkIfOneLinkExistWithTrans(Petri_transition pt) {
		for(int i = 0; i< rete.getLinks().size(); i++) {
			if(rete.getLinks().get(i).getDestination() == pt.getId())
				return true;
		}
		return false;
	}

	private void modificaToken(Petri_transition pt) {
		rete.reduceToken(pt.getId(), pt.getValue());;
		rete.addToken(pt.getId(), 1);	//viene passato 1 perchè per ora è il valore di default
	}
	
	private int getIndexOfLocation(int toFind) {
		for (int i = 0; i < rete.getLocations().size(); i++) {
			if(rete.getLocations().get(i).getId() == toFind)
				return i;
		}
		return 0;
	}
	
	
	/**
	 * metodo che ritorna true se tra le transition c'è un pareggio tra le transition con priorità maggiore
	 * @param attivabili transition che sono attivabili
	 * @return se c'è o meno un pareggio
	 */
	private boolean checkPrioritiesDraw(ArrayList<Petri_transition> attivabili) {
		ArrayList<Integer> priorities = getPriorities(attivabili);
		int max = Utility.getMax(priorities);
		int temp = 0;
		for(Integer priority : priorities) {
			if(max == priority)
					temp++;
		}	
		if(temp >= 2)
				return true;
		return false;
	}
	
	private ArrayList<Petri_transition> getDraw(ArrayList<Petri_transition> attivabili){
		ArrayList<Integer> priorities = getPriorities(attivabili);
		int max = Utility.getMax(priorities);
		for(int i = 0 ; i < attivabili.size() ; i++) {
			if(attivabili.get(i).getNetId() != max)
				attivabili.remove(i);
		}
		return attivabili;
	}
	
	private ArrayList<Integer> getPriorities(ArrayList<Petri_transition> attivabili){
		ArrayList<Integer> priorities = new ArrayList<>();
		for(Petri_transition pt : attivabili)
			priorities.add(pt.getPriority());
		return priorities;
	}
}
