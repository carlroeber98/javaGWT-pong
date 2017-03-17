package com.carl.pongspiel.client.view;

import com.carl.pongspiel.client.model.Difficulty;
import com.carl.pongspiel.client.presenter.PongPresenter;
import com.carl.pongspiel.shared.model.PlayerType;
import com.carl.pongspiel.shared.model.UserPoints;
import com.google.gwt.user.client.ui.Widget;

public interface PongView {
	public interface Presenter{

		void checkUserAcc(String username, String password);

		void startGame();

		void pointsPlusOne(PlayerType type);

		int reboundTopBottom(int yDirection);

		void reboundBat(int xDirection);

		Boolean checkNewUsername(String text);

		void createNewUser(String text, String text2, String text3);
		
	}

	void setPresenter(PongPresenter presenter);

	Widget asWidget();

	Widget getBall();

	void setStartButtonVisible(boolean visible);

	void buildGameField(int width, int height);

	void setPoints(int Player, int Bot);

	void welcomeUserLabel(UserPoints pointsPlayer);

	void moveBall(int xDir, int yDir, int speed);

	void userWarning(String warningMessage);

	void resetGameElements();

	void addKeyHandlers();

	void setbatSpeed();

	void newUserLayout();

	void loginUserLabel();

	Difficulty getDifficulty();

	void setXYDirection(int xDir, int yDir);
	

}
