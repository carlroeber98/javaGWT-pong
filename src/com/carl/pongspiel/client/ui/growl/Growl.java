package com.carl.pongspiel.client.ui.growl;

import static com.google.gwt.query.client.GQuery.$;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class represents an instance of the displayed Growl.
 * 
 * @author Sushmitha
 *
 */

public class Growl extends Composite {

	private static GrowlUiBinder uiBinder = GWT.create(GrowlUiBinder.class);

	interface GrowlUiBinder extends UiBinder<Widget, Growl> {
	}

	/**
	 * Represents the main application container
	 */
	private static HasWidgets appContainer;

	/**
	 * Represents the number currently of active growls.
	 */
	private static int[] counter = new int[GrowlPosition.values().length];

	@UiField
	Div growlContainer;
	@UiField
	Span message;
	static List<Growl> allGrowls = new ArrayList<Growl>();

	Growl thisGrowl;
	GrowlOptions growlOptions = new GrowlOptions();

	public Growl() {
		initWidget(uiBinder.createAndBindUi(this));
		growlContainer.getElement().setAttribute("data-growl", "container");
		growlContainer.getElement().setAttribute("role", "alert");
		thisGrowl = this;
		allGrowls.add(thisGrowl);
	}

	public Growl(GrowlOptions options) {
		this();
		this.setGrowlOptions(options);
	}

	public static void setContainer(HasWidgets container) {
		appContainer = container;
	}

	/**
	 * Display Growl with custom message and default settings.
	 * 
	 * @param message
	 * @return displayed growl
	 */
	public Growl growl(final String message) {

		this.message.setText(message);
		try {
			appContainer.add(thisGrowl);
		} catch (Exception e) {
			GWT.log("ERROR: Application container is undefined . Use setContainer(container) method to set the main container to which this growl has to be added.");
			e.printStackTrace();
		}

		applyOptions();

		growlContainer.addStyleName(growlOptions.getEnterAnimation().getCssName());
		$(growlContainer).animate(Properties.create(), growlOptions.getDelay() * 1000, new Function() {
			public void f(Element e) {
				growlContainer.addStyleName(growlOptions.getExitAnimation().getCssName());
				$(growlContainer).animate(Properties.create(), 1000, new Function() {
					public void f(Element e) {
						discardThisGrowl();
						adjustOffsetOfAllActiveGrowls();
					}
				});
			}
		});

		return this;
	}

	/**
	 * Discards the growl from the application
	 */
	private void discardThisGrowl() {
		thisGrowl.removeFromParent();
		counter[growlOptions.getPosition().ordinal()]--;
		allGrowls.remove(thisGrowl);
	}

	/**
	 * Adjusts the offset of all active growls
	 */
	private void adjustOffsetOfAllActiveGrowls() {
		for (Growl growl : allGrowls) {
			if (growlOptions.getPosition().ordinal() == growl.growlOptions.getPosition().ordinal()) {
				int positionHorizontal = Integer.parseInt(growl.growlContainer.getElement().getStyle().getProperty(growl.getGrowlOptions().getPositionHorizontal()).replace("px", ""));
				growl.growlContainer.getElement().getStyle().setProperty(growl.growlOptions.getPositionHorizontal(), positionHorizontal - 61 + "px");
			}
		}
	}

	/**
	 * Applies options to the growl
	 */
	private void applyOptions() {
		growlContainer.getElement().setAttribute("data-growl-position", growlOptions.getPosition().getPosition());
		growlContainer.getElement().addClassName("alert alert-" + growlOptions.getType().getCssName());

		growlContainer.getElement().getStyle().setProperty(growlOptions.getPositionHorizontal(), (counter[growlOptions.getPosition().ordinal()]++ * 61) + 20 + "px");
		growlContainer.getElement().getStyle().setProperty(growlOptions.getPositionVertical(), "20px");
	}

	/**
	 * @return the growlOptions
	 */
	public GrowlOptions getGrowlOptions() {
		return growlOptions;
	}

	/**
	 * @param growlOptions
	 *            the growlOptions to set
	 */
	public void setGrowlOptions(GrowlOptions growlOptions) {
		this.growlOptions = growlOptions;
	}

}