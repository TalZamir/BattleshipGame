package ui.components;

import javafx.scene.control.Button;

/**
 * Created by barakm on 02/09/2017
 */
public class BoardButton extends Button {

    private final int row;
    private final int column;

    public BoardButton(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
