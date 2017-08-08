package logic.interfaces;

import logic.Battleship;
import logic.enums.GameType;

import java.util.List;

public interface IGameSerializer {

    GameType getGameType();

    int getBoardSize();

    List<Battleship> getBattleships();

    int getMines();

}
