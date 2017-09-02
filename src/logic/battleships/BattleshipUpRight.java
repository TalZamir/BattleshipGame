package logic.battleships;

import logic.Coordinate;

/**
 * Created by barakm on 01/09/2017
 */
public class BattleshipUpRight extends AdvancedBattleship {

    public BattleshipUpRight(Battleship battleship) {
        super(battleship);
    }

    @Override
    public Coordinate getPosition() {
        return new Coordinate(row, column);
    }
}
