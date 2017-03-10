package com.carl.pongspiel.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author Carl
 *
 */
public class PongPresenter implements PongView.Presenter, EntryPoint{

	private PongView pongView;
	
	@Override
	public void onModuleLoad() {
		
		Window.setTitle("Pong");
		
		pongView = new PongViewImpl();
		pongView.setPresenter(this);
		
		RootPanel.get("container").add(pongView.asWidget());
		
		
//		HTMLPanel container = HTMLPanel.wrap(Document.get().getElementById("container"));
//		container.getElement().getStyle().setTextAlign(TextAlign.CENTER);
//		container.getElement().getStyle().setLineHeight(50, Unit.PX);
//		container.getElement().getStyle().setBackgroundColor("red");
//		container.getElement().getStyle().setMarginTop(30, Unit.PX);
//		container.getElement().getStyle().setMarginLeft(200, Unit.PX);
//		Text testText = new Text("Username: ");
//		container.add(testText);
	}

	@Override
	public void checkUser(String username, String password) {
		UserService.Util.getInstance().checkUser(username, password, new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				pongView.buildGame();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				return;
				
			}
		});
	}

}


