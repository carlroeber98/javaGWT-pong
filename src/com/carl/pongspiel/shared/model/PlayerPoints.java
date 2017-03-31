package com.carl.pongspiel.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PlayerPoints implements IsSerializable{
	private String username = "";
	private PlayerType playerType; 
	private int points = 0;
	private int highscrore = 0;
	
	public PlayerPoints(){
		//
	}
	
	public PlayerPoints(String username, PlayerType playerType, int points, int highscore){
		this.setUsername(username);
		this.setPlayerType(playerType);
		this.setPoints(points);
		this.setHighscrore(highscore);
	}
	
	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}
	
	public PlayerType getPlayerType() {
		return playerType;
	}

	public PlayerPoints(int points, PlayerType playerType){
		this.setPoints(points);
		this.setPlayerType(playerType);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getHighscrore() {
		return highscrore;
	}

	public void setHighscrore(int highscrore) {
		this.highscrore = highscrore;
	}
}
