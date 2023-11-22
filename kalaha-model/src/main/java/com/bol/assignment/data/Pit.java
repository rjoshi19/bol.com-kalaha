package com.bol.assignment.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pit {
    public static int P1_HOUSE_INDEX = 7;
    public static int P2_HOUSE_INDEX = 14;

    private int stones;
    private int playerNumber;
    private int index;

    public Pit(int index, int playerNumber, int stones) {
        this.stones = stones;
        this.playerNumber = playerNumber;
        this.index = index;
    }

    public int emptyPit() {
        int stonesInHand = getStones();
        stones = 0;
        return stonesInHand;
    }

    /**
     * Allows a pit to receive a certain amount of stones
     *
     * @param amountStones The amount of stones to be added to the specific pit
     */
    public void receiveStones(int amountStones) {
        stones += amountStones;
    }

}
