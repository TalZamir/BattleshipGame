package logic;

import logic.interfaces.IPlayer;

public class Player implements IPlayer {

    private Board board;

    public Player(Board board) {
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

    public void setBoard(Board board) {
        this.board = board;
    }
}
