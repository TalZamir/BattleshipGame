package logic.builders;

import logic.battleships.Battleship;
import logic.enums.ErrorMessages;
import logic.exceptions.XmlContentException;
import module.ShipType;
import module.ShipTypeType;
import ui.verifiers.IInputVerifier;
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
    // Builds user battleships list
    // **************************************************** //
    public List<Battleship> buildUserBattleships(List<ShipType> ships) throws XmlContentException {
        List<Battleship> battleships = new ArrayList<>();
        for (ShipType ship : ships) {
            if (!battleshipsMap.containsKey(ship.getShipTypeId())) {
                throw new XmlContentException(ErrorMessages.UNKNOWN_SHIP_ID); // battleship ID doesn't exist
            } else if (Integer.parseInt(battleshipsMap.get(ship.getShipTypeId()).getLength()) <= 0) {
                throw new XmlContentException(ErrorMessages.SHIP_LENGTH);
            }

            battleships.add(new Battleship(battleshipsMap.get(ship.getShipTypeId()), ship));
        }

        IInputVerifier inputVerifier = new XmlFileVerifier();
        if (!inputVerifier.isBattleshipAmountOk(battleships, battleshipsMap)) {
            throw new XmlContentException(ErrorMessages.SHIP_MISSMATCH); // battleship ID doesn't exist
        }
        return battleships;
    }

    // **************************************************** //
    // Initiates builder battleships map
    // **************************************************** //
    private void init(List<ShipTypeType> shipTypes) throws XmlContentException {
        battleshipsMap = new HashMap<>();
        for (ShipTypeType battleship : shipTypes) {
            if (battleshipsMap.containsKey(battleship.getId())) {
                throw new XmlContentException(ErrorMessages.DUPLICATE_SHIP_ID);
            } else {
                battleshipsMap.put(battleship.getId(), battleship);
            }
        }
    }
}
