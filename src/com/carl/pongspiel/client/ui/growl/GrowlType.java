package com.carl.pongspiel.client.ui.growl;

/**
 * Enumeration of Growl`s types (CSS class names).
 * <p/>
 * Style name is appended after "alert-", so resulting CSS class name is "alert-[type]".
 *
 */

public enum GrowlType implements com.google.gwt.dom.client.Style.HasCssName {

	DANGER("danger"), INFO("info"), SUCCESS("success"), WARNING("warning");

	private final String cssClass;

	private GrowlType(final String cssClass) {
		this.cssClass = cssClass;
	}

	@Override
	public String getCssName() {
		return cssClass;
	}

}
