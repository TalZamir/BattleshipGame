package logic;

import logic.enums.CellStatus;

public class Cell {

    private Coordinate point;
    private String shipId;
    private CellStatus cellStatus;

    public Coordinate getPoint() {
        return point;
    }

    public void setPoint(Coordinate point) {
        this.point = point;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }
}
