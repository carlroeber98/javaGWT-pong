/**
 * 
 */
package com.carl.pongspiel.client;

import com.carl.pongspiel.client.model.Color;
import com.carl.pongspiel.client.model.Difficulty;
import com.carl.pongspiel.client.presenter.LoginPresenter;
import com.carl.pongspiel.client.presenter.PongPresenter;
import com.carl.pongspiel.client.ui.GamePreferences;
import com.carl.pongspiel.client.ui.growl.Animation;
import com.carl.pongspiel.client.ui.growl.Growl;
import com.carl.pongspiel.client.ui.growl.GrowlOptions;
import com.carl.pongspiel.client.ui.growl.GrowlType;
import com.carl.pongspiel.client.view.LoginView;
import com.carl.pongspiel.client.view.LoginViewImpl;
import com.carl.pongspiel.client.view.PongView;
import com.carl.pongspiel.client.view.PongViewImpl;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Application controller (main presenter) class for handling application level
 * events, history management and view transition logic. This class handles the
 * logic that is not specific to any presenter.
 * 
 * @author Carl
 *
 */

public class AppController implements EntryPoint {
	
	private LoginView loginView;
	private LoginPresenter loginPresenter;
	private PongView pongView;
	private PongPresenter pongPresenter;

	public boolean newView = false;
	
	public void onModuleLoad() {
		initGrowl();
		initPongView();
		initLoginView();
	}

	private void initGrowl() {
		Growl.setContainer(RootPanel.get("growlContainer"));
	}

	public void buildGamePong(Difficulty difficulty) {
		if (newView)
			initPongView();
		pongPresenter.go(RootPanel.get("container"));
		GamePreferences gamePreferences = new GamePreferences(); 
		gamePreferences = getGamePreferences();
		gamePreferences.setDifficulty(difficulty);
		pongPresenter.buildGamePong(gamePreferences);
	}
	
	public void initLoginView() {
		loginView = new LoginViewImpl();
		loginPresenter = new LoginPresenter(loginView, this);
		loginPresenter.go(RootPanel.get("container"));
	}
	
	public void initPongView() {
		pongView = new PongViewImpl();
		pongPresenter = new PongPresenter(pongView, this);
	}
	
	public GamePreferences getGamePreferences() {
		//Menü
		GamePreferences gamePreferences = new GamePreferences();
		gamePreferences.setGameFieldBackgroundColor(Color.BLACK);
		gamePreferences.setGameFieldBorderColor(Color.BLUE);
		gamePreferences.setBallColor(Color.WHITE);
		gamePreferences.setBatPlayer1Color(Color.PURPLE);
		gamePreferences.setBatPlayer2Color(Color.PURPLE);
		gamePreferences.setLabelPointsColor(Color.WHITE);
		gamePreferences.setButtonBackgroundColor(Color.WHITE);
		gamePreferences.setButtonFontColor(Color.PURPLE);
		return gamePreferences;
	}
	

	public void setNewView(boolean bool){
		newView = bool;
	}
	
	public void showInfo(String infoText) {
		showGrowl(GrowlType.INFO, true, infoText);
	}

	public void showSuccess(String infoText) {
		showGrowl(GrowlType.SUCCESS, true, infoText);
	}

	public void showWarning(String warningMessage) {
		showGrowl(GrowlType.WARNING, true, warningMessage);
	}

	public void showError(final String errorMessage) {
		showGrowl(GrowlType.DANGER, false, errorMessage);
	}

	private static void showGrowl(GrowlType type, boolean allowDismiss, String message) {
		try {
			final GrowlOptions growlOpts = new GrowlOptions();
			growlOpts.setType(type);
			growlOpts.setAnimation(Animation.FADE_IN_UP_BIG, Animation.FADE_OUT_DOWN_BIG);
			new Growl(growlOpts).growl(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}