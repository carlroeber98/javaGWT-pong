package com.carl.pongspiel.client.view;

import com.carl.pongspiel.client.model.Difficulty;
import com.carl.pongspiel.client.presenter.LoginPresenter;
import com.carl.pongspiel.shared.model.UserPoints;
import com.google.gwt.user.client.ui.Widget;

public interface LoginView {
	public interface Presenter{
		
		void checkUserAcc(String username, String password);
		
		Boolean checkNewUsername(String username);

		void createNewUser(String username, String password, String passwordConfirm);

		void onLoadingFinished();

	}

	void setPresenter(LoginPresenter presenter);

	Widget asWidget();
	
	void welcomeUserLabel(UserPoints pointsPlayer);
	
	void userWarning(String warningMessage);
	
	void newUserLayout();

	void loginUserLabel();

	Difficulty getDifficulty();

	void loadingPage();
	
}
