/**
 * 
 */
package com.carl.pongspiel.client;

import com.carl.pongspiel.client.model.Difficulty;
import com.carl.pongspiel.client.presenter.LoginPresenter;
import com.carl.pongspiel.client.presenter.PongPresenter;
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

	public void onModuleLoad() {
		initPongView();
		initLoginView();
		loginPresenter.go(RootPanel.get("container"));
	}

	public void buildGamePong(Difficulty difficulty) {
		pongPresenter.go(RootPanel.get("container"));
		pongPresenter.buildGamePong(1024, 512, 8, difficulty);
	}
	
	public void initLoginView(){
		loginView = new LoginViewImpl();
		loginPresenter = new LoginPresenter(loginView, this);
	}
	
	public void initPongView(){
		pongView = new PongViewImpl();
		pongPresenter = new PongPresenter(pongView, this);
	}

}
