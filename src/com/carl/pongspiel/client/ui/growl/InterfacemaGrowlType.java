package com.interfacema.gis.client.ui.growl;

/**
 * Enumeration of Growl`s types (CSS class names).
 * <p/>
 * Style name is appended after "alert-", so resulting CSS class name is "alert-[type]".
 *
 */

public enum InterfacemaGrowlType implements com.google.gwt.dom.client.Style.HasCssName {

	DANGER("danger"), INFO("info"), SUCCESS("success"), WARNING("warning");

	private final String cssClass;

	private InterfacemaGrowlType(final String cssClass) {
		this.cssClass = cssClass;
	}

	@Override
	public String getCssName() {
		return cssClass;
	}

}
