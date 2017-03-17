package com.carl.pongspiel.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface UserServiceAsync {

	void checkUserAcc(String username, String password, AsyncCallback<Boolean> callback);
	
	void checkNewUsername(String username, AsyncCallback<Boolean> callback);

	void getUserHighscore(String username, AsyncCallback<Integer> callback);

	void createNewUser(String username, String password, AsyncCallback<Boolean> asyncCallback);
}
