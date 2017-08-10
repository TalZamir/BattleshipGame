package logic.builders;

import logic.Battleship;
import logic.enums.ExceptionsMeassage;
import logic.exceptions.XmlContentException;
import module.BoardType;
import module.ShipType;
import module.ShipTypeType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class BattleshipBuilder {

    private HashMap<String, ShipTypeType> battleshipsMap;

    public BattleshipBuilder(List<ShipTypeType> shipTypes) throws XmlContentException {
        init(shipTypes);
    }

    // **************************************************** //
    // Initiates builder battleships map
    // **************************************************** //
    public void init(List<ShipTypeType> shipTypes) throws XmlContentException {
        battleshipsMap = new HashMap<>();
        for(ShipTypeType battleship : shipTypes) {
            if(battleshipsMap.containsKey(battleship.getId())) {
                throw new XmlContentException(ExceptionsMeassage.DUPLICATE_SHIP_ID);
            }
            else {
                battleshipsMap.put(battleship.getId(), battleship);
            }
        }
    }

    // **************************************************** //
    // Builds user battleships list
    // **************************************************** //
    public List<Battleship> buildUserBattleships(List<ShipType> ships) throws XmlContentException {
        List<Battleship> battleships = new ArrayList<>();
        for(ShipType ship : ships) {
            if(!battleshipsMap.containsKey(ship.getShipTypeId())) {
                throw new XmlContentException(ExceptionsMeassage.UNKNOWN_SHIP_ID); // battleship ID doesn't exist
            }
            battleships.add(new Battleship(battleshipsMap.get(ship.getShipTypeId()), ship));
        }
        if(!checkAmount(battleships)) {
            throw new XmlContentException(ExceptionsMeassage.SHIP_MISSMATCH); // battleship ID doesn't exist
        }
        return battleships;
    }

    // **************************************************** //
    // Battleships amount check
    // **************************************************** //
    public boolean checkAmount(List<Battleship> battleships) {
        Collection<ShipTypeType> battleshipsCollection = battleshipsMap.values();
        int counter = 0;
        for(ShipTypeType primitiveShip : battleshipsCollection) {
            for(Battleship currentShip : battleships) {
                if(primitiveShip.getId() == currentShip.getId()) {
                    counter++;
                }
            }
            if(counter != Integer.parseInt(primitiveShip.getAmount())) {
                return false;
            }
            counter = 0;
        }
        return true;
    }
}
