package ui.enums;

public enum MenuItem {

    LOAD_XML(1),
    START_GAME(2),
    STATUS(3),
    PLAY_MOVE(4),
    STATISTICS(5),
    QUIT(6),
    MINE(7),
    EXIT(8),
    UNINITIALIZED(0);

    private int item;

    MenuItem(int item) {
        this.item = item;
    }

    public int value() {
        return item;
    }
}
