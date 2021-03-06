package it.unibs.ingesw;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public final class Menu_Pnp {

	private final static String MENUPNP[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Crea una PNp a partire da una PN",
			"2:Visualizza una PNp",
			"3:Salva una o pi? PNp",
			"4:Simula l'evoluzione di una PNp da file",
			"0:Indietro",
			"___________________________",
	};
	private final static String MESSAGGI_MENU[] = {
			"da quale rete vuoi partire?",
			"Come vuoi chiamare questa PNp?",
			"Esiste gi? una Pnp con questo nome",
			"Questa PNP esiste gi?",
	};
	private static final String NO_RETI_V = "Non ci sono PNp da visualizzare";
	private static final String NO_RETI_S = "Non ci sono PNp da salvare";
	

		public static void createPnp(ArrayList<Priority_network> pnp, ArrayList<Petri_network> pn, ArrayList<Network> ns) {
			
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
			Priority_network toAdd = new Priority_network(pn.get(select), name);
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
		
	private static void setPriorities(Priority_network toSet) {
			for (Petri_transition pt : toSet.getTransitions()) {
				System.out.println("Inserisci la priorit? della transizione "+pt.getName() + " (1 per default)");
				pt.setPriority(Utility.readLowLimitInt(1));
			}
		}
		
		public static void simulaPriorityNet() {
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
		}

}