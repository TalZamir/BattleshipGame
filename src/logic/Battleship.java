package logic;

import logic.enums.BattleshipDirectionType;
import logic.enums.ErrorMessages;
import logic.exceptions.XmlContentException;
import module.ShipType;
import module.ShipTypeType;

public class Battleship {

    private final String id;
    private final int length;
    private final int score;
    private final String category;
    private final BattleshipDirectionType direction;
    private final Coordinate position;
    private boolean isAlive;
    private int lengthForIsAlive;

    public Battleship(ShipTypeType shipTypeType, ShipType shipType) throws XmlContentException {
        isAlive = true;
        // ShipTypeType attributes
        id = shipTypeType.getId();
        length = Integer.parseInt(shipTypeType.getLength());
        lengthForIsAlive = length;
        score = Integer.parseInt(shipTypeType.getScore());
        category = shipTypeType.getCategory();
        // ShipType attributes
        direction = getEnumDirection(shipType.getDirection());
        position = new Coordinate(Integer.parseInt(shipType.getPosition().getX()), Integer.parseInt(shipType.getPosition().getY()));
    }

    public String getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public String getCategory() {
        return category;
    }

    boolean isAlive() {
        return isAlive;
    }

    int getScore() {
        return score;
    }

    BattleshipDirectionType getDirection() {
        return direction;
    }

    Coordinate getPosition() {
        return position;
    }

    void decrementLength() {
        if (--lengthForIsAlive == 0) {
            isAlive = false;
        }
    }

    private BattleshipDirectionType getEnumDirection(String direction) throws XmlContentException {
        //TODO: this code should be activated on the second exercise
        //        for (BattleshipDirectionType battleshipDirectionType : BattleshipDirectionType.values()) {
        //            if (direction.equalsIgnoreCase(battleshipDirectionType.value())) {
        //                return battleshipDirectionType;
        //            }
        //        }

        if (direction.equalsIgnoreCase(BattleshipDirectionType.ROW.value())) {
            return BattleshipDirectionType.ROW;
        }

        if (direction.equalsIgnoreCase(BattleshipDirectionType.COLUMN.value())) {
            return BattleshipDirectionType.COLUMN;
        }

        throw new XmlContentException(ErrorMessages.INVALID_BATTLESHIP_DIRECTION);
    }
}
