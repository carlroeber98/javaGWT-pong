package com.carl.pongspiel.client.view;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.RadioButton;

import com.carl.pongspiel.client.model.Difficulty;
import com.carl.pongspiel.client.presenter.LoginPresenter;
import com.carl.pongspiel.shared.model.UserPoints;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.HasText;
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
	}
	
	@UiField
	HasText usernameField;

	@UiField
	HasText passwordField;
	
	@UiField
	HasText passwordConfirmField;

	@UiField
	Button sendButton;
	
	@UiField
	Widget userPreferences;

	@UiField
	HasText welcomeLabel;

	@UiField
	Widget userMessageDiv;

	@UiField
	HasText userMessageLabel;

	@UiField
	Widget waitingLayout;
	
	@UiField
	Widget passwordConfirmFieldDiv;
	
	@UiField
	Label newUserLabel;
	
	@UiField
	Label LoginUserLabel;
	
	@UiField
	ButtonGroup difficultyButtonGroup;
	
	private List<RadioButton> difficultyRadioButtons = new ArrayList<RadioButton>();
	
	private void initDifficultyButtons() {
		for(Difficulty difficulty : Difficulty.values()){
			RadioButton radioButton = new RadioButton("difficulty");
			radioButton.setText(difficulty.getName());
			radioButton.setFormValue(difficulty.name());
			if(difficulty.equals(Difficulty.MEDIUM)){
				radioButton.setValue(true);
			}
			difficultyRadioButtons.add(radioButton);
			difficultyButtonGroup.add(radioButton);
		}
	}

	@UiHandler("sendButton")
	public void onSendButtonClicked(ClickEvent e) {
		GWT.debugger();
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
	
	@UiHandler("usernameField")
	void onUsernameInputKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			if (passwordConfirmFieldDiv.isVisible()){
				presenter.createNewUser(usernameField.getText(), passwordField.getText(), passwordConfirmField.getText());
			}
			else{
				presenter.checkUserAcc(usernameField.getText(), passwordField.getText());
			}
		}
	}
	
	@UiHandler("newUserLabel")
	void onNewUserLabelClick(ClickEvent e) {
		presenter.checkNewUsername(usernameField.getText());
	}
	
	public void newUserLayout(){
		newUserLabel.setVisible(false);
		LoginUserLabel.setVisible(true);
		passwordConfirmFieldDiv.setVisible(true);
	}
	
	@UiHandler("LoginUserLabel")
	void onLoginUserLabelClick(ClickEvent e) {
		loginUserLabel();
	}
	
	public void loginUserLabel(){
		newUserLabel.setVisible(true);
		LoginUserLabel.setVisible(false);
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
	
	public void welcomeUserLabel(UserPoints pointsPlayer) {
		welcomeLabel.setText("Wilkommen " + pointsPlayer.getUsername() + ". " + "Du hast einen aktuellen Highscore von " + pointsPlayer.getHighscrore() + " Punkten");
	}

	public void userWarning(String userMessage) {
		userMessageLabel.setText(userMessage);
		userMessageDiv.setVisible(true);
	}

	@Override
	public void loadingPage() {
		userMessageDiv.setVisible(false);
		userPreferences.setVisible(false);
		Timer t = new Timer() {
			@Override
			public void run() {
				waitingLayout.setVisible(false);
				presenter.onLoadingFinished();
			}
		};
		t.schedule(2000);
		waitingLayout.setVisible(true);
	}
	
	
}
