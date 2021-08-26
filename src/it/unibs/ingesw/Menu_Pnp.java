package it.unibs.ingesw;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public final class Menu_Pnp {

	private final static String MENUPNP[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Crea una PNp a partire da una PN",
			"2:Visualizza una PNp",
			"3:Salva una o più PNp",
			"4:Simula l'evoluzione di una PNp da file",
			"0:Indietro",
			"___________________________",
	};
	private final static String MESSAGGI_MENU[] = {
			"da quale rete vuoi partire?",
			"Come vuoi chiamare questa PNp?",
			"Esiste già una Pnp con questo nome",
			"Questa PNP esiste già",
	};
	private static final String NO_RETI_V = "Non ci sono PNp da visualizzare";
	private static final String NO_RETI_S = "Non ci sono PNp da salvare";

	public static void priorityMenu(ArrayList<Priority_network> pnp, ArrayList<Petri_network> pn, ArrayList<Network> ns) {
		int select = -1;
		do {
			for (String s : MENUPNP)
				System.out.println(s);
			
			select = Utility.readLimitedInt(0, MENUPNP.length-3);
			switch(select) {
			
			case 0:
				break;
			case 1:
				createPnp(pnp, pn, ns);
				break;
			case 2: 
				if(pnp.size() != 0)
					Menu_Visua.pnpViewer(pnp);
				else {
					System.out.println(NO_RETI_V);
				}
				break;
			case 3:
				if(pnp.size() != 0)
					Menu_Salva.pnpSaveOption(pnp);
				else 
					System.out.println(NO_RETI_S);
				break;
			case 4:
				ArrayList<String> s = new ArrayList<String>();
				Simulatore daSimulare;
				Priority_network rete;
				int scelta;
				int selezione;
				try {
					s = ReadN.readFile(Priority_network.class);
				} catch (FileNotFoundException f) {
					f.printStackTrace();
				}
				System.out.println("Scegli di quale PNp vuoi simulare l'evoluzione");
				System.out.println(ReadN.getNetNamesList(Priority_network.class));
				scelta = Utility.readLimitedInt(0, s.size()-1);
				rete = (Priority_network) ReadN.jsonToObject(s.get(scelta), Priority_network.class);
				daSimulare = new Simulatore(rete);
				System.out.println("STATO DI PARTENZA:");
				Menu_Visua.printPetriNet(rete);
				do {
					System.out.println("MARCATURA SUCCESSIVA:");
					daSimulare.nextStep();
					System.out.println("Vuoi proseguire con la simulazione? \n 0)Esci \n 1)Prosegui");
					selezione = Utility.readLimitedInt(0, 1);
				}while(selezione!=0);
				break;
		/*	case 5:
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
			*/}
		}while (select!=0);
		
	}
		
		private static void createPnp(ArrayList<Priority_network> pnp, ArrayList<Petri_network> pn, ArrayList<Network> ns) {
			
			System.out.println(Menu_Visua.getPNetworksList(pn));
			int select = -1;
			System.out.println(MESSAGGI_MENU[0]);
			select = Utility.readLimitedInt(0, pn.size()-1);
			String name;
			do {
			System.out.println(MESSAGGI_MENU[1]);
			name = Utility.readString();
			if(checkPNpExistence(name, pnp))
				System.out.println(MESSAGGI_MENU[2]);
			}while(checkPNpExistence(name, pnp));
			int i;
			for(i = 0; i<ns.size(); i++) {
				if (ns.get(i).getId() == pn.get(select).getFatherNetId())
					break;
			}
			Priority_network toAdd = new Priority_network(pn.get(select), ns.get(i), name);
			
		/*	setCosts(toAdd, pn.get(select));
			setTokens(toAdd, pn.get(select)); */
			setPriorities(toAdd);
			if (!checkPNpExistence(toAdd.getName(), pnp))
				pnp.add(toAdd);
			else
				System.out.println(MESSAGGI_MENU[3]);
		}	
		
	
		
		private static boolean pnpExistence(Priority_network toAdd, ArrayList<Priority_network> pnps) {
			
			if (pnps.size() == 0) {
				return false;
			}
			
			for (Priority_network pnp : pnps) {
				if(pnpSingleCheck(pnp, toAdd))
					return true;
			
			}
			return false;

		}
		
		
		
		private static boolean pnpSingleCheck(Priority_network pnp, Priority_network toCheck) {
			if (toCheck.getFatherNetId() == pnp.getFatherNetId()){
				if(pnp.getName().equals(toCheck.getName()))
					return true;
			}
			return false;
		}
		
		private static boolean checkPNpExistence (String name, ArrayList<Priority_network> pnp) {
			if(pnp.size()>0) {
				for (Priority_network pn : pnp) {
					if(Utility.nameCheck(pn, name)) {
						return true;
					}
				}
			}
			return false;
		}
		
		
		
		
		
		
		
		
		
		
		
		

	/*	private static void setCosts(Priority_network toSet, Petri_network pn) {
			for (Petri_transition pnt : toSet.getTransitions()) {
				for (Petri_transition pt : pn.getTransitions()) {
					if(pnt.getId() == pt.getId())
						pnt.setCost(pt.getValue());
				}
			}
		}

		private static void setTokens(Priority_network toSet, Petri_network pn) {
			for (Petri_location pnl : toSet.getLocations()) {
				for (Petri_location pl : pn.getLocations()) {
					if(pnl.getId() == pl.getId())
						pnl.setToken(pl.getValue());
				}
			}
		}
		
		*/
		
		private static void setPriorities(Priority_network toSet) {
			for (Petri_transition pt : toSet.getTransitions()) {
				System.out.println("Inserisci la priorità della transizione "+pt.getName() + " (1 per default)");
				pt.setPriority(Utility.readLowLimitInt(1));
			}
		}

}