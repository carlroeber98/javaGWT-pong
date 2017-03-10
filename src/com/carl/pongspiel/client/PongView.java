package com.carl.pongspiel.client;

import com.google.gwt.user.client.ui.Widget;

public interface PongView {
	public interface Presenter{

		void checkUser(String username, String password);
		
	}; 

	void setPresenter(PongPresenter presenter);

	Widget asWidget();

	public void buildGame();


}
