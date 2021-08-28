package it.unibs.ingesw;

import java.util.ArrayList;

public final class Menu_Salva {

	private static final String MENUSALVA[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Salva una rete",
			"2:Salva tutte le reti",
			"0:Indietro",
			"___________________________"};
	
	private static final String SALVATAGGIO = "Salvataggio eseguito";
	private static final String RICHIESTA_SALVATAGGIO = "Quale rete vuoi salvare?";
	private static final String NO_RETI = "non ci sono reti da salvare";
	
	/**
	 * Metodo dedicato al salvataggio delle reti su file, permette di salvare una rete unica o tutte quelle create.
	 */
	public static void saveOption(ArrayList<Network> ns) {
		if(ns.size() == 0) {
			System.out.println(NO_RETI);
			return;
		}
		int select = -1;
		for (String s : MENUSALVA)
			System.out.println(s);
		select = Utility.readLimitedInt(0, MENUSALVA.length-4);
		switch (select) {
		case 0:
			break;
		case 1:
			System.out.println(RICHIESTA_SALVATAGGIO);
			System.out.println(Menu_Visua.getNetworksList(ns));
			int i = Utility.readLimitedInt(0, ns.size());
			saveNetOnFile(ns.get(i));
			break;
		case 2:
			for (Network n : ns)
				saveNetOnFile(n);
			break;
		}
	}
	
	public static void pSaveOption(ArrayList<Petri_network> pn) {
		if(pn.size() == 0) {
			System.out.println(NO_RETI);
			return;
		}
		int select = -1;
		for (String s : MENUSALVA)
			System.out.println(s);
		select = Utility.readLimitedInt(0, MENUSALVA.length-4);
		switch (select) {
		case 0:
			break;
		case 1:
			System.out.println(RICHIESTA_SALVATAGGIO);
			System.out.println(Menu_Visua.getPNetworksList(pn));
			int i = Utility.readLimitedInt(0, pn.size());
			saveNetOnFile(pn.get(i));
			break;
		case 2:
			for (Petri_network n : pn)
				saveNetOnFile(n);
			break;
		}
	}
	
	public static void pnpSaveOption(ArrayList<Priority_network> pnp) {
		if(pnp.size() == 0) {
			System.out.println(NO_RETI);
			return;
		}
		int select = -1;
		for (String s : MENUSALVA)
			System.out.println(s);
		select = Utility.readLimitedInt(0, MENUSALVA.length-4);
		switch (select) {
		case 0:
			break;
		case 1:
			System.out.println(RICHIESTA_SALVATAGGIO);
			System.out.println(Menu_Visua.getPnpList(pnp));
			int i = Utility.readLimitedInt(0, pnp.size());
			saveNetOnFile(pnp.get(i));
			break;
		case 2:
			for (Priority_network n : pnp)
				saveNetOnFile(n);
			break;
		}
	}
	
	

	/**
	 * Metodo che richiama dalla classe statica WriteN il salvataggio su file delle reti create
	 */
	private static void saveNetOnFile(IDNameGiver n){
		if (!ReadN.checkIdExistence(n.getId(), n.getClass()))
			WriteN.save(n);
		System.out.println(SALVATAGGIO + " rete " + n.getName());
	}
	
}
