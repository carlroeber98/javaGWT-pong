package com.carl.pongspiel.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.RadioButton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class PongViewImpl extends Composite implements PongView {

	@UiTemplate("PongView.ui.xml")
	interface Pong_Spiel_CarliBinder extends UiBinder<Widget, PongViewImpl> {

		Widget createAndBindUi(PongViewImpl viewImpl);
	}

	private static Pong_Spiel_CarliBinder uiBinder = GWT.create(Pong_Spiel_CarliBinder.class);
	
	private PongView.Presenter presenter;
	
	@UiField
	RadioButton radioButtonVeryEasy;
	
	@UiField
	RadioButton radioButtonEasy;

	@UiField
	RadioButton radioButtonMedium;
	
	@UiField
	RadioButton radioButtonHeavy;
	
	@UiField
	RadioButton radioButtonVeryHeavy;
	
	@UiField
	HasText usernameField;

	@UiField
	HasText passwordField;
	
	@UiField
	Button sendButton;
	
	@UiField
	Container start;
	
	@UiField
	Container gameField;
	
	
	public PongViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("sendButton")
	public void onSendButtonClicked(ClickEvent e){
		presenter.checkUser(usernameField.getText(), passwordField.getText());
	}    
 
    
	@Override
	public void setPresenter(PongPresenter presenter) {
		this.presenter = presenter;
	}
	
	public void buildGame(){
		start.setVisible(false);
		gameField.setVisible(true);
	}

	
}
