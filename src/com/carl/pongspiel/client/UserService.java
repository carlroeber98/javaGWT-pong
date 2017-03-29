package com.carl.pongspiel.client;

import com.carl.pongspiel.shared.model.UserPoints;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {
	
	public static class Util {
		private static UserServiceAsync instance;

		public static UserServiceAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(UserService.class);
			}
			return instance;
		}
	}
	
	Boolean checkUserAcc(String username, String password) ;
	
	Boolean checkNewUsername(String username);
	
	Boolean createNewUser(String username, String password);
	
	Integer getUserHighscore(String username) ;
	
	Boolean setNewHighscore(UserPoints playerType);
}
