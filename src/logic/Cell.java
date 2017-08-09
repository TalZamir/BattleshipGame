package logic;

import logic.enums.CellStatus;

public class Cell {

    private final Coordinate point;
    private char shipId;
    private CellStatus cellStatus;

    public Cell(Coordinate point, char shipId) {
        this.point = point;
        this.shipId = shipId;

        //TODO: use Tal's enum
        cellStatus = CellStatus.REGULAR;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }
}
