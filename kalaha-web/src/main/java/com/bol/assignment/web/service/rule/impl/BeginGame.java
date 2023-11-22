package com.bol.assignment.web.service.rule.impl;

import com.bol.assignment.data.Game;
import com.bol.assignment.data.Pit;
import com.bol.assignment.web.exception.KalahaIllegalMoveException;
import com.bol.assignment.web.service.function.CheckedBiConsumer;
import com.bol.assignment.web.service.rule.GameRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bol.assignment.data.Player.*;
import static java.lang.String.format;

@Service
@Slf4j
public class BeginGame implements GameRule {
    /**
     * Check if pit is empty. It denotes an illegal move.
     */
    public CheckedBiConsumer<Game, Pit> checkEmptyStartingPit = (game, startingPit) -> {
        log.debug("Checking is the selected pit is empty");
        if (startingPit.getStones() == 0 && (game.getActivePlayerOrGameState() != GAME_OVER)) {
            throw new KalahaIllegalMoveException("Can not start from empty pit");
        }
    };

    /**
     * This method checks if the player that has played is the one whose turn it is.
     * If the game hasn't started, the selected pit's player is set as starting player or active player
     * if the turn is player-1's and player-2's pit is selected for play, error is displayed. Vice-versa is true as well,
     */
    public CheckedBiConsumer<Game, Pit> checkPlayerTurn = (game, startingPit) -> {
        log.debug("Checking is the correct player pit is chosen");
        if (game.getActivePlayerOrGameState() == NONE) {
            var playerNumber = startingPit.getPlayerNumber() == PLAYER1 ? PLAYER1 : PLAYER2;
            game.setActivePlayerOrGameState(playerNumber);
        }

        if ((game.getActivePlayerOrGameState() == PLAYER1 && startingPit.getPlayerNumber() != PLAYER1) ||
                (game.getActivePlayerOrGameState() == PLAYER2 && startingPit.getPlayerNumber() != PLAYER2)) {
            throw new KalahaIllegalMoveException(format("Its not your turn, let the other player play. It is player-%s's turn.", game.getActivePlayerOrGameState()));
        }
    };
}