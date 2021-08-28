package it.unibs.ingesw;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public final class Menu_Petri {

	private final static String MENUPETRI[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Crea una rete di Petri a partire da una rete esistente",
			"2:Visualizza una rete di Petri",
			"3:Salva una o pi� reti di Petri",
			"4:Simula l'evoluzione di una rete di Petri da file",
			"0:Indietro",
			"___________________________",
	};
	private final static String MESSAGGI_MENU[] = {
			"da quale rete vuoi partire?",
			"Come vuoi chiamare questa rete di Petri?",
			"Esiste gi� una rete di petri con questo nome",
			"Questa rete di petri esiste gi�",
	};
	private static final String NO_RETI_V = "Non ci sono reti di petri da visualizzare";
	private static final String NO_RETI_S = "Non ci sono reti di petri da salvare";
	
	
	//TODO: da togliere?
/*	public static void petriMenu(ArrayList<Petri_network> pn, ArrayList<Network> ns) {
		int select = -1;
		do {
			for (String s : MENUPETRI)
				System.out.println(s);
			
			select = Utility.readLimitedInt(0, MENUPETRI.length-3);
			switch(select) {
			
			case 0:
				break;
			case 1:
				createPetri(pn, ns);
				break;
			case 2: 
				if(pn.size() != 0)
					Menu_Visua.petriNetViewer(pn);
				else {
					System.out.println(NO_RETI_V);
				}
				break;
			case 3:
				if(pn.size() != 0)
					Menu_Salva.pSaveOption(pn);
				else 
					System.out.println(NO_RETI_S);
				break;
			case 4:
				simulaPetri();
				break;
			case 5:
				ArrayList<String> s2 = new ArrayList<String>();
				int j;
				try {
					s2 = ReadN.readFile(Petri_network.class);
					} catch (FileNotFoundException f) {
						f.printStackTrace();
					}
				Petri_network prete;
				System.out.println(ReadN.getNetNamesList(Petri_network.class));
				j = Utility.readLimitedInt(0, 10000);
				prete = (Petri_network) ReadN.jsonToObject(s2.get(j), Petri_network.class);
				pn.add(prete);
				break;
			}
		}while (select!=0);
		
	}*/
	
	public static void createPetri(ArrayList<Petri_network> pn, ArrayList<Network> ns) {
		
		System.out.println(Menu_Visua.getNetworksList(ns));
		int select = -1;
		System.out.println(MESSAGGI_MENU[0]);
		select = Utility.readLimitedInt(0, ns.size()-1);
		String name;
		do {
		System.out.println(MESSAGGI_MENU[1]);
		name = Utility.readString();
		if(checkPNetExistence(name, pn))
			System.out.println(MESSAGGI_MENU[2]);
		}while(checkPNetExistence(name, pn));
		Petri_network toAdd = new Petri_network(ns.get(select), name);
		setCosts(toAdd);
		setTokens(toAdd);
		if (!petriExistence(toAdd, pn))
			pn.add(toAdd);
		else
			System.out.println(MESSAGGI_MENU[3]);
	}
	
	
	
	private static boolean petriExistence(Petri_network toAdd, ArrayList<Petri_network> pn) {
		
		if (pn.size() == 0) {
			return false;
		}
		
		for (Petri_network pns : pn) {
			if(petriSingleCheck(pns, toAdd))
				return true;
		
		}
		return false;

	}
	
	
	
	private static boolean petriSingleCheck(Petri_network pn, Petri_network toCheck) {
		if (toCheck.getFatherNetId() == pn.getFatherNetId()){
			for(int i=0; i<toCheck.getLocations().size(); i++) {
				if(toCheck.getLocations().get(i).getValue() != pn.getLocations().get(i).getValue())
					return false;
			}
			
			for (int j=0; j<toCheck.getTransitions().size(); j++) {
				if(toCheck.getTransitions().get(j).getValue() != pn.getTransitions().get(j).getValue())
					return false;
				/*if(toCheck.getTransitions().get(j).getPriority() != pn.getTransitions().get(j).getPriority())
					return false;*/
			}
			return true;
		}
		return false;
	}
	
	private static boolean checkPNetExistence (String name, ArrayList<Petri_network> pn) {
		if(pn.size()>0) {
			for (Petri_network pns : pn) {
				if(Utility.nameCheck(pns, name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static void setCosts(Petri_network toSet) {
		for (Petri_transition pt : toSet.getTransitions()) {
			System.out.println("Inserisci il costo della transizione "+pt.getName() + " (1 per default)");
			pt.setCost(Utility.readLowLimitInt(1));
	
		}
	}
	
	private static void setTokens(Petri_network toSet) {
		for (Petri_location pl : toSet.getLocations()) {
			System.out.println("Inserisci la marcatura iniziale della posizione "+pl.getName() + " (0 per default)");
			pl.setToken(Utility.readLowLimitInt(0));
		}
	}
	
	public static void simulaPetri() {
		ArrayList<String> s = new ArrayList<String>();
		Simulatore daSimulare;
		Petri_network rete;
		int scelta;
		int selezione;
		try {
		s = ReadN.readFile(Petri_network.class);
		} catch (FileNotFoundException f) {
			f.printStackTrace();
		}
		System.out.println("Scegli di quale rete di Petri vuoi simulare l'evoluzione");
		System.out.println(ReadN.getNetNamesList(Petri_network.class));
		scelta = Utility.readLimitedInt(0, s.size()-1);
		rete = (Petri_network) ReadN.jsonToObject(s.get(scelta), Petri_network.class);
		daSimulare = new Simulatore(rete);
		System.out.println("STATO DI PARTENZA:");
		Menu_Visua.printPetriNet(rete);
		do {
			System.out.println("MARCATURA SUCCESSIVA:");
			daSimulare.nextStep();
			System.out.println("Vuoi proseguire con la simulazione? \n 0)Esci \n 1)Prosegui");
			selezione = Utility.readLimitedInt(0, 1);
		}while(selezione!=0);
	}
	

}
