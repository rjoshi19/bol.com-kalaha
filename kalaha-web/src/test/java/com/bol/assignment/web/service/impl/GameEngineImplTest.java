package com.bol.assignment.web.service.impl;

import com.bol.assignment.data.Game;
import com.bol.assignment.data.Pit;
import com.bol.assignment.test.utils.TestUtils;
import com.bol.assignment.web.service.function.CheckedBiConsumer;
import com.bol.assignment.web.service.rule.impl.BeginGame;
import com.bol.assignment.web.service.rule.impl.DistributeStones;
import com.bol.assignment.web.service.rule.impl.EndGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameEngineImplTest {
    @InjectMocks
    GameEngineImpl gameEngine;

    BeginGame beginGame;
    DistributeStones distributeStones;
    EndGame endGame;

    @Mock
    CheckedBiConsumer<Game, Pit> checkedBiConsumer;

    @BeforeEach
    public void init() {
        beginGame = new BeginGame();
        distributeStones = new DistributeStones();
        endGame = new EndGame();

        gameEngine = new GameEngineImpl(beginGame, distributeStones, endGame);
    }


    @Test
    void testPlay() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);
        var pit = dummyBoard.getPit(1);

        gameEngine.play(dummyGame, pit);
    }
}