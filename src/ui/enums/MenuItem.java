package ui.enums;

public enum MenuItem {

    LOAD_XML(1),
    START_GAME(2),
    STATUS(3),
    PLAY_MOVE(4),
    STATISTICS(5),
    QUIT(6);

    private int item;

    MenuItem(int message) {
        this.item = message;
    }

    public int item() {
        return item;
    }
}
