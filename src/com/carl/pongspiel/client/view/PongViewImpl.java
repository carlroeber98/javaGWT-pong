package com.carl.pongspiel.client.view;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.RadioButton;
import org.gwtbootstrap3.client.ui.html.Div;

import com.carl.pongspiel.client.model.Difficulty;
import com.carl.pongspiel.client.presenter.PongPresenter;
import com.carl.pongspiel.client.ui.Position;
import com.carl.pongspiel.shared.model.PlayerType;
import com.carl.pongspiel.shared.model.UserPoints;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class PongViewImpl extends Composite implements PongView {

	@UiTemplate("PongView.ui.xml")
	public interface PongSpielCarliBinder extends UiBinder<Widget, PongViewImpl> {

		Widget createAndBindUi(PongViewImpl viewImpl);
	}

	private static PongSpielCarliBinder uiBinder = GWT.create(PongSpielCarliBinder.class);

	private PongView.Presenter presenter;
	
	private int xDirection;
	private int yDirection;
	private int batSpeed = 0;

	@UiField
	HasText usernameField;

	@UiField
	HasText passwordField;
	
	@UiField
	HasText passwordConfirmField;

	@UiField
	Button sendButton;

	@UiField
	Button startButton;

	@UiField
	Widget userPreferences;

	@UiField
	Div gameField;

	@UiField
	Widget game;

	@UiField
	HasText pointLabel;

	@UiField
	HasText welcomeLabel;

	@UiField
	Widget userMessageDiv;

	@UiField
	HasText userMessageLabel;

	@UiField
	Widget waitingLayout;

	@UiField
	Widget ball;

	@UiField
	Widget batBot;
	
	@UiField
	Widget passwordConfirmFieldDiv;

	@UiField
	Widget batPlayer;
	
	@UiField
	Label newUserLabel;
	
	@UiField
	Label LoginUserLabel;
	
	@UiField
	ButtonGroup difficultyButtonGroup;

	private int gameFieldBorder = 8;
	
	private Position gameFieldInnerUpperLeft;
	private Position gameFieldInnerBottomRight;
	private Integer gameFieldWidth;
	private Integer gameFieldHeight;
	
	private List<RadioButton> difficultyRadioButtons = new ArrayList<RadioButton>();

	public PongViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		initDifficultyButtons();
	}
	
	public void setXYDirection(int xDir, int yDir){
		this.xDirection = xDir;
		this.yDirection = yDir;
	}
	
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

	@Override
	public void setPresenter(PongPresenter presenter) {
		this.presenter = presenter;
	}
	
	public void setbatSpeed(){
		this.batSpeed = getDifficulty().getbatSpeed();
	}

	public void buildGameField(final int height, final int width) {
		userMessageDiv.setVisible(false);
		userPreferences.setVisible(false);
		batSpeed = getDifficulty().getbatSpeed();
		Timer t = new Timer() {
			@Override
			public void run() {
				waitingLayout.setVisible(false);
				game.setVisible(true);
				initGameField(height, width);
				resetGameElements();
			}
		};
		t.schedule(2000);
		waitingLayout.setVisible(true);
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

	private void initGameField(int width, int height) {
		this.gameFieldHeight = height;
		this.gameFieldWidth = width;
		gameField.getElement().getStyle().setHeight(gameFieldHeight, Unit.PX);
		gameField.getElement().getStyle().setWidth(gameFieldWidth, Unit.PX);
		gameField.getElement().getStyle().setBorderWidth(gameFieldBorder, Unit.PX);

		Position positionGameField = new Position(gameField.getElement().getAbsoluteTop(),
				gameField.getElement().getAbsoluteLeft());
		gameFieldInnerUpperLeft = new Position(positionGameField.getTop() + gameFieldBorder,
				positionGameField.getLeft() + gameFieldBorder);
		gameFieldInnerBottomRight = new Position((gameFieldInnerUpperLeft.getTop() + gameFieldHeight - gameFieldBorder),
				(gameFieldInnerUpperLeft.getLeft() + gameFieldWidth - gameFieldBorder));
	}

	public void setStartButtonVisible(boolean visible) {
		startButton.setVisible(visible);
	}

	public void setPoints(int player, int bot) {
		pointLabel.setText(player + " : " + bot);
	}

	@UiHandler("startButton")
	public void onstartButtonClicked(ClickEvent e) {
		presenter.startGame();
	}

	public Widget getBall() {
		return ball;
	}

	public void setBall(Widget ball) {
		this.ball = ball;
	}

	public void resetGameElements() {
		Position ballPosition = new Position(gameFieldInnerUpperLeft.getTop() + gameFieldHeight / 2,
				gameFieldInnerUpperLeft.getLeft() + gameFieldWidth / 2);
		ball.getElement().getStyle().setLeft(ballPosition.getLeft(), Unit.PX);
		ball.getElement().getStyle().setTop(ballPosition.getTop(), Unit.PX);

		Position batPlayerPosition = new Position(
				gameFieldInnerUpperLeft.getTop() + gameFieldHeight / 2 - batPlayer.getOffsetHeight() / 2,
				gameFieldInnerUpperLeft.getLeft() + 15);
		batPlayer.getElement().getStyle().setLeft(batPlayerPosition.getLeft(), Unit.PX);
		batPlayer.getElement().getStyle().setTop(batPlayerPosition.getTop(), Unit.PX);

		Position batBotPosition = new Position(
				gameFieldInnerUpperLeft.getTop() + gameFieldHeight / 2 - batBot.getOffsetHeight() / 2,
				gameFieldInnerUpperLeft.getLeft() + gameFieldWidth - 15 - gameFieldBorder);
		batBot.getElement().getStyle().setLeft(batBotPosition.getLeft(), Unit.PX);
		batBot.getElement().getStyle().setTop(batBotPosition.getTop(), Unit.PX);
	}

	public void welcomeUserLabel(UserPoints pointsPlayer) {
		welcomeLabel.setText("Wilkommen " + pointsPlayer.getUsername() + ". " + "Du hast einen aktuellen Highscore von " + pointsPlayer.getHighscrore() + " Punkten");
	}

	public void userWarning(String userMessage) {
		userMessageLabel.setText(userMessage);
		userMessageDiv.setVisible(true);
	}

	public void moveBall(int xDir, final int yDir, int ballSpeed) {
		xDirection = xDir;
		yDirection = yDir;
		GWT.log("xDir " + xDirection + " yDir " + yDirection);
		new Timer(){
			@Override
			public void run() {
				if (ball.getAbsoluteTop() + yDirection < gameFieldInnerUpperLeft.getTop()
						|| ball.getAbsoluteTop() + yDirection > gameFieldInnerBottomRight.getTop()) {
					 	yDirection = presenter.reboundTopBottom(yDirection);
					}
					if (ball.getAbsoluteLeft() + xDirection < gameFieldInnerUpperLeft.getLeft()) {
						presenter.pointsPlusOne(PlayerType.BOT);
						this.cancel();
					}
					if (ball.getAbsoluteLeft() + xDirection > gameFieldInnerBottomRight.getLeft()) {
						presenter.pointsPlusOne(PlayerType.PLAYER);
						this.cancel();
					}
					if (ball.getAbsoluteLeft() + ball.getOffsetWidth() + xDirection == batBot.getAbsoluteLeft()&&
						ball.getAbsoluteTop() + yDirection > batBot.getAbsoluteTop() &&  
						ball.getAbsoluteTop() + yDirection < batBot.getAbsoluteTop() + batBot.getOffsetHeight()){
							presenter.reboundBat(xDirection);
						}
					if (ball.getAbsoluteLeft() + xDirection == batPlayer.getAbsoluteLeft() + batPlayer.getOffsetWidth() &&
						ball.getAbsoluteTop() + yDirection > batPlayer.getAbsoluteTop() &&  
						ball.getAbsoluteTop() + yDirection < batPlayer.getAbsoluteTop() + batPlayer.getOffsetHeight()){
							presenter.reboundBat(xDirection);
						}
					
					movebatBot();
					
					ball.getElement().getStyle().setTop(ball.getAbsoluteTop() + yDirection, Unit.PX);
					ball.getElement().getStyle().setLeft(ball.getAbsoluteLeft() + xDirection, Unit.PX);
			}
		}.scheduleRepeating(ballSpeed);
	}
	
	public void movebatBot(){
		if (ball.getAbsoluteTop() + 5 - batBot.getOffsetHeight() / 2 > gameFieldInnerUpperLeft.getTop() && ball.getAbsoluteTop() - 10 + batBot.getOffsetHeight() / 2< gameFieldInnerBottomRight.getTop()){
			batBot.getElement().getStyle().setTop(ball.getAbsoluteTop() - batBot.getOffsetHeight() / 2, Unit.PX);
		}
	}

	public void addKeyHandlers() {
		RootPanel.get().addDomHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.isUpArrow()) {
					if (batPlayer.getAbsoluteTop() - batSpeed > gameFieldInnerUpperLeft.getTop()) {
						batPlayer.getElement().getStyle().setTop(batPlayer.getAbsoluteTop() - batSpeed, Unit.PX);
					}
					if (batPlayer.getAbsoluteTop() - batSpeed < gameFieldInnerUpperLeft.getTop() || batPlayer.getAbsoluteTop() - batSpeed == gameFieldInnerUpperLeft.getTop()) {
						batPlayer.getElement().getStyle().setTop(gameFieldInnerUpperLeft.getTop(), Unit.PX);
					}
				}
				if (event.isDownArrow()) {
					if (batPlayer.getAbsoluteTop() + batSpeed < gameFieldInnerBottomRight.getTop() - batPlayer.getOffsetHeight() + gameFieldBorder) {
						batPlayer.getElement().getStyle().setTop(batPlayer.getAbsoluteTop() + batSpeed, Unit.PX);
					}
					if (batPlayer.getAbsoluteTop() + batSpeed > gameFieldInnerBottomRight.getTop() - batPlayer.getOffsetHeight() + gameFieldBorder
							|| batPlayer.getAbsoluteTop() + batSpeed == gameFieldInnerBottomRight.getTop() - batPlayer.getOffsetHeight() + gameFieldBorder) {
						batPlayer.getElement().getStyle().setTop(gameFieldInnerBottomRight.getTop() - batPlayer.getOffsetHeight() + gameFieldBorder, Unit.PX);
					}
				}
			}
		}, KeyDownEvent.getType());
	}

}
