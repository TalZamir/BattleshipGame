package logic;

import logic.enums.GameType;
import logic.interfaces.IPlayer;
import logic.interfaces.ISerializer;
import logic.serializers.XmlSerializer;
import module.BattleShipGameType;
import module.BoardsType;

import java.util.ArrayList;
import java.util.List;

public class TheGame {

    private final int firstPlayerIndex;
    private final int secondPlayerIndex;
    private final GameType gameType;

    public TheGame() {
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
    private void initGameComponents(BattleShipGameType battleShipGameType) {
        setPlayers();

        setBoards(Integer.valueOf(battleShipGameType.getBoardSize()), battleShipGameType.getBoards());
    }

    private void setBoards(int boardSize, BoardsType boards) {
        //        List<Board> boards = new ArrayList<>();

        // TODO: Pass a Battleshiplist
        new Board(boardSize, null);
    }

    private void setPlayers() {
        List<IPlayer> players = new ArrayList<>();

        players.set(firstPlayerIndex, new Player());
        players.set(secondPlayerIndex, new Player());
    }
}
