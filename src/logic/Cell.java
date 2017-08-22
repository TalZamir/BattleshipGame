package logic;

import logic.enums.CellStatus;

public class Cell {

    private Coordinate point;
    private Battleship shipRef;
    private CellStatus cellStatus;

    public Coordinate getPoint() {
        return point;
    }

    void setPoint(Coordinate point) {
        this.point = point;
    }

    Battleship getShipRef() {
        return shipRef;
    }

    void setShipRef(Battleship shipRef) {
        this.shipRef = shipRef;
    }

    CellStatus getCellStatus() {
        return cellStatus;
    }

    void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }
}
