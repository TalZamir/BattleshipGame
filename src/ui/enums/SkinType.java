package ui.enums;

/**
 * Created by barakm on 05/09/2017
 */
public enum SkinType {
    DEFAULT("Default"),
    LIGHT("Light"),
    DARCULA("Darcula");

    private final String value;

    SkinType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
