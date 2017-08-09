package logic;

import logic.enums.BattleshipType;
import module.BoardType;
import module.ShipType;
import module.ShipTypeType;

public class Battleship {

    private boolean isAlive;
    private final String id;
    private final int length;
    private final int score;
    private final String category;
    private final String direction;
    private final Coordinate position;


    public Battleship(ShipTypeType shipTypeType, ShipType shipType) {
        this.isAlive = true;
        // ShipTypeType attributes
        this.id = shipTypeType.getId();
        this.length = Integer.parseInt(shipTypeType.getLength());
        this.score = Integer.parseInt(shipTypeType.getScore());
        this.category = shipTypeType.getCategory();
        // ShipType attributes
        this.direction = shipType.getDirection();
        this.position = new Coordinate(Integer.parseInt(shipType.getPosition().getX()), Integer.parseInt(shipType.getPosition().getY()));
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
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

}
