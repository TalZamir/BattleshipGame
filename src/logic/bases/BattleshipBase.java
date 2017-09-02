package logic.bases;

import logic.interfaces.IBuildShipDetails;

/**
 * Created by barakm on 02/09/2017
 */
public abstract class BattleshipBase implements IBuildShipDetails {

    protected int score;
    protected int lengthForIsAlive;
    protected boolean isAlive;

    protected BattleshipBase() {
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void decrementLength() {
        if (--lengthForIsAlive == 0) {
            isAlive = false;
        }
    }

    public int getScore() {
        return score;
    }
}
