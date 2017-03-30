package com.carl.pongspiel.shared;

public class Languages {
	
	public static boolean de = true;
	public static boolean en = false;
	
	public static String send(){
		if (de)
			return "Senden";
		else
			return "Send";
	}
	
	public static String start(){
		if (de)
			return "Starten";
		else
			return "Start";
	}
	
	public static String pause(){
		if (de)
			return "Pause";
		else
			return "Break";
	}
	
	public static String continue2(){
		if (de)
			return "Fortsetzen";
		else
			return "Continue";
	}
}
