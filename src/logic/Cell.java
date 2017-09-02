package logic;

import logic.bases.BattleshipBase;
import logic.enums.CellStatus;

public class Cell {

    private Coordinate point;
    private BattleshipBase shipRef;
    private CellStatus cellStatus;

    public Coordinate getPoint() {
        return point;
    }

    void setPoint(Coordinate point) {
        this.point = point;
    }

    BattleshipBase getShipRef() {
        return shipRef;
    }

    void setShipRef(BattleshipBase shipRef) {
        this.shipRef = shipRef;
    }

    CellStatus getCellStatus() {
        return cellStatus;
    }

    void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }
}
