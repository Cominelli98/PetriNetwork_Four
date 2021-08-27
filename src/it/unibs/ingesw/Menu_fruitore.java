package it.unibs.ingesw;

public final class Menu_fruitore {
	
	final static String MENU_FRUITORE[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:simula una rete di petri",
			"2:sinula una rete di petri prioritizzata",
			"0:Indietro",
			"___________________________"};
	
	public static void menuFriutore(){
		int select = -1;
		do {
			printMenu(MENU_FRUITORE);
			select = Utility.readLimitedInt(0, MENU_FRUITORE.length-4);
			
			switch(select) {
			case 1:
				Menu_Petri.simulaPetri();
				break;
			case 2:
				Menu_Pnp.simulaPriorityNet();
				break;
			case 0:
				break;
			}
		}while(select != 0);
		
	}
	
	public static void printMenu(String[] toPrint) {
		for(String s : toPrint)
			System.out.println(s);
	}
}
