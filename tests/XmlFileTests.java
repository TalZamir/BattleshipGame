import logic.Battleship;
import logic.TheGame;
import logic.exceptions.XmlContentException;
import module.BattleShipGameType;
import module.PositionType;
import module.ShipType;
import module.ShipTypeType;
import org.junit.jupiter.api.Test;
import ui.verifiers.ErrorCollector;
import ui.verifiers.IInputVerifier;
import ui.verifiers.XmlFileVerifier;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by barakm on 27/07/2017
 */
class XmlFileTests {

    private static final String RELATIVE_PATH = "module/battleshipx.xml";
    private final ErrorCollector errorCollector = new ErrorCollector();
    private final IInputVerifier inputVerifier = new XmlFileVerifier();

    @Test
    void loadEmptyFile() {
        boolean actual = inputVerifier.isFileOk("", errorCollector);

        assertEquals(false, actual);
    }

    @Test
    void verifyFileIsNotXml() {
        boolean actual = inputVerifier.isFileOk("bla.bla", errorCollector);

        assertEquals(false, actual);
    }

    @Test
    void verifyFileIsXml() {
        boolean actual = inputVerifier.isFileOk("bla.xml", errorCollector);

        assertEquals(true, actual);
    }

    @Test
    void verifyBoardsSize() throws JAXBException {
        BattleShipGameType xmlContent = new BattleShipGameType();

        xmlContent.setBoardSize("5");
        boolean actual = inputVerifier.isContentOK(xmlContent, errorCollector);

        assertEquals(true, actual);

        xmlContent.setBoardSize("20");
        actual = inputVerifier.isContentOK(xmlContent, errorCollector);

        assertEquals(true, actual);

        xmlContent.setBoardSize("4");
        actual = inputVerifier.isContentOK(xmlContent, errorCollector);

        assertEquals(false, actual);

        xmlContent.setBoardSize("21");
        actual = inputVerifier.isContentOK(xmlContent, errorCollector);

        assertEquals(false, actual);
    }

    @Test
    void verifyFileNotExist() {
        boolean actual;
        TheGame theGame = new TheGame();

        try {
            theGame.loadFile("stamPath.xml", errorCollector);
            actual = false;
        } catch (XmlContentException e) {
            actual = true;
        }

        assertEquals(true, actual);
    }

    @Test
    void verifyFileExist() {
        boolean actual;
        TheGame theGame = new TheGame();

        try {
            theGame.loadFile(RELATIVE_PATH, errorCollector);
            actual = true;
        } catch (XmlContentException e) {
            actual = false;
        }

        assertEquals(true, actual);
    }

    @Test
    void verifyGameIsOn() {
        boolean actual;
        TheGame theGame = new TheGame();

        try {
            theGame.loadFile(RELATIVE_PATH, errorCollector);
            theGame.init();
        } catch (XmlContentException e) {
            e.printStackTrace();
        }

        //Try to load another file after start game.
        try {
            actual = !theGame.loadFile(RELATIVE_PATH, errorCollector);
        } catch (XmlContentException e) {
            actual = true;
        }

        assertEquals(true, actual);
    }

    @Test
    void verifyBattleshipAmountIsOk() {
        boolean actual;
        List<Battleship> battleships = new ArrayList<>();
        Map<String, ShipTypeType> map = new HashMap<>();

        ShipTypeType shipTypeType = new ShipTypeType();
        ShipType shipType1 = new ShipType();
        ShipType shipType2 = new ShipType();

        setShipTypeType(shipTypeType, "2", "1", "1", "1", "1");

        PositionType positionType1 = new PositionType();
        PositionType positionType2 = new PositionType();

        setPosition(positionType1, "1", "1");
        setPosition(positionType2, "3", "3");

        setShipType(shipType1, "1", positionType1);

        setShipType(shipType2, "1", positionType2);

        battleships.add(new Battleship(shipTypeType, shipType1));
        battleships.add(new Battleship(shipTypeType, shipType2));

        map.put("1", shipTypeType);

        actual = inputVerifier.isBattleshipAmountOk(battleships, map);

        assertEquals(true, actual);
    }

    @Test
    void verifyBattleshipAmountIsNotOk() {
        boolean actual;
        List<Battleship> battleships = new ArrayList<>();
        Map<String, ShipTypeType> map = new HashMap<>();

        ShipTypeType shipTypeType = new ShipTypeType();
        ShipType shipType1 = new ShipType();

        setShipTypeType(shipTypeType, "2", "1", "1", "1", "1");

        PositionType positionType1 = new PositionType();

        setPosition(positionType1, "1", "1");

        setShipType(shipType1, "1", positionType1);

        battleships.add(new Battleship(shipTypeType, shipType1));

        map.put("1", shipTypeType);

        actual = inputVerifier.isBattleshipAmountOk(battleships, map);

        assertEquals(false, actual);
    }

    private void setShipType(ShipType shipType1, String id, PositionType positionType1) {
        shipType1.setShipTypeId(id);
        shipType1.setPosition(positionType1);
    }

    private void setShipTypeType(ShipTypeType shipTypeType,
                                 String amount,
                                 String id,
                                 String length,
                                 String score,
                                 String category) {
        shipTypeType.setAmount(amount);
        shipTypeType.setId(id);
        shipTypeType.setLength(length);
        shipTypeType.setScore(score);
        shipTypeType.setCategory(category);
    }

    private void setPosition(PositionType positionType1, String x, String y) {
        positionType1.setX(x);
        positionType1.setY(y);
    }
}
