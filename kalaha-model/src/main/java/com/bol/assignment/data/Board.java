package com.bol.assignment.data;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.bol.assignment.data.Pit.P1_HOUSE_INDEX;
import static com.bol.assignment.data.Pit.P2_HOUSE_INDEX;
import static com.bol.assignment.data.Player.PLAYER1;

@Getter
public class Board {
    private final Map<Integer, Pit> pits;
    private final Player player1;
    private final Player player2;

    public Board(Player player1, Player player2, int stones) {
        this.player1 = player1;
        this.player2 = player2;
        this.pits = initPits(stones);
    }


    /**
     * @param stones number of stones in each pit at the starting of the game
     * @return map of pits with index for easy fetch
     */
    private Map<Integer, Pit> initPits(int stones) {
        Map<Integer, Pit> pits = new ConcurrentHashMap<>();
        for(int i= 1; i < P1_HOUSE_INDEX; i++){
            Pit pit = new Pit(i, player1.index(), stones);
            pits.put(i, pit);
        }

        for(int i= P1_HOUSE_INDEX + 1; i < P2_HOUSE_INDEX; i++){
            Pit pit = new Pit(i, player2.index(), stones);
            pits.put(i, pit);
        }
        pits.put(P1_HOUSE_INDEX, player1.house());
        pits.put(P2_HOUSE_INDEX, player2.house());
        return pits;
    }

    /**
     * @param pitIndex The index of the @{@link Pit} that should be emptied
     */
    public int emptyPit(int pitIndex) {
        return pits.get(pitIndex).emptyPit();
    }

    /**
     * @param pitIndex     The index of the @{@link Pit} that should be edited
     * @param stonesAmount The amount of stones that should be added to the @{@link Pit}
     */
    public void addToPit(int pitIndex, int stonesAmount) {
        pits.get(pitIndex).receiveStones(stonesAmount);
    }

    /**
     * @param playerNumber       The player to which's kalaha we need to add stones
     * @param stonesAmount The amount of stones that needs to be added to the @{@link Player}s @{@link House}
     */
    public void addToHouse(int playerNumber, int stonesAmount) {
        Player player = playerNumber == PLAYER1 ? player1 : player2;
        player.house().receiveStones(stonesAmount);
    }


    /**
     * Compares the players Kalahas to determine the winner
     *
     * @return the @{@link int} enum associated with the winning player
     */
    public Player winningPlayer() {
        if (player1.house().getStones() > player2.house().getStones()) {
            // player 1 won
            return player1;
        } else if (player1.house().getStones() == player2.house().getStones()) {
            // draw
            return null;
        } else {
            // player 2 won
            return player2;
        }
    }

    /**
     * @param index of the required @{@link Pit}
     * @return the Pit at the specific index
     */
    public Pit getPit(int index) {
        return pits.get(index);
    }

    /**
     * @param playerNumber
     * @return total stones in the pit for player with @{link playerNumber}
     */
    public int getStonesInPitsForPlayer(int playerNumber) {
        return pits.values().stream()
                .filter(pit -> null != pit && pit.getPlayerNumber() == playerNumber && !(pit instanceof House) )
                .mapToInt(Pit::getStones)
                .sum();
    }
}
