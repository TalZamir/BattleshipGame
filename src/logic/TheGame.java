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

    private final GameType gameType;
    private Player[] players;
    private int turns;



    public TheGame() throws XmlContentException {
        players = new Player[2];
        turns = 1;
        ISerializer serializer = new XmlSerializer();
        BattleShipGameType battleShipGameType = serializer.getBattleShipGameType();

        initGameComponents(battleShipGameType);

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
        int playIndex = 0;
        for(BoardType boardType : boards) {
            List<Battleship> battleships = battleshipBuilder.buildUserBattleships(boardType.getShip()); // Builds player battleships
            Board board = new Board(boardSize, battleships); // Builds player board
            players[playIndex].setBoard(board); // Sets player board
        }
    }

    // **************************************************** //
    // Returns the current player ships board
    // **************************************************** //
    public char[][] getCurrentPlayerShipBoard() {
        int currentPlayerIndex = turns % 2;
        return players[currentPlayerIndex].getBoard().getAllieMode();
    }
}
