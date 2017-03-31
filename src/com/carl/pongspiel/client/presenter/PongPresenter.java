package com.carl.pongspiel.client.presenter;

import java.util.Random;

import com.carl.pongspiel.client.AppController;
import com.carl.pongspiel.client.UserService;
import com.carl.pongspiel.client.ui.GamePreferences;
import com.carl.pongspiel.client.view.PongView;
import com.carl.pongspiel.shared.model.PlayerType;
import com.carl.pongspiel.shared.Languages;
import com.carl.pongspiel.shared.model.PlayerPoints;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author Carl
 *
 */
public class PongPresenter implements PongView.Presenter {

	private PongView pongView;
	private AppController appController;
	
	public PongPresenter(PongView pongView, AppController appController){
		
		this.appController = appController;
		
		pongView.setPresenter(this);
		this.pongView = pongView;
	}
	
	public void go(RootPanel container) {
		container.clear();
		container.add(pongView.asWidget());
		Window.addResizeHandler(new ResizeHandler() {
			
			@Override
			public void onResize(ResizeEvent event) {
				pongView.timerCancel();
				pongView.setBreakButton(Languages.continue2());
				getNewGameComponetSize();
			}
		});
	}

	/**
	 * Variables
	 */
	PlayerPoints pointsPlayer = new PlayerPoints(0, PlayerType.PLAYER);
	PlayerPoints pointsBot = new PlayerPoints(0, PlayerType.BOT);
	int randomXDirection;
	int	randomYDirection; 
	int ballSpeed;
	
	/**
	 * ->setPoints, addKeyHandylers, initialize gameField with width and height
	 */
	public void buildGamePong(GamePreferences gamePreferences) {
		pongView.setPoints(pointsPlayer.getPoints(), pointsBot.getPoints());
		pongView.addKeyHandlers();
		ballSpeed = gamePreferences.getDifficulty().getballSpeed();
		pongView.setbatSpeed( gamePreferences.getDifficulty().getbatSpeed());
		gamePreferences = getGameComponetsSize(gamePreferences);
		pongView.buildGameField(gamePreferences);
	}
	
	/**
	 * ->set x-yDirection, set ballspeed, call moveball 
	 */
	public void startGame() {
		pongView.setStartButtonVisible(false);
		pongView.setBreakButton(Languages.pause());
		pongView.setBreakButtonVisible(true);
		
		while (randomXDirection == 0 && randomYDirection == 0 || randomXDirection == 0)
			getRandomDirections(-1, 1);
		
		pongView.moveBall(randomXDirection, randomYDirection, ballSpeed);
}
	
	public void getRandomDirections(int min, int max){
		randomXDirection = (int) random(min, max);
		randomYDirection = (int) random(min, max);
		GWT.log("xDir " + randomXDirection + "yDir " + randomYDirection);
	}
	
	/**
	 * ->set player and bot points 
	 */
	public void pointsPlusOne(PlayerType type) {
		if(type.equals(PlayerType.PLAYER)){
			pointsPlayer.setPoints(pointsPlayer.getPoints() + 1);
		}
		else{
			pointsBot.setPoints(pointsBot.getPoints() + 1);
		}
		pongView.setPoints(pointsPlayer.getPoints(), pointsBot.getPoints());
		pongView.resetGameElements();
		if (pointsBot.getPoints() == 7 || pointsPlayer.getPoints() == 7){
			if (pointsPlayer.getHighscrore() < pointsPlayer.getPoints()){
				saveHighscore(pointsPlayer);
			}
			if (pointsBot.getHighscrore() < pointsBot.getPoints()){
				saveHighscore(pointsBot);
			}
			appController.initLoginView();
		}
		pongView.setBreakButtonVisible(false);
		pongView.setStartButtonVisible(true);
	}
	

	/**
	 * ->rebound player and botbat
	 */
	public void reboundBat(int xDir){
		xDir = xDir * -1;
		int yDir  = random(-1, 1);
		pongView.setXyDirection(xDir, yDir);
		GWT.log("xDir: " + xDir + " yDir: "+ yDir);
	}
	
	/**
	 * ->rebound top and bottom 
	 */
	public int reboundTopBottom(int yDir){
		return yDir * -1;
	}
	
	/**
	 * -get a random number between min and max
	 */
	private int random(int min, int max) {
		Random r = new Random();
		int Result = r.nextInt(max-min) + min;
		return Result;
	}

	private void saveHighscore(PlayerPoints playerType) {
		UserService.Util.getInstance().setNewHighscore(playerType, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Failure loading database");
				//pongView.userWarning("Fehler beim hinzufügen des Highscores.");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result){
					//pongView.userWarning("Der neue Highscore von wurde zu Datenbank hinzugefügt.");
				}
				else{
					//pongView.userWarning("Fehler beim hinzufügen des Highscores.");
				}
			}
		});
	}

	@Override
	public void logout() {
		appController.setNewView(true);
		appController.initLoginView();
	}
	
	public void getNewGameComponetSize(){
		GamePreferences gamePreferences = getGameComponetsSize(new GamePreferences());
		pongView.rebuildGameField(gamePreferences);
	}
	
	public GamePreferences getGameComponetsSize(GamePreferences gamePreferences) {
		int windowWidth  = Window.getClientWidth();
		int windowHeight = Window.getClientHeight();
		int border = 8;
		if (windowHeight < 500 || windowWidth < 1000)
			 border = 4;
		gamePreferences.setGameFieldWidth(windowWidth / 2);
		gamePreferences.setGameFieldHeight(windowHeight / 2);
		gamePreferences.setGameFieldBorder(border);
		gamePreferences.setBallHeight(windowWidth / 150);
		gamePreferences.setBallWidth(windowWidth / 150);
		gamePreferences.setBatPlayer1PositionLeft(15);
		gamePreferences.setBatPlayer1Height(windowHeight / 8);
		gamePreferences.setBatPlayer1Width(border);
		gamePreferences.setBatPlayer2PositionRight(15);
		gamePreferences.setBatPlayer2Height(windowHeight / 8);
		gamePreferences.setBatPlayer2Width(border);
		gamePreferences.setLabelPointsSize(windowWidth / 14 + windowHeight / 14);
		gamePreferences.setButtonSize(windowWidth / 100 + windowHeight / 100);
		return gamePreferences;
	}
	
}
