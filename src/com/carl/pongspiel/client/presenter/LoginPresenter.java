package com.carl.pongspiel.client.presenter;

import com.carl.pongspiel.client.AppController;
import com.carl.pongspiel.client.UserService;
import com.carl.pongspiel.client.view.LoginView;
import com.carl.pongspiel.shared.model.PlayerType;
import com.carl.pongspiel.shared.model.UserPoints;
import com.google.gwt.core.client.GWT;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author Carl
 *
 */
public class LoginPresenter implements LoginView.Presenter {

	private LoginView loginView;
	private AppController appController;
	
	
	public LoginPresenter(LoginView loginView, AppController appController){
		
		this.appController = appController;
		
		loginView.setPresenter(this);
		this.loginView = loginView;

	}

	public void go(RootPanel container) {
		container.clear();
		container.add(loginView.asWidget());
	}
	
	/**
	 * Variables
	 */
	private boolean booleanOut;
	private UserPoints pointsPlayer = new UserPoints(0, PlayerType.PLAYER);
	UserPoints pointsBot = new UserPoints(0, PlayerType.BOT);

	/**
	 * ->check the username in Database
	 * ->if already existing return true else return false
	 * 
	 * 
	 */
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
						loginView.userWarning("Failure loading database.");
						GWT.log("Failure loading database.");
					}
	
					@Override
					public void onSuccess(Boolean result) {
						if (!result){
							loginView.newUserLayout();
							loginView.userWarning("Bitte geben Sie ein Passwort ein.");
							booleanOut = true;
						}
						else{
							loginView.userWarning("Der Username existiert bereits. Bitte wählen Sie einen anderen Benutzernamen");
							booleanOut = false;
						}
					}
					
				});
			}
			else{
				loginView.userWarning("Der Username darf keine Sonderzeichen enthalten!");
				return false;
			}
		}
		else{
			loginView.userWarning("Der Username darf nicht leer sein!");
			return false;
		}
		return booleanOut;
	}

	/**
	 * ->create a new User with the password
	 * ->if new user added return true else return false
	 * 
	 * 
	 */
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
								loginView.userWarning("Failure loading database.");
								GWT.log("Failure loading database.");
							}
			
							@Override
							public void onSuccess(Boolean result) {
								if (result){
									loginView.loginUserLabel();
									loginView.userWarning("Ein neues Benutzerkonto wurde erstellt.");
								}
								else{ 
									loginView.userWarning("Fehler beim erstellen eines neuen Benutzerkontos.");
								}
							}
							
						});
					}
					else{
						loginView.userWarning("Die Passwörter stimmen nicht überein!");
					}
				}
				else{
					loginView.userWarning("Der Username darf keine Sonderzeichen enthalten!");
				}
			}
			else{
				loginView.userWarning("Der Username und das Passwort dürfen nicht leer sein!");
			}
		}
	}
	
	/**
	 * ->if the username matches with the password return true, else return false
	 * 
	 * 
	 */
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
						loginView.userWarning("Failure loading database.");
						GWT.log("Failure loading database.");
					}
	
					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							pointsPlayer.setUsername(username);
							getUserHighscore(username);
						} 
						else {
							loginView.userWarning("Der Username oder das Passwort ist falsch!");
						}
					}
				});
			}
			else{
				loginView.userWarning("Der Username darf keine Sonderzeichen enthalten!");
			}
		}
		else{
			loginView.userWarning("Der Username und das Passwort dürfen nicht leer sein!");
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
				loginView.welcomeUserLabel(pointsPlayer);
				loginView.loadingPage();
			}

			@Override
			public void onSuccess(Integer result) {
				pointsPlayer.setHighscrore(result);
				loginView.welcomeUserLabel(pointsPlayer);
				loginView.loadingPage();
			}
		});
	}

	public void onLoadingFinished(){
		appController.buildGamePong(loginView.getDifficulty());
	}


	
}
