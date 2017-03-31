package com.carl.pongspiel.client.view;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.RadioButton;

import com.carl.pongspiel.client.model.Difficulty;
import com.carl.pongspiel.client.presenter.LoginPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LoginViewImpl extends Composite implements LoginView {
	
	@UiTemplate("LoginView.ui.xml")
	public interface LoginViewUiBinder extends UiBinder<Widget, LoginViewImpl> {

		Widget createAndBindUi(LoginViewImpl viewImpl);
	}

	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

	private LoginView.Presenter presenter;
	
	@Override
	public void setPresenter(LoginPresenter presenter) {
		this.presenter = presenter;
	}
	
	public LoginViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		Window.setTitle("Login");
		initDifficultyButtons();
		initClickable();
	}
	
	@UiField
	Input usernameField;

	@UiField
	Input passwordField;
	
	@UiField
	Input passwordConfirmField;

	@UiField
	Button singleplayer;
	
	@UiField
	Button multiplayer;
	
	@UiField
	Widget loginDiv;

	@UiField
	Widget passwordConfirmFieldDiv;
	
	@UiField
	Label newUserLabel;
	
	@UiField
	Label loginUserLabel;
	
	@UiField
	ButtonGroup difficultyButtonGroup;
	
	@UiField
	Widget spinner;
	
	@UiField
	Image pongImage;
	
	private List<RadioButton> difficultyRadioButtons = new ArrayList<RadioButton>();
	
	private void initDifficultyButtons() {
		for(Difficulty difficulty : Difficulty.values()){
			RadioButton radioButton = new RadioButton("difficulty");
			radioButton.setText(difficulty.getName());
			radioButton.setFormValue(difficulty.name());
			radioButton.getElement().addClassName("clickable");
			if(difficulty.equals(Difficulty.MEDIUM)){
				radioButton.setValue(true);
			}
			difficultyRadioButtons.add(radioButton);
			difficultyButtonGroup.add(radioButton);
		}
	}

	@UiHandler("singleplayer")
	public void onsingleplayerButtonClicked(ClickEvent e) {
		presenter.setSinglePlayer(true);
		if (passwordConfirmFieldDiv.isVisible()){
			presenter.createNewUser(usernameField.getText(), passwordField.getText(), passwordConfirmField.getText());
		}
		else{
			presenter.checkUserAcc(usernameField.getText(), passwordField.getText());
		}
	}
	
	@UiHandler("multiplayer")
	public void onmultiplayerButtonClicked(ClickEvent e) {
		presenter.setSinglePlayer(false);
		if (passwordConfirmFieldDiv.isVisible()){
			presenter.createNewUser(usernameField.getText(), passwordField.getText(), passwordConfirmField.getText());
		}
		else{
			presenter.checkUserAcc(usernameField.getText(), passwordField.getText());
		}
	}

	@UiHandler("passwordField")
	void onPasswordInputKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			if (passwordConfirmFieldDiv.isVisible()){
				presenter.createNewUser(usernameField.getText(), passwordField.getText(), passwordConfirmField.getText());
			}
			else{
				presenter.checkUserAcc(usernameField.getText(), passwordField.getText());
			}
		}
	}
	
	@UiHandler("passwordConfirmField")
	void onPasswordConfirmInputKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			if (passwordConfirmFieldDiv.isVisible()){
				presenter.createNewUser(usernameField.getText(), passwordField.getText(), passwordConfirmField.getText());
			}
			else{
				presenter.checkUserAcc(usernameField.getText(), passwordField.getText());
			}
		}
	}
	
//	@UiHandler("usernameField")
//	void onUsernameInputKeyUp(KeyUpEvent event) {
//		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
//			if (passwordConfirmFieldDiv.isVisible()){
//				presenter.createNewUser(usernameField.getText(), passwordField.getText(), passwordConfirmField.getText());
//			}
//			else{
//				presenter.checkUserAcc(usernameField.getText(), passwordField.getText());
//			}
//		}
//	}
	
	@UiHandler("newUserLabel")
	void onNewUserLabelClick(ClickEvent e) {
		presenter.checkNewUsername(usernameField.getText());
	}
	
	public void newUserLayout(){
		newUserLabel.setVisible(false);
		loginUserLabel.setVisible(true);
		passwordConfirmFieldDiv.setVisible(true);
	}
	
	@UiHandler("loginUserLabel")
	void onLoginUserLabelClick(ClickEvent e) {
		loginUserLabel();
	}
	
	public void loginUserLabel(){
		newUserLabel.setVisible(true);
		loginUserLabel.setVisible(false);
		passwordConfirmFieldDiv.setVisible(false);
	}
	
	public Difficulty getDifficulty(){
		Difficulty selectedDificulty = null;
		for(RadioButton radioButton : difficultyRadioButtons){
			if(radioButton.getValue()){
				try{
					selectedDificulty = Difficulty.valueOf(radioButton.getFormValue());
					break;
				}catch(Exception e){
					selectedDificulty = Difficulty.MEDIUM;
				}
			}
			
		}
		return selectedDificulty;
	}

	public void loadingPage() {
		loginDiv.setVisible(false);
		Timer t = new Timer() {
			@Override
			public void run() {
				spinner.setVisible(false);
				presenter.onLoadingFinished();
			}
		};
		t.schedule(2000);
		spinner.setVisible(true);
	}

	private void initClickable() {
		singleplayer.getElement().addClassName("clickable");
		multiplayer.getElement().addClassName("clickable");
		newUserLabel.getElement().addClassName("clickable");
		loginUserLabel.getElement().addClassName("clickable");
	}

	public void setLoginComponentSize() {
		int windowWidth  = Window.getClientWidth();
		int windowHeight = Window.getClientHeight();
		pongImage.getElement().getStyle().setWidth(windowWidth / 8 + windowHeight / 8, Unit.PX);
		pongImage.getElement().getStyle().setHeight(windowWidth / 8 + windowHeight / 8, Unit.PX);
		singleplayer.getElement().getStyle().setFontSize(windowWidth / 100 + windowHeight / 100, Unit.PX);
		multiplayer.getElement().getStyle().setFontSize(windowWidth / 100 + windowHeight / 100, Unit.PX);
		newUserLabel.getElement().getStyle().setFontSize(windowWidth / 100 + windowHeight / 100, Unit.PX);
		loginUserLabel.getElement().getStyle().setFontSize(windowWidth / 100 + windowHeight / 100, Unit.PX);
		usernameField.getElement().getStyle().setFontSize(windowWidth / 140 + windowHeight / 140, Unit.PX);
		passwordConfirmField.getElement().getStyle().setFontSize(windowWidth / 140 + windowHeight / 140, Unit.PX);
		passwordField.getElement().getStyle().setFontSize(windowWidth / 140 + windowHeight / 140, Unit.PX);
		difficultyButtonGroup.getElement().getStyle().setFontSize(windowWidth / 140 + windowHeight / 140, Unit.PX);
	}
	
}
