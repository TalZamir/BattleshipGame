package logic;

import module.ShipType;
import module.ShipTypeType;

public class Battleship {

    private final String id;
    private final int length;
    private final int score;
    private final String category;
    private final String direction;
    private final Coordinate position;
    private boolean isAlive;
    private int lengthForIsAlive;

    public Battleship(ShipTypeType shipTypeType, ShipType shipType) {
        isAlive = true;
        // ShipTypeType attributes
        id = shipTypeType.getId();
        length = Integer.parseInt(shipTypeType.getLength());
        lengthForIsAlive = length;
        score = Integer.parseInt(shipTypeType.getScore());
        category = shipTypeType.getCategory();
        // ShipType attributes
        direction = shipType.getDirection();
        position = new Coordinate(Integer.parseInt(shipType.getPosition().getX()), Integer.parseInt(shipType.getPosition().getY()));
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public int getScore() {
        return score;
    }

    public String getCategory() {
        return category;
    }

    public String getDirection() {
        return direction;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void decrementLength() {
        if (--lengthForIsAlive == 0)
            isAlive = false;
    }
}
