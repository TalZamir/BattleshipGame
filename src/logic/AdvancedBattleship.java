package logic;

import logic.enums.BattleshipDirectionType;
import logic.interfaces.IBuildShipDetails;

/**
 * Created by barakm on 29/08/2017
 */
public abstract class AdvancedBattleship implements IBuildShipDetails {

    private final Battleship battleship;
    protected BattleshipDirectionType currentDirection;
    protected int row, column;

    protected AdvancedBattleship(Battleship battleship) {
        this.battleship = battleship;
        currentDirection = BattleshipDirectionType.COLUMN;
        row = battleship.getPosition().getRow();
        column = battleship.getPosition().getColumn();
    }

    @Override
    public int getLength() {
        return battleship.getLength();
    }

    @Override
    public BattleshipDirectionType getDirection() {
        return currentDirection;
    }

    public void switchDirection() {
        if (currentDirection == BattleshipDirectionType.COLUMN) {
            currentDirection = BattleshipDirectionType.ROW;
        } else {
            currentDirection = BattleshipDirectionType.COLUMN;
        }
    }
}
