package it.unibs.ingesw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public final class ReadN {
	
	private static final int ID_INIZIALE = -1;
	private static final String FILE_NET = "data.txt";
	private static final String FILE_PNET = "petri_data.txt";
	private static final String FILE_PNP = "pnp_data.txt";

	
	public static Object jsonToObject(String s, Class c ) {
		Gson gson = new Gson();
		return gson.fromJson(s,c);
	}
	
	public static Object loadFromSource(Class c, String source ) throws FileNotFoundException, IOException {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(source));
			String toLoad = reader.readLine();
			Gson gson = new Gson();
			return gson.fromJson(toLoad,c);
		} catch (FileNotFoundException e) {
			throw e;
		}
		catch (IOException e) {
			throw e;
		}
		
	}
	
	public static ArrayList<String> readFile(Class c)throws FileNotFoundException, IllegalArgumentException {
		String data;
		if (c == Network.class) 
			data = FILE_NET;
		else if (c == Petri_network.class)
			data = FILE_PNET;
		else if (c == Priority_network.class)
			data = FILE_PNP;
		else 
			throw new IllegalArgumentException("tipo non valido");
		
		String line;
		ArrayList<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(data))){
			line = reader.readLine();
			while(line != null) {
				lines.add(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static ArrayList<Integer> getNetIDsFromFile(Class c) {
		ArrayList<Integer> IDs = new ArrayList<>();
		try {
			ArrayList<String> nets = readFile(c);
			Gson gson = new Gson();
			for(String s : nets) {
				IDNameGiver net = (IDNameGiver) gson.fromJson(s, c);
				IDs.add(net.getId());
			}
		} catch (FileNotFoundException e) {
			IDs.add(ID_INIZIALE);
		}
		return IDs;
	}
	
	public static boolean checkIdExistence(int id, Class c) {
		for (Integer intero : getNetIDsFromFile(c))
			if(intero == id)
				return true;
		return false;
	}
	
	
	public static StringBuffer getNetNamesList(Class c) {
		StringBuffer names = new StringBuffer();
		try {
			ArrayList<String> nets = readFile(c);
			int i = 0;
			for(String s : nets) {
				IDNameGiver net = (IDNameGiver) jsonToObject(s,c);
				names.append(i+")"+net.getName()+"\n");
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}
	
	public static ArrayList<String> getNames(Class c) throws FileNotFoundException, IllegalArgumentException {
		ArrayList<String> nets = readFile(c);
		ArrayList<String> names = new ArrayList<>();
		for(String s : nets) {
			IDNameGiver n = (IDNameGiver) jsonToObject(s, c);
			names.add(n.getName());
		}
		return names;
	}
	
	public static boolean checkNetNameExistence(String name, Class c) throws FileNotFoundException{
		for (String string : getNames(c)) {
			if (name.toUpperCase().equals(string.toUpperCase()))
				return true;
		}
		return false;
	}
	

	
}
