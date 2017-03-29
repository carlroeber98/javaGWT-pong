package com.carl.pongspiel.server;

import java.io.*;

import com.carl.pongspiel.client.UserService;
import com.carl.pongspiel.shared.model.UserPoints;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServiceImpl extends RemoteServiceServlet implements UserService {
	
	public Boolean checkUserAcc(String username, String password) {
		try {
			return accessUserAcc(username, password);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean checkNewUsername(String username){
		try {
			return checkUsername(username);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean createNewUser(String username, String password){
		try {
			return addNewUser(username, password);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Integer getUserHighscore(String username){
		try {
			return getHighscore(username);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean setNewHighscore(UserPoints playerType) {
		try {
			return addNewHighscore(playerType);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private Boolean checkUsername(String username) throws IOException{
		FileReader fileR = new FileReader("database/LoginDatabase.txt");
		BufferedReader bufR = new BufferedReader(fileR);
		String line;
		while ((line = bufR.readLine()) != null && !line.isEmpty()) {
			if (line.contains(";")) {
				String[] lineElements = line.split(";");
				String usernameTXT = lineElements[0];
				if (usernameTXT.equals(username)){
					bufR.close();
					System.out.println("LoginDatabase: User already exists.");
					return true;
				}
			}
		}
		bufR.close();
		System.out.println("LoginDatabase: User not found.");
		return false;
	}
	
	private Boolean addNewUser(String username, String password) throws IOException {
		File file = new File("database/Logindatabase.txt");
		Writer writer = new FileWriter(file, true);
		writer.write(username + ";" + password);
		writer.write(System.getProperty("line.separator"));
		writer.flush();
		writer.close();
		System.out.println("LoginDatabase: New user added.");
		return true;
	}

	private Boolean accessUserAcc(String username, String password) throws IOException{
		FileReader fileR = new FileReader("database/LoginDatabase.txt");
		BufferedReader bufR = new BufferedReader(fileR);
		String line;
		while ((line = bufR.readLine()) != null && !line.isEmpty()) {
			if (line.contains(";")) {
				String[] lineElements = line.split(";");
				String usernameTXT = lineElements[0];
				String passwordTXT = lineElements[1];
				if (usernameTXT.equals(username)){
					if (passwordTXT.equals(password)){
						bufR.close();
						System.out.println("LoginDatabase: User found. Password correct.");
						return true;
					}
					else{
						bufR.close();
						System.out.println("LoginDatabase: User found. Password incorrect.");
						return false;
						}
					}
				}
			}
		bufR.close();
		System.out.println("LoginDatabase: User not found.");
		return false;
	}
	
	private Integer getHighscore(String username)  throws IOException{
		FileReader fileR = new FileReader("database/HighscoreDatabase.txt");
		BufferedReader bufR = new BufferedReader(fileR);
		String line;
		while ((line = bufR.readLine()) != null && !line.isEmpty()) {
			if (line.contains(";")) {
				String[] lineElements = line.split(";");
				String usernameTXT = lineElements[0];
				Integer highscoreTXT = Integer.parseInt(lineElements[1]);
				if (usernameTXT.equals(username)){
					bufR.close();
					System.out.println("HighscoreDatabase: User found. Highscore loaded.");
					return highscoreTXT;
				}
			}
		}
		bufR.close();
		System.out.println("HighscoreDatabase: User not found.");
		return 0;
	}

	private Boolean addNewHighscore(UserPoints playerType) throws IOException{
		File file = new File("database/HighscoreDatabase.txt");
		Writer writer = new FileWriter(file, true);
		writer.write(playerType.getUsername() + ";" + playerType.getPoints());
		writer.write(System.getProperty("line.separator"));
		writer.flush();
		writer.close();
		System.out.println("HighscoreDatabase: New Highscore added.");
		return true;
	}
}
