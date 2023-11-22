package com.bol.assignment.web.service.rule.impl;

import com.bol.assignment.constants.PitType;
import com.bol.assignment.data.Board;
import com.bol.assignment.data.Game;
import com.bol.assignment.data.House;
import com.bol.assignment.data.Pit;
import com.bol.assignment.web.service.function.CheckedBiConsumer;
import com.bol.assignment.web.service.rule.GameRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bol.assignment.constants.PitType.*;
import static com.bol.assignment.data.Pit.P1_HOUSE_INDEX;
import static com.bol.assignment.data.Player.PLAYER1;
import static com.bol.assignment.data.Player.PLAYER2;

@Service
@Slf4j
public class DistributeStones implements GameRule {
    /**
     * 1. Pick up the stones in the pit and empty the pit
     * 2. Start distributing the stones from next pit
     * 3. Keep a track of the following: stone was last placed in a Pit or a House, index of the last pit to receive the stones
     * 4. If the current pit is a House and if it belongs to the player whose turn it is, then place the stone in the House. If not then skip the House.
     * 5. If the last stone was not placed in a House, put the stone in the next pit.
     * 6. After all stones are distributed, if the last stone was placed in the player's empty pit, collect all stones from opposite player's pit and place them in the player's House.
     * 7. Choose and set the next active player
     */
    public CheckedBiConsumer<Game, Pit> distribute = (game, fromPit) -> {
        var stones = fromPit.emptyPit();

        var currentPitIndex = fromPit.getIndex() + 1;
        var lastStonePlacedIn = NONE;
        var lastUpdatedIndex = fromPit.getIndex();

        var numOfPits = game.getBoard().getPits().size();

        while (stones != 0) {
            var currentPit = game.getBoard().getPit(currentPitIndex);
            lastStonePlacedIn = PIT;
            if (currentPit instanceof House) {
                if (game.getActivePlayerOrGameState() == currentPit.getPlayerNumber()) {
                    game.getBoard().addToHouse(currentPit.getPlayerNumber(), 1);
                    lastStonePlacedIn = HOUSE;
                } else {
                    currentPitIndex = getNextPitIndex(numOfPits, currentPitIndex);
                    continue;
                }
            }

            if (HOUSE != lastStonePlacedIn) {
                game.getBoard().addToPit(currentPitIndex, 1);
            }

            lastUpdatedIndex = currentPitIndex;
            currentPitIndex = getNextPitIndex(numOfPits, currentPitIndex);
            --stones;
        }

        collectOppositeStones(game.getBoard(), lastUpdatedIndex, game.getActivePlayerOrGameState());
        game.setActivePlayerOrGameState(determineNextPlayer(fromPit, lastStonePlacedIn));
    };

    /**
     * If the last stone was placed in a House, continue with the same player
     * else switch players
     * @param pit
     * @param lastStonePlacedIn
     * @return
     */
    private int determineNextPlayer(Pit pit, PitType lastStonePlacedIn) {
        if (lastStonePlacedIn == HOUSE) {
            return pit.getPlayerNumber();
        } else {
            return pit.getPlayerNumber() == PLAYER1 ? PLAYER2 : PLAYER1;
        }
    }

    private int getNextPitIndex(int numOfPits, int index) {
        return index == numOfPits ? 1 : index + 1;
    }

    /**
     * A player has distributed a stone into one of its own empty pits, all the stones
     * from their own pit, plus the opposite pit, will be placed in their own kalaha
     *
     * @param index the pit index for which the opposite pit needs to be emptied
     */
    private void collectOppositeStones(Board board, int index, int activePlayer) {
        var numberOfPits = board.getPits().size();
        var pit = board.getPit(index);
        if (pit instanceof House || !hasLandedInEmptyPit(pit)) {
            return;
        }

        if ((activePlayer == PLAYER1 && index < P1_HOUSE_INDEX)) {
            log.info("collecting all stones from {} putting in {}", (numberOfPits - index), index);
            var stonesWon = board.emptyPit(numberOfPits - index) + board.emptyPit(index);
            board.addToHouse(PLAYER1, stonesWon);
        }

        if (activePlayer == PLAYER2 && index > P1_HOUSE_INDEX) {
            log.info("collecting all stones from {} putting in {}", (numberOfPits - index), index);
            var stonesWon = board.emptyPit(numberOfPits - index) + board.emptyPit(index);
            board.addToHouse(PLAYER2, stonesWon);
        }
    }

    private boolean hasLandedInEmptyPit(Pit pit) {
        return pit.getStones() == 1;
    }
}
