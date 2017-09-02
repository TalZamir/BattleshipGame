package logic.battleships;

import logic.Coordinate;
import logic.enums.BattleshipDirectionType;

/**
 * Created by barakm on 01/09/2017
 */
public class BattleshipDownRight extends AdvancedBattleship {

    public BattleshipDownRight(Battleship battleship) {
        super(battleship);
    }

    @Override
    public Coordinate getPosition() {
        if (currentDirection == BattleshipDirectionType.COLUMN) {
            return new Coordinate(row - getLength() + 1, column);
        } else {
            return new Coordinate(row, column);
        }
    }
}
