package com.carl.pongspiel.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface UserServiceAsync {

	void checkUser(String username, String password, AsyncCallback<Boolean> callback);
}
