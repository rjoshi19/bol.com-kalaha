package com.bol.assignment.web.service.impl;

import com.bol.assignment.data.Game;
import com.bol.assignment.data.Pit;
import com.bol.assignment.web.service.GameEngine;
import com.bol.assignment.web.service.rule.impl.BeginGame;
import com.bol.assignment.web.service.rule.impl.DistributeStones;
import com.bol.assignment.web.service.rule.impl.EndGame;
import org.springframework.stereotype.Service;

@Service
public class GameEngineImpl implements GameEngine {
    private final BeginGame beginGame;
    private final DistributeStones distributeStones;
    private final EndGame endGame;


    public GameEngineImpl(BeginGame beginGame, DistributeStones distributeStones, EndGame endGame) {
        this.beginGame = beginGame;
        this.distributeStones = distributeStones;
        this.endGame = endGame;
    }

    /**
     * This method calls all the rules one by one
     * Slight variation of Pipeline design pattern
     * @param game game current being played
     * @param pit current pit being played
     */
    @Override
    public void play(Game game, Pit pit) {
        beginGame.checkPlayerTurn
                .andThen(beginGame.checkEmptyStartingPit)
                .andThen(distributeStones.distribute)
                .andThen(endGame.finishGameAndResetBoard)
                .accept(game, pit);
    }
}
