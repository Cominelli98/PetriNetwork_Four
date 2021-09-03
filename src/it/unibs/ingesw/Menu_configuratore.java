package it.unibs.ingesw;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public final class Menu_configuratore {
	
	private final static String MENU_CONFIGURATORE[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:crea una nuova rete",
			"2:Crea una nuova rete di petri",
			"3:crea una nuova rete di petri priorizzata",
			"4:Visualizza una rete",
			"5:Salva una o più reti",
			"6:Carica una rete da file",
			"0:Indietro",
			"___________________________"};
	
	private final static String SCELTA_VISUALIZZAZIONE[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:visualizza una rete",
			"2:visualizza una rete di petri",
			"3:visualizza una rete di petri priorizzata",
			"0:Indietro",
			"___________________________"};
	
	private final static String SCELTA_SALVATAGGIO[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:salvare una rete",
			"2:salvare una rete di petri",
			"3:salvare una rete di petri priorizzata",
			"0:Indietro",
			"___________________________"};
	
	private final static String SCELTA_CARICA_DA_FILE[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:carica un rete",
			"2:carica un rete di petri",
			"3:carica una rete di petri priorizzata",
			"0:Indietro",
			"___________________________"};
	
	private final static String SOURCE = "inserisci l'indirizzo del file";

	private static final String ERRORE_INDIRIZZO_SBAGLIATO = "file non trovato";
	private static final String ERRORE_CARICAMENTO ="errore nel caricamento della rete";
	private static final String PADRE_NON_PRESENTE = "rete di base non presente";
	private static final String PADRE_P_NON_PRESENTE = "rete di petri padre non presente";
	private static final String GIA_PRESENTE = "già presente";
	
	public static void configuratore() {
		int select = -1;
		do {
			printMenu(MENU_CONFIGURATORE);
			select = Utility.readLimitedInt(0, MENU_CONFIGURATORE.length-4);
			switch(select) {
			case 1:
				Menu_Reti.createNetwork(Menu.getCurrentNetwork(), Menu.getNetworks());
				break;
			case 2:
				Menu_Petri.createPetri(Menu.getPetriNetworks(), Menu.getNetworks());
				break;
			case 3:
				Menu_Pnp.createPnp(Menu.getPriorityNetworks(), Menu.getPetriNetworks(), Menu.getNetworks());
				break;
			case 4:
				visualizationOption();
				break;
			case 5:
				saveOption();
				break;
			case 6:
				loadOption();
				break;
			case 0:
				break;
			}
		}while(select != 0);
	}
	
	private static void saveOption() {
		int select = -1;
		do {
			printMenu(SCELTA_SALVATAGGIO);
			select = Utility.readLimitedInt(0, SCELTA_SALVATAGGIO.length-4);
			switch(select) {
			case 1:
				Menu_Salva.saveOption(Menu.getNetworks());
				break;
			case 2:
				Menu_Salva.pSaveOption(Menu.getPetriNetworks());
				break;
			case 3:
				Menu_Salva.pnpSaveOption(Menu.getPriorityNetworks());
				break;
			case 0:
				break;
			}
		}while(select != 0);
	}

	private static void visualizationOption() {
		int select = -1;
		do {
			printMenu(SCELTA_VISUALIZZAZIONE);
			select = Utility.readLimitedInt(0, SCELTA_VISUALIZZAZIONE.length-4);
			switch(select) {
			case 1:
				 Menu_Visua.netViewer(Menu.getNetworks());
				break;
			case 2:
				 Menu_Visua.petriNetViewer(Menu.getPetriNetworks());
				break;
			case 3:
				 Menu_Visua.pnpViewer(Menu.getPriorityNetworks());
				break;
			case 0:
				break;
			}
		}while(select != 0);
		
	}
	
	private static void loadOption() {
		int select = -1;
		do {
			printMenu(SCELTA_CARICA_DA_FILE);
			select = Utility.readLimitedInt(0, SCELTA_VISUALIZZAZIONE.length-4);
			try {
				switch(select) {
				case 1:
					System.out.println(SOURCE);
					Network n = load(Network.class, Utility.readString());
					if(!checkLoadedValidity(n)) {
						System.out.println("rete caricata non corretta");
						return;
					}
					Menu.addNetwork(n);
					System.out.println("rete "+n.getName()+" aggiunta");
					break;
				case 2:
					System.out.println(SOURCE);
					Petri_network pn = load(Petri_network.class, Utility.readString());
					if(!checkLoadedValidity(pn)) {
						System.out.println("rete caricata non corretta");
						return;
					}
					Menu.addPetriNetwork(pn);
					System.out.println("rete di petri "+pn.getName()+" aggiunta");
					break;
				case 3:
					System.out.println(SOURCE);
					Priority_network pnp = load(Priority_network.class, Utility.readString());
					if(!checkLoadedValidity(pnp)) {
						System.out.println("rete caricata non corretta");
						return;
					}
					Menu.addPNPNetwork(pnp);
					System.out.println("rete "+pnp.getName()+" aggiunta");
					break;
				case 0:
					break;
				}
			}
			catch(FileNotFoundException e) {
				System.out.println(ERRORE_INDIRIZZO_SBAGLIATO);
			}
			catch(IOException e) {
				System.out.println(ERRORE_CARICAMENTO);
			}
			
		}while(select != 0);
	} 
	
	private static <T extends IDNameGiver> T  load(Class c, String source) throws FileNotFoundException, IOException {
		try {
			return (T) ReadN.loadFromSource(c, source);
		}  catch (FileNotFoundException e) {
			throw e;
		}
		catch (IOException e) {
			throw e;
		}
	}

	public static void printMenu(String [] toPrint) {
		for(String s :toPrint)
			System.out.println(s);
	}
	
	private static boolean checkLoadedValidity(Network n) {
		if(n.getLocations() == null || n.getTransitions() == null || n.getNetLinks() == null)
			return false;
		if(checkExistence(n)) {
			System.out.println(GIA_PRESENTE);
			return false;
		}
		return true;
	}
	
	private static boolean checkLoadedValidity(Petri_network pn) {
		if(pn.getLocations() == null || pn.getTransitions() == null || pn.getLinks() == null)
			return false;
		for(Petri_transition pt : pn.getTransitions()) {
			if(pt.getPriority() != -1)
				return false;
		}
		if(!checkFather(pn)) {
			System.out.println(PADRE_NON_PRESENTE);
			return false;
		}
		if(checkExistence(pn)) {
			System.out.println(GIA_PRESENTE);
			return false;
		}
		return true;
	}
	
	private static boolean checkLoadedValidity(Priority_network pnp) {
		if(pnp.getLocations() == null || pnp.getTransitions() == null || pnp.getLinks() == null)
			return false;
		for(Petri_transition pt : pnp.getTransitions()) {
			if(pt.getPriority() < 1)
				return false;
		}
		if(!checkFather(pnp)) {
			System.out.println(PADRE_P_NON_PRESENTE);
			return false;
		}
		if(checkExistence(pnp)) {
			System.out.println(GIA_PRESENTE);
			return false;
		}
		return true;
	}
	
	private static boolean checkFather(Petri_network pn) {
		for(Network n : Menu.getNetworks()) {
			if(n.getId() == pn.getFatherNetId())
				return true;
		}
		return false;
	}
	
	private static boolean checkFather(Priority_network pnp) {
		for(Petri_network pn : Menu.getPetriNetworks()) {
			if(pn.getId() == pnp.getPriorityNetID())
				return true;
		}
		return false;
	}
	
	private static boolean checkExistence(Network n) {
		for(int i : Menu.netIDs()) {
			if(i == n.getId())
				return true;
		}
		return false;
	}
	
	private static boolean checkExistence(Petri_network pn) {
		for(int i : Menu.pNetIDs()) {
			if(i == pn.getId())
				return true;
		}
		return false;
	}
	
	private static boolean checkExistence(Priority_network pnp) {
		for(int i : Menu.pnpNetIDs()) {
			if(i == pnp.getId())
				return true;
		}
		return false;
	}
}


