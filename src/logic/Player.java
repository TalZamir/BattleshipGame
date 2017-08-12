package logic;

import logic.interfaces.IPlayer;

public class Player implements IPlayer {

    private final Board board;
    private final String name;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    //TODO
    @Override
    public int getUserId() {
        return 0;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public String getName() {
        return name;
    }
}
