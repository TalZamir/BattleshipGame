package logic;

import logic.interfaces.IPlayer;

import java.util.concurrent.TimeUnit;

public class Player implements IPlayer {

    private final Board board;
    private final String name;
    private int hits;
    private int misses;
    private int turns;
    private long time;
    private long turnStartTime;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
        this.hits = 0;
        this.misses = 0;
        this.turns = 0;
        this.time = 0;
    }

    // **************************************************** //
    // Increase hits amonut and turns amount by 1 and handles time
    // **************************************************** //
    public void increaseHit() {
        hits++;
        turns++;
        updateTime();
    }

    // **************************************************** //
    // Increase misses amonut and turns amount by 1 and handles time
    // **************************************************** //
    public void increaseMiss() {
        misses++;
        turns++;
        updateTime();
    }

    public void updateTime() {
        time += System.currentTimeMillis() - turnStartTime;
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
        return (name + "  -  Hits: " + hits + "  Misses: " + misses + "  Average move time: " + averageTime());
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
}
