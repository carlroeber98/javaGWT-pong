package com.carl.pongspiel.client.ui.growl;

/**
 * 
 * This class represents basic Growl`s options, that you can use to customize
 * display of each Growl.
 * 
 * @author Sushmitha
 *
 */

public class GrowlOptions {

	private Animation enter = Animation.FADE_IN_UP;

	private Animation exit = Animation.FADE_OUT_DOWN;

	private int delay = 6;

	private GrowlPosition position = GrowlPosition.BOTTOM_RIGHT;

	private GrowlType type = GrowlType.INFO;

	/**
	 * Sets the enter and exit animation.
	 * 
	 * @param enter
	 * @param exit
	 */
	public final void setAnimation(Animation enter, Animation exit) {
		this.enter = (enter != null) ? enter : Animation.NO_ANIMATION;
		this.exit = (exit != null) ? exit : Animation.NO_ANIMATION;
	}

	/**
	 * Sets the exit delay.
	 * 
	 * @param delay
	 */
	public final void setDelay(int delay) {
		if (delay > 3 && delay < 10)
			this.delay = delay;
	}

	/**
	 * Sets the position.
	 * 
	 * @param position
	 */
	public final void setPosition(GrowlPosition position) {
		if (position != null) {
			this.position = position;
		}
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 */
	public final void setType(GrowlType type) {
		if (type != null)
			this.type = type;
	}

	/**
	 * @return the enter
	 */
	public Animation getEnterAnimation() {
		return enter;
	}

	/**
	 * @return the exit
	 */
	public Animation getExitAnimation() {
		return exit;
	}

	/**
	 * @return the delay
	 */
	public int getDelay() {
		return delay;
	}
	
	/**
	 * @return the position
	 */
	public GrowlPosition getPosition() {
		return position;
	}

	/**
	 * @return the position horizontal
	 */
	public String getPositionHorizontal() {
		return position.getPosition().split("-")[0];
	}
	
	/**
	 * @return the position vertical
	 */
	public String getPositionVertical() {
		return position.getPosition().split("-")[1];
	}

	/**
	 * @return the type
	 */
	public GrowlType getType() {
		return type;
	}

}
