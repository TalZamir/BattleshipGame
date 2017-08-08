package Logic.Interfaces;

import Logic.Battleship;
import Logic.Enums.GameType;

import java.util.List;

public interface IGameSerializer {

    GameType getGameType();

    int getBoardSize();

    List<Battleship> getBattleships();

    int getMines();

}
