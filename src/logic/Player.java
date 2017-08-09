package logic;

import logic.interfaces.IPlayer;

public class Player implements IPlayer {

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private Board board;

    //TODO
    @Override
    public int getUserId() {
        return 0;
    }
}
