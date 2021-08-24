package it.unibs.ingesw;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Menu {
	
	private final String SELEZIONE = "Seleziona: ";
	
	private final String MENUSTART[] = {
			"MENU:",
			"___________________________",
			"1:Crea una rete e gestiscila",
			"2:Gestisci reti di Petri",
			"0:Esci",
			"___________________________",
			
	};
	
	private Network currentNetwork;
	private ArrayList<Network> networks;
	private ArrayList<Petri_network> petriNetworks;
	
	/**
	 * Costruisce un men� inizializzando l'array di reti e creando, se non esiste ancora, il file su cui verranno
	 * salvate
	 */
	public Menu() {
		
		networks = new ArrayList<>();
		petriNetworks = new ArrayList<>();
		WriteN.fileCreation();
		Network.network_id = Utility.getMax(ReadN.getNetIDsFromFile(Network.class));
		Petri_network.petriNetworkId = Utility.getMax(ReadN.getNetIDsFromFile(Petri_network.class));
	}
	
	/**
	 *metodo principale di avvio del men�, switch che richiama tutte le funzionalit�, 0 per uscire
	 */
	public void startMenu() {
		loadSavedNets();
		int select = -1;
		do {
			for (String s : MENUSTART) {
				System.out.println(s);
			}
			
			select = Utility.readLimitedInt(0, MENUSTART.length-4);
			switch (select) {
				case 1:
					Menu_Reti.createNetwork(currentNetwork, networks);
					break;
				case 2:
//					if(networks.size() != 0)
//						saveOption();
//					else
//						System.out.println("Non ci sono reti da salvare");
//					break;
//				case 4:
					//createPetri();
					Menu_Petri.petriMenu(petriNetworks, networks);
					break;
				case 0:
					Utility.close();
					break;
			}
		}while(select != 0);
		
	}
	
	public void loadSavedNets() {
		ArrayList<String> n = new ArrayList<String>();
		ArrayList<String> pn = new ArrayList<String>();
			try {
				n = ReadN.readFile(Network.class);
				pn = ReadN.readFile(Petri_network.class);

			} catch (FileNotFoundException | IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(String s : n) 
				networks.add((Network) ReadN.jsonToObject(s, Network.class));
			for(String s : pn)
				petriNetworks.add((Petri_network) ReadN.jsonToObject(s, Petri_network.class));
	}
}
	