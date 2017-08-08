package logic.enums;

public enum GameType {
    BASIC("basic"),
    ADVANCE("advance");

    private final String value;

    GameType(String value) {
        this.value = value;
    }

    public String getGameTypeValue() {
        return value;
    }
}
