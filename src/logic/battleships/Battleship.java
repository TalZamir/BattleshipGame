package logic.battleships;

import logic.Coordinate;
import logic.bases.BattleshipBase;
import logic.enums.BattleshipDirectionType;
import logic.enums.ErrorMessages;
import logic.enums.ShipCategoryType;
import logic.exceptions.XmlContentException;
import module.ShipType;
import module.ShipTypeType;

public class Battleship extends BattleshipBase {

    private String id;
    private int length;
    private BattleshipDirectionType direction;
    private Coordinate position;
    private ShipCategoryType category;

    public Battleship() {
    }

    public Battleship(ShipTypeType shipTypeType, ShipType shipType) throws XmlContentException {

        // ShipTypeType attributes
        id = shipTypeType.getId();
        length = Integer.parseInt(shipTypeType.getLength());
        lengthForIsAlive = length;
        score = Integer.parseInt(shipTypeType.getScore());
        category = getEnumCategory(shipTypeType.getCategory());
        // ShipType attributes
        direction = getEnumDirection(shipType.getDirection());
        position = new Coordinate(Integer.parseInt(shipType.getPosition().getX()), Integer.parseInt(shipType.getPosition().getY()));
    }

    public String getId() {
        return id;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public BattleshipDirectionType getDirection() {
        return direction;
    }

    @Override
    public Coordinate getPosition() {
        return position;
    }

    public ShipCategoryType getCategory() {
        return category;
    }

    private ShipCategoryType getEnumCategory(String category) {
        if (category.equalsIgnoreCase(ShipCategoryType.REGULAR.getValue())) {
            return ShipCategoryType.REGULAR;
        } else {
            return ShipCategoryType.L_SHIP;
        }
    }

    private BattleshipDirectionType getEnumDirection(String direction) throws XmlContentException {
        for (BattleshipDirectionType battleshipDirectionType : BattleshipDirectionType.values()) {
            if (direction.equalsIgnoreCase(battleshipDirectionType.value())) {
                return battleshipDirectionType;
            }
        }

        throw new XmlContentException(ErrorMessages.INVALID_BATTLESHIP_DIRECTION);
    }
}
