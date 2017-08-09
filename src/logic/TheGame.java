package logic;

import logic.builders.BattleshipBuilder;
import logic.enums.ExceptionsMeassage;
import logic.enums.GameType;
import logic.exceptions.XmlContentException;
import logic.interfaces.IPlayer;
import logic.interfaces.ISerializer;
import logic.serializers.XmlSerializer;
import module.BattleShipGameType;
import module.BoardType;
import module.BoardsType;
import module.ShipTypeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TheGame {

    private final int firstPlayerIndex;
    private final int secondPlayerIndex;
    private final GameType gameType;


    public TheGame() throws XmlContentException {
        ISerializer serializer = new XmlSerializer();
        BattleShipGameType battleShipGameType = serializer.getBattleShipGameType();

        initGameComponents(battleShipGameType);
        firstPlayerIndex = 0;
        secondPlayerIndex = 1;

        if (battleShipGameType.getGameType().equals(GameType.BASIC.getGameTypeValue())) {
            gameType = GameType.BASIC;
        } else {
            gameType = GameType.ADVANCE;
        }
    }

    //TODO now we are creating only human users. Need to add computer user.
    private void initGameComponents(BattleShipGameType battleShipGameType) throws XmlContentException {
//        setPlayers();
        BattleshipBuilder battleshipBuilder = new BattleshipBuilder(battleShipGameType.getShipTypes().getShipType());
        setBoards(Integer.parseInt(battleShipGameType.getBoardSize()), battleShipGameType.getBoards().getBoard(), battleshipBuilder);
    }

    // **************************************************** //
    // Defines players' boards
    // **************************************************** //
    private void setBoards(int boardSize, List<BoardType> boards, BattleshipBuilder battleshipBuilder) throws XmlContentException {
        for(BoardType boardType : boards) {
            List<Battleship> battleships = battleshipBuilder.buildUserBattleships(boardType.getShip());
            Board board = new Board(boardSize, battleships);
        }
    }

    // **************************************************** //
    // Defines game players
    // **************************************************** //
    private void setPlayers() {
        List<IPlayer> players = new ArrayList<>();

        players.set(firstPlayerIndex, new Player());
        players.set(secondPlayerIndex, new Player());
    }
}
