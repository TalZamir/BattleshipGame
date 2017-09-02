package ui.verifiers;

import logic.battleships.Battleship;
import module.BattleShipGameType;
import module.ShipTypeType;

import java.util.List;
import java.util.Map;

/**
 * Created by barakm on 15/08/2017
 */
public interface IInputVerifier {

    boolean isFileOk(String filePath, ErrorCollector errorCollector);

    boolean isContentOK(BattleShipGameType inputContent, ErrorCollector errorCollector);

    boolean isBattleshipAmountOk(List<Battleship> battleships, Map<String, ShipTypeType> battleshipsMap);
}
