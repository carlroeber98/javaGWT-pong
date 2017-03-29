package com.carl.pongspiel.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Div;

import com.carl.pongspiel.client.presenter.PongPresenter;
import com.carl.pongspiel.client.ui.Position;
import com.carl.pongspiel.shared.model.PlayerType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class PongViewImpl extends Composite implements PongView {

	@UiTemplate("PongView.ui.xml")
	public interface PongViewUiBinder extends UiBinder<Widget, PongViewImpl> {

		Widget createAndBindUi(PongViewImpl viewImpl);
	}

	private static PongViewUiBinder uiBinder = GWT.create(PongViewUiBinder.class);
	
	private PongView.Presenter presenter;
	
	@Override
	public void setPresenter(PongPresenter presenter) {
		this.presenter = presenter;
	}
	
	public PongViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		Window.setTitle("Pong");
		initClickable();
	}
	
	/**
	 * Variables
	 */
	private int xDirection;
	private int yDirection;
	private int batSpeed;
	private int gameFieldBorder;
	private Position ballPosition;
	private Position batPlayerPosition;
	private Position batBotPosition;
	private Integer gameFieldWidth;
	private Integer gameFieldHeight;

	/**
	 * Ui-Fields
	 */
	@UiField
	Button startButton;

	@UiField
	Div gameField;

	@UiField
	Widget game;

	@UiField
	HasText pointsLabel;

	@UiField
	Widget ball;

	@UiField
	Widget batBot;
	
	@UiField
	Widget batPlayer;
	
	@UiField
	Button logoutButton;
	
	/**
	 * Ui-Handler
	 */
	@UiHandler("startButton")
	public void onstartButtonClicked(ClickEvent e) {
		presenter.startGame();
	}
	
	@UiHandler("logoutButton")
	public void onlogoutButtonClicked(ClickEvent e) {
		presenter.logout();
	}
	
	public void addKeyHandlers() {
		RootPanel.get().addDomHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.isUpArrow()) {
					GWT.log(batPlayer.getElement().getOffsetTop() - batSpeed + ", " + gameField.getElement().getOffsetTop());
					if (batPlayer.getElement().getOffsetTop() - batSpeed >  0) {
						batPlayer.getElement().getStyle().setTop(batPlayer.getElement().getOffsetTop() - batSpeed, Unit.PX);
					}
					if (batPlayer.getElement().getOffsetTop() - batSpeed < 0 || batPlayer.getElement().getOffsetTop() - batSpeed == 0) {
						batPlayer.getElement().getStyle().setTop(0, Unit.PX);
					}
				}
				if (event.isDownArrow()) {
					if (batPlayer.getElement().getOffsetTop() + batSpeed < gameFieldHeight) {
						batPlayer.getElement().getStyle().setTop(batPlayer.getElement().getOffsetTop() + batSpeed, Unit.PX);
					}
					if (batPlayer.getElement().getOffsetTop() + batSpeed > gameFieldHeight - batPlayer.getOffsetHeight() + gameFieldBorder
							|| batPlayer.getElement().getOffsetTop() + batSpeed == gameFieldHeight - batPlayer.getOffsetHeight() + gameFieldBorder) {
						batPlayer.getElement().getStyle().setTop(gameFieldHeight - batPlayer.getOffsetHeight() + gameFieldBorder, Unit.PX);
					}
				}
			}
		}, KeyDownEvent.getType());
	}

	
	public void buildGameField(int width, int height, int border) {
		gameFieldWidth = width;
		gameFieldHeight = height;
		gameFieldBorder = border;
		gameField.getElement().getStyle().setHeight(gameFieldHeight, Unit.PX);
		gameField.getElement().getStyle().setWidth(gameFieldWidth, Unit.PX);
		gameField.getElement().getStyle().setBorderWidth(gameFieldBorder, Unit.PX);
		initGameFieldPositions();
		resetGameElements();
		game.setVisible(true);
		//gameField.getElement().getStyle().setBackgroundColor("blue");
	}
	
	private void initGameFieldPositions() {
		ballPosition = new Position(gameFieldHeight / 2 - ball.getOffsetHeight() / 2, gameFieldWidth / 2 - ball.getOffsetWidth() / 2);
		batPlayerPosition = new Position(gameFieldHeight / 2 - batPlayer.getOffsetHeight() / 2, 15);
		batBotPosition = new Position(gameFieldHeight / 2 - batBot.getOffsetHeight() / 2, gameFieldWidth - 15 - gameFieldBorder);
	}
	
	public void resetGameElements() {
		ball.getElement().getStyle().setLeft(ballPosition.getLeft(), Unit.PX);
		ball.getElement().getStyle().setTop(ballPosition.getTop(), Unit.PX);

		batPlayer.getElement().getStyle().setLeft(batPlayerPosition.getLeft(), Unit.PX);
		batPlayer.getElement().getStyle().setTop(batPlayerPosition.getTop(), Unit.PX);

		batBot.getElement().getStyle().setLeft(batBotPosition.getLeft(), Unit.PX);
		batBot.getElement().getStyle().setTop(batBotPosition.getTop(), Unit.PX);
	}
	
	public void setXyDirection(int xDir, int yDir){
		this.xDirection = xDir;
		this.yDirection = yDir;
	}

	public void setbatSpeed(int speed){
		this.batSpeed = speed;
	}

	public void setStartButtonVisible(boolean visible) {
		startButton.setVisible(visible);
	}

	public void setPoints(int player, int bot) {
		pointsLabel.setText(player + " : " + bot);
	}

	public Widget getBall() {
		return ball;
	}

	public void setBall(Widget ball) {
		this.ball = ball;
	}

	public void moveBall(int xDir, final int yDir, int ballSpeed) {
		xDirection = xDir;
		yDirection = yDir;
		new Timer(){
			@Override
			public void run() {
				if (ball.getElement().getOffsetTop() + yDirection < 0
					|| ball.getElement().getOffsetTop() + yDirection > gameFieldHeight - gameFieldBorder) {
				 	yDirection = presenter.reboundTopBottom(yDirection);
				}
				if (ball.getElement().getOffsetLeft() + xDirection < 0) {
					presenter.pointsPlusOne(PlayerType.BOT);
					this.cancel();
				}
				if (ball.getElement().getOffsetLeft() + xDirection > gameFieldWidth - gameFieldBorder) {
					presenter.pointsPlusOne(PlayerType.PLAYER);
					this.cancel();
				}
				if (ball.getElement().getOffsetLeft() + ball.getOffsetWidth() + xDirection == batBot.getElement().getOffsetLeft() && 
						ball.getElement().getOffsetTop() + ball.getOffsetHeight() + yDirection > batBot.getElement().getOffsetTop() &&  
						ball.getElement().getOffsetTop() + yDirection < batPlayer.getElement().getOffsetTop() + batBot.getOffsetHeight()){
							presenter.reboundBat(xDirection);
				}
				if (ball.getElement().getOffsetLeft() + xDirection == batPlayer.getElement().getOffsetLeft() + batPlayer.getOffsetWidth() && 
					ball.getElement().getOffsetTop() + ball.getOffsetHeight() + yDirection > batPlayer.getElement().getOffsetTop() &&  
					ball.getElement().getOffsetTop() + yDirection < batPlayer.getElement().getOffsetTop() + batPlayer.getOffsetHeight()){
						presenter.reboundBat(xDirection);
				}
				//Funnktion kanten des schlägers
				
				moveBatBot();
				
				ball.getElement().getStyle().setTop(ball.getElement().getOffsetTop() + yDirection, Unit.PX);
				ball.getElement().getStyle().setLeft(ball.getElement().getOffsetLeft() + xDirection, Unit.PX);
			}
		}.scheduleRepeating(ballSpeed);
	}
	
	public void moveBatBot(){
		if (ball.getElement().getOffsetTop() + 5 - batBot.getOffsetHeight() / 2 > 0 && ball.getElement().getOffsetTop() - 5 + batBot.getOffsetHeight() / 2 < gameFieldHeight){
			batBot.getElement().getStyle().setTop(ball.getElement().getOffsetTop() - batBot.getOffsetHeight() / 2, Unit.PX);
		}
	}
	
	public void initClickable(){
		startButton.getElement().addClassName("clickable");
		logoutButton.getElement().addClassName("clickable");
	}

}
