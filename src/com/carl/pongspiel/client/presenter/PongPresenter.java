package com.carl.pongspiel.client.presenter;

import java.util.Random;

import com.carl.pongspiel.client.UserService;
import com.carl.pongspiel.client.view.PongView;
import com.carl.pongspiel.client.view.PongViewImpl;
import com.carl.pongspiel.shared.model.PlayerType;
import com.carl.pongspiel.shared.model.UserPoints;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author Carl
 *
 */
public class PongPresenter implements PongView.Presenter, EntryPoint {

	private PongView pongView;
	private UserPoints pointsPlayer = new UserPoints(0, PlayerType.PLAYER);
	UserPoints pointsBot = new UserPoints(0, PlayerType.BOT);
	
	Boolean booleanOut;
	
	int randomXDirection = 0;
	int	randomYDirection = 0; 
	

	@Override
	public void onModuleLoad() {

		Window.setTitle("Pong");

		pongView = new PongViewImpl();
		pongView.setPresenter(this);

		go(RootPanel.get("container"));

	}

	public void go(RootPanel container) {
		container.clear();
		container.add(pongView.asWidget());
	}

	public void buildGameField() {
		pongView.setPoints(pointsPlayer.getPoints(), pointsBot.getPoints());
		pongView.addKeyHandlers();
		pongView.buildGameField(1024, 512);
	}

	public void startGame() {
		pongView.setStartButtonVisible(false);
		
		while (randomXDirection == 0 && randomYDirection == 0)
			getRandomDirections(-2, 2);
		
		int ballSpeed;
		
		ballSpeed = pongView.getDifficulty().getballSpeed();
		
		pongView.setbatSpeed();
		pongView.moveBall(randomXDirection, randomYDirection, ballSpeed);
}
	
	public void getRandomDirections(int min, int max){
		randomXDirection = (int) random(min, max);
		randomYDirection = (int) random(min, max);
		GWT.log("xDir " + randomXDirection + "yDir " + randomYDirection);
	}
	
	public void pointsPlusOne(PlayerType type) {
		if(type.equals(PlayerType.PLAYER)){
			pointsPlayer.setPoints(pointsPlayer.getPoints() + 1);
		}
		else{
			pointsBot.setPoints(pointsBot.getPoints() + 1);
		}
		pongView.setPoints(pointsPlayer.getPoints(), pointsBot.getPoints());
		
		pongView.resetGameElements();
		pongView.setStartButtonVisible(true);
	}
	
	public void reboundBat(int xDir){
		xDir = xDir * -1;
		int yDir  = random(-2, 2);
		pongView.setXYDirection(xDir, yDir);
		GWT.log("xDir: " + xDir + " yDir: "+ yDir);
	}
	
	public int reboundTopBottom(int yDir){
		return yDir * -1;
	}

	private int random(int min, int max) {
		Random r = new Random();
		int Result = r.nextInt(max-min) + min;
		return Result;
	}
	
	public Boolean checkNewUsername(String username){
		if (username != null && !username.isEmpty()) {
			String pattern = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?]";
			RegExp regExp = RegExp.compile(pattern);
			MatchResult matcher = regExp.exec(username);
			boolean matchFound = matcher != null;
			if (!matchFound){
				UserService.Util.getInstance().checkNewUsername(username, new AsyncCallback<Boolean>() {
					
					@Override
					public void onFailure(Throwable caught) {
						pongView.userWarning("Failure loading database.");
						GWT.log("Failure loading database.");
					}
	
					@Override
					public void onSuccess(Boolean result) {
						if (!result){
							pongView.newUserLayout();
							pongView.userWarning("Bitte geben Sie ein Passwort ein.");
							booleanOut = true;
						}
						else{
							pongView.userWarning("Der Username existiert bereits. Bitte wählen Sie einen anderen Benutzernamen");
							booleanOut = false;
						}
					}
					
				});
			}
			else{
				pongView.userWarning("Der Username darf keine Sonderzeichen enthalten!");
				return false;
			}
		}
		else{
			pongView.userWarning("Der Username darf nicht leer sein!");
			return false;
		}
		return booleanOut;
	}

	public void createNewUser(String username, String password, String passwordConfirm){
		if (checkNewUsername(username)){
			if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
				String pattern = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?]";
				RegExp regExp = RegExp.compile(pattern);
				MatchResult matcher = regExp.exec(username);
				boolean matchFound = matcher != null;
				if (!matchFound){
					if (password.equals(passwordConfirm)){
						UserService.Util.getInstance().createNewUser(username, password, new AsyncCallback<Boolean>() {
			
							@Override
							public void onFailure(Throwable caught) {
								pongView.userWarning("Failure loading database.");
								GWT.log("Failure loading database.");
							}
			
							@Override
							public void onSuccess(Boolean result) {
								if (result){
									pongView.loginUserLabel();
									pongView.userWarning("Ein neues Benutzerkonto wurde erstellt.");
								}
								else{ 
									pongView.userWarning("Fehler beim erstellen eines neuen Benutzerkontos.");
								}
							}
							
						});
					}
					else{
						pongView.userWarning("Die Passwörter stimmen nicht überein!");
					}
				}
				else{
					pongView.userWarning("Der Username darf keine Sonderzeichen enthalten!");
				}
			}
			else{
				pongView.userWarning("Der Username und das Passwort dürfen nicht leer sein!");
			}
		}
	}
	
	public void checkUserAcc(final String username, String password) {
		if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
			String pattern = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?]";
			RegExp regExp = RegExp.compile(pattern);
			MatchResult matcher = regExp.exec(username);
			boolean matchFound = matcher != null;
			if (!matchFound){
				UserService.Util.getInstance().checkUserAcc(username, password, new AsyncCallback<Boolean>() {
	
					@Override
					public void onFailure(Throwable caught) {
						pongView.userWarning("Failure loading database.");
						GWT.log("Failure loading database.");
					}
	
					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							pointsPlayer.setUsername(username);
							getUserHighscore(username);
						} 
						else {
							pongView.userWarning("Der Username oder das Passwort ist falsch!");
						}
					}
				});
			}
			else{
				pongView.userWarning("Der Username darf keine Sonderzeichen enthalten!");
			}
		}
		else{
			pongView.userWarning("Der Username und das Passwort dürfen nicht leer sein!");
		}
	}

	/**
	 * --> 
	 * @param username
	 */
	public void getUserHighscore(String username) {
		UserService.Util.getInstance().getUserHighscore(username, new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Failure loading database");
				pongView.welcomeUserLabel(pointsPlayer);
				buildGameField();
			}

			@Override
			public void onSuccess(Integer result) {
				pointsPlayer.setHighscrore(result);
				pongView.welcomeUserLabel(pointsPlayer);
				buildGameField();
			}
		});
	}		
	
}
