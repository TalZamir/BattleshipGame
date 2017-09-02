package logic.battleships;

import logic.Coordinate;
import logic.enums.BattleshipDirectionType;

/**
 * Created by barakm on 01/09/2017
 */
public class BattleshipRightUp extends AdvancedBattleship {

    public BattleshipRightUp(Battleship battleship) {
        super(battleship);
    }

    @Override
    public Coordinate getPosition() {
        if (currentDirection == BattleshipDirectionType.ROW) {
            return new Coordinate(row, column - getLength() + 1);
        } else {
            return new Coordinate(row - getLength() + 1, column);
        }
    }
}
