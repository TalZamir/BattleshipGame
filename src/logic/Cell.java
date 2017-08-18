package logic;

import logic.enums.CellStatus;

public class Cell {

    private Coordinate point;
    private Battleship shipRef;
    private CellStatus cellStatus;

    public Coordinate getPoint() {
        return point;
    }

    public void setPoint(Coordinate point) {
        this.point = point;
    }

    public Battleship getShipRef() {
        return shipRef;
    }

    public void setShipRef(Battleship shipRef) {
        this.shipRef = shipRef;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }
}
