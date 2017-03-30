/**
 * 
 */
package com.carl.pongspiel.client;

import com.carl.pongspiel.client.model.Color;
import com.carl.pongspiel.client.model.Difficulty;
import com.carl.pongspiel.client.presenter.LoginPresenter;
import com.carl.pongspiel.client.presenter.PongPresenter;
import com.carl.pongspiel.client.ui.GamePreferences;
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
		initPongView();
		initLoginView();
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
		gamePreferences.setGameFieldWidth(1024);
		gamePreferences.setGameFieldHeight(512);
		gamePreferences.setGameFieldBackgroundColor(Color.BLACK);
		gamePreferences.setGameFieldBorder(8);
		gamePreferences.setGameFieldBorderColor(Color.BLUE);
		gamePreferences.setBallHeight(10);
		gamePreferences.setBallWidth(10);
		gamePreferences.setBallColor(Color.YELLOW);
		gamePreferences.setBatPlayer1PositionLeft(20);
		gamePreferences.setBatPlayer1Height(100);
		gamePreferences.setBatPlayer1Width(8);
		gamePreferences.setBatPlayer1Color(Color.BLUE);
		gamePreferences.setBatPlayer2PositionRight(15);
		gamePreferences.setBatPlayer2Height(100);
		gamePreferences.setBatPlayer2Width(8);
		gamePreferences.setBatPlayer2Color(Color.BLUE);
		gamePreferences.setLabelPointsColor(Color.WHITE);
		gamePreferences.setButtonBackgroundColor(Color.WHITE);
		gamePreferences.setButtonFontColor(Color.BLACK);
		return gamePreferences;
	}
	
	public void setNewView(boolean bool){
		newView = bool;
	}
	
}