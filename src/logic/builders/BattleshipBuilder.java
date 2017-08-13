package logic.builders;

import logic.Battleship;
import logic.enums.ErrorMessages;
import logic.exceptions.XmlContentException;
import module.ShipType;
import module.ShipTypeType;
import ui.verifiers.XmlFileVerifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleshipBuilder {

    private Map<String, ShipTypeType> battleshipsMap;

    public BattleshipBuilder(List<ShipTypeType> shipTypes) throws XmlContentException {
        init(shipTypes);
    }

    // **************************************************** //
    // Initiates builder battleships map
    // **************************************************** //
    public void init(List<ShipTypeType> shipTypes) throws XmlContentException {
        battleshipsMap = new HashMap<>();
        for (ShipTypeType battleship : shipTypes) {
            if (battleshipsMap.containsKey(battleship.getId())) {
                throw new XmlContentException(ErrorMessages.DUPLICATE_SHIP_ID);
            } else {
                battleshipsMap.put(battleship.getId(), battleship);
            }
        }
    }

    // **************************************************** //
    // Builds user battleships list
    // **************************************************** //
    public List<Battleship> buildUserBattleships(List<ShipType> ships) throws XmlContentException {
        List<Battleship> battleships = new ArrayList<>();
        for (ShipType ship : ships) {
            if (!battleshipsMap.containsKey(ship.getShipTypeId())) {
                throw new XmlContentException(ErrorMessages.UNKNOWN_SHIP_ID); // battleship ID doesn't exist
            }
            battleships.add(new Battleship(battleshipsMap.get(ship.getShipTypeId()), ship));
        }
        if (!XmlFileVerifier.isBattleshipAmountOk(battleships, battleshipsMap)) {
            throw new XmlContentException(ErrorMessages.SHIP_MISSMATCH); // battleship ID doesn't exist
        }
        return battleships;
    }
}
