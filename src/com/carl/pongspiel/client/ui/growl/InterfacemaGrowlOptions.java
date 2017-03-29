package com.interfacema.gis.client.ui.growl;

/**
 * 
 * This class represents basic Growl`s options, that you can use to customize
 * display of each Growl.
 * 
 * @author Sushmitha
 *
 */

public class InterfacemaGrowlOptions {

	private InterfacemaAnimation enter = InterfacemaAnimation.FADE_IN_UP;

	private InterfacemaAnimation exit = InterfacemaAnimation.FADE_OUT_DOWN;

	private int delay = 6;

	private InterfacemaGrowlPosition position = InterfacemaGrowlPosition.BOTTOM_RIGHT;

	private InterfacemaGrowlType type = InterfacemaGrowlType.INFO;

	/**
	 * Sets the enter and exit animation.
	 * 
	 * @param enter
	 * @param exit
	 */
	public final void setAnimation(InterfacemaAnimation enter, InterfacemaAnimation exit) {
		this.enter = (enter != null) ? enter : InterfacemaAnimation.NO_ANIMATION;
		this.exit = (exit != null) ? exit : InterfacemaAnimation.NO_ANIMATION;
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
	public final void setPosition(InterfacemaGrowlPosition position) {
		if (position != null) {
			this.position = position;
		}
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 */
	public final void setType(InterfacemaGrowlType type) {
		if (type != null)
			this.type = type;
	}

	/**
	 * @return the enter
	 */
	public InterfacemaAnimation getEnterAnimation() {
		return enter;
	}

	/**
	 * @return the exit
	 */
	public InterfacemaAnimation getExitAnimation() {
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
	public InterfacemaGrowlPosition getPosition() {
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
	public InterfacemaGrowlType getType() {
		return type;
	}

}
