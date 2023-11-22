package com.bol.assignment.web.service.rule.impl;

import com.bol.assignment.data.Game;
import com.bol.assignment.data.House;
import com.bol.assignment.data.Pit;
import com.bol.assignment.data.Player;
import com.bol.assignment.web.service.function.CheckedBiConsumer;
import com.bol.assignment.web.service.rule.GameRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bol.assignment.data.Pit.P1_HOUSE_INDEX;
import static com.bol.assignment.data.Pit.P2_HOUSE_INDEX;
import static com.bol.assignment.data.Player.*;

@Service
@Slf4j
public class EndGame implements GameRule {
    /**
     * Check if any of the player has all empty pits.
     * If yes, then game is over. State is changed to Game Over and winning player is set
     * After deciding the winner, reset the board
     */
    public CheckedBiConsumer<Game, Pit> finishGameAndResetBoard = (game, pit) -> {
        final var board = game.getBoard();

        final var stonesInPitsForPlayer1 = board.getStonesInPitsForPlayer(PLAYER1);
        final var stonesInPitsForPlayer2 = board.getStonesInPitsForPlayer(PLAYER2);

        if (stonesInPitsForPlayer1 == 0 || stonesInPitsForPlayer2 == 0) {
            game.setActivePlayerOrGameState(GAME_OVER);

            board.addToHouse(PLAYER1, stonesInPitsForPlayer1);
            board.addToHouse(PLAYER2, stonesInPitsForPlayer2);

            game.setWinner(board.winningPlayer());
            resetBoard(game);
        }
    };

    private void resetBoard(Game game){
        for(var key: game.getBoard().getPits().keySet()){
            if(key.equals(P1_HOUSE_INDEX) || key.equals(P2_HOUSE_INDEX)) {
                continue;
            }
            Pit pit = game.getBoard().getPits().get(key);
            pit.setStones(0);
        }
    }
}