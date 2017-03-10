package com.carl.pongspiel.server;

import java.io.*;

import com.carl.pongspiel.client.UserService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServiceImpl extends RemoteServiceServlet implements UserService {

	@Override
	public Boolean checkUser(String username, String password) {
		String included = "";
		try {
			included = searchUser(username, password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (included.equals("newUser")){
			return false;
		}
		else{
		return true;
		}
	}

	private void addUser(String username, String password) throws IOException {
		File file = new File("database/database.txt");
		Writer writer = new FileWriter(file, true);
		writer.write(username + ";" + password);
		writer.write(System.getProperty("line.separator"));
		writer.flush();
		writer.close();
	
	}

	private String searchUser(String username, String password) throws IOException{
//		FileReader fileR = new FileReader("database/database.txt");
//		BufferedReader bufR = new BufferedReader(fileR);
//		while(bufR.readLine() != null){
//			String[] line = bufR.readLine().split(";");
//			String usernameTXT = line[0];
//			String passwordTXT = line[1];
//			if (usernameTXT.equals(username) && passwordTXT.equals(password)){
//				bufR.close();
//				return "oldUser";
//			}
//		}
//		bufR.close();
		addUser(username, password);
		return "newUser";
	}
}
	
