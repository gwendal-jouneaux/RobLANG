package fr.gwendal_jouneaux.rob_lang.execute;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Utils {
	public static String fileToString(File f) {
		StringBuilder sb = new StringBuilder();
		try {
	      Scanner scan = new Scanner(f);
	      while (scan.hasNextLine()) {
	        String line = scan.nextLine();
	        sb.append(line+"\n");
	      }
	      scan.close();
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } 
	    return sb.toString();
	}
	
	public static Color colorFromString(String color) {
		Color out = null;
		switch(color) {
		case "RED":
			out = Color.RED;
			break;
		case "GREEN":
			out = Color.GREEN;
			break;
		case "BLUE":
			out = Color.BLUE;
			break;
		default:
			out = Color.BLACK;
		}
		return out;
	}
	
	public static String colortoString(Color color) {
		if(color == null) return "GROUND";
		if(color.equals(Color.RED)) return "RED";
		if(color.equals(Color.GREEN)) return "GREEN";
		if(color.equals(Color.BLUE)) return "BLUE";
		return "GROUND";
	}
}
