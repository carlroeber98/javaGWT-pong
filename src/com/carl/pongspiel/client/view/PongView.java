package com.carl.pongspiel.client.view;

import com.carl.pongspiel.client.presenter.PongPresenter;
import com.carl.pongspiel.shared.model.PlayerType;
import com.google.gwt.user.client.ui.Widget;

public interface PongView {
	public interface Presenter{

		void startGame();

		void pointsPlusOne(PlayerType type);

		int reboundTopBottom(int yDirection);

		void reboundBat(int xDirection);

		void logout();

	}

	void setPresenter(PongPresenter presenter);

	Widget asWidget();

	Widget getBall();

	void setStartButtonVisible(boolean visible);

	void setPoints(int Player, int Bot);

	void moveBall(int xDir, int yDir, int speed);

	void resetGameElements();

	void addKeyHandlers();

	void setbatSpeed(int speed);

	void setXyDirection(int xDir, int yDir);
	
	void buildGameField(int width, int hight, int gameFieldBorder);

}
