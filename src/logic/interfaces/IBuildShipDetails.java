package logic.interfaces;

import logic.Coordinate;
import logic.enums.BattleshipDirectionType;

/**
 * Created by barakm on 30/08/2017
 */
public interface IBuildShipDetails {

    Coordinate getPosition();

    int getLength();

    BattleshipDirectionType getDirection();
}
