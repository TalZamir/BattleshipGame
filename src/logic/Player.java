package logic;

import logic.interfaces.IPlayer;

import java.util.concurrent.TimeUnit;

public class Player implements IPlayer {

    private static final int MINES_AMOUNT = 2;
    private final Board board;
    private final String name;
    private int hits;
    private int misses;
    private int turns;
    private int score;
    private long time;
    private long turnStartTime;
    private int mines;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
        this.hits = 0;
        this.misses = 0;
        this.score = 0;
        this.turns = 0;
        this.time = 0;
        this.mines = MINES_AMOUNT;
    }

    // **************************************************** //
    // Increase hits amonut and turns amount by 1 and handles time
    // **************************************************** //
    public void increaseHit() {
        hits++;
        updateTime();
    }

    // **************************************************** //
    // Increase misses amonut and turns amount by 1 and handles time
    // **************************************************** //
    public void increaseMiss() {
        misses++;
        updateTime();
    }

    // **************************************************** //
    // Decrease mines amount
    // **************************************************** //
    public void decreaseMine() {
        mines--;
        updateTime();
    }

    public void updateTime() {
        time += System.currentTimeMillis() - turnStartTime;
        turns++;
        turnStartTime = System.currentTimeMillis();
    }

    private String averageTime() {
        long avg;
        if (turns != 0) {
            avg = time / turns;
        } else {
            avg = 0;
        }
        String avgStr = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(avg),
                TimeUnit.MILLISECONDS.toSeconds(avg) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(avg)));
        return avgStr;
    }

    // **************************************************** //
    // Increase player score
    // **************************************************** //
    public void increaseScore(int amount) {
        this.score += amount;
    }

    //TODO
    @Override
    public int getUserId() {
        return 0;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return (name + "  -  Score: " + score + "  Turns: " + turns + "  Hits: " + hits + "  Misses: " + misses +
                "  Mines: " + (MINES_AMOUNT - mines) + "/" + MINES_AMOUNT + "  Average move time: " + averageTime());
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int score) {
        this.hits = score;
    }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public void setTurnStartTime(long turnStartTime) { this.turnStartTime = turnStartTime; }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
