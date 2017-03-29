package com.interfacema.gis.client.ui.growl;



/**
 * Enumeration of possible screen positions of the Growl.
 *
 */
public enum InterfacemaGrowlPosition {

    TOP_LEFT("top-left"),
    TOP_RIGHT("top-right"),
    BOTTOM_LEFT("bottom-left"),
    BOTTOM_RIGHT("bottom-right");

    private final String position;

    private InterfacemaGrowlPosition(final String position) {
        this.position = position;
    }

    /**
     * Get String representation of position.
     *
     * @return String representation of position
     */
    public String getPosition() {
        return this.position;
    }

}
