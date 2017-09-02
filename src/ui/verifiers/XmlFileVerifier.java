package ui.verifiers;

import logic.battleships.Battleship;
import logic.enums.ErrorMessages;
import logic.enums.GameType;
import logic.enums.ShipCategoryType;
import module.BattleShipGameType;
import module.ShipTypeType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by barakm on 13/08/2017
 */
public class XmlFileVerifier implements IInputVerifier {

    @Override
    public boolean isFileOk(String filePath, ErrorCollector errorCollector) {
        if (filePath.equals("")) {
            errorCollector.addMessage(ErrorMessages.EMPTY_FILE_NAME.message());
        }

        String[] strings = filePath.split("\\.");
        if (!strings[strings.length - 1].equals("xml")) {
            errorCollector.addMessage(ErrorMessages.XML_PREFIX.message());
        }

        return errorCollector.getNumOfErrors() <= 0;
    }

    @Override
    public boolean isContentOK(BattleShipGameType xmlContent, ErrorCollector errorCollector) {
        if (Integer.parseInt(xmlContent.getBoardSize()) < 5 || Integer.parseInt(xmlContent.getBoardSize()) > 20) {
            errorCollector.addMessage(ErrorMessages.BOARD_SIZE.message());
        }

        if (xmlContent.getGameType().equalsIgnoreCase(GameType.BASIC.getGameTypeValue())) {
            xmlContent.getShipTypes()
                      .getShipType().forEach(ship -> {
                if (ship.getCategory().equalsIgnoreCase(ShipCategoryType.L_SHIP.getValue())) {
                    errorCollector.addMessage(ErrorMessages.INVALID_GAME_TYPE_OR_SHIP_CATEGORY.message());
                }
            });
        }

        return errorCollector.getNumOfErrors() <= 0;
    }

    @Override
    public boolean isBattleshipAmountOk(List<Battleship> battleships, Map<String, ShipTypeType> battleshipsMap) {
        Collection<ShipTypeType> battleshipsCollection = battleshipsMap.values();
        int counter = 0;

        for (ShipTypeType primitiveShip : battleshipsCollection) {
            for (Battleship currentShip : battleships) {
                if (primitiveShip.getId().equals(currentShip.getId())) {
                    counter++;
                }
            }
            if (counter != Integer.parseInt(primitiveShip.getAmount())) {
                return false;
            }
            counter = 0;
        }
        return true;
    }
}
