package com.bol.assignment.web.service.rule.impl;

import com.bol.assignment.data.Pit;
import com.bol.assignment.test.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.Map;

import static com.bol.assignment.data.Player.PLAYER1;
import static com.bol.assignment.data.Player.PLAYER2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
class EndGameTest {
    @InjectMocks
    EndGame endGame;

    @Test
    public void testFinishGameAndResetBoardWhenPlayer1HasNoStonesLeft() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);
        dummyGame.setActivePlayerOrGameState(PLAYER1);

        Iterator<Map.Entry<Integer, Pit>> iterator = dummyBoard
                .getPits()
                .entrySet()
                .stream()
                .filter(pitEntry -> pitEntry.getValue().getPlayerNumber() == PLAYER1)
                .iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, Pit> pitEntry = iterator.next();
            pitEntry.getValue().setStones(0);
        }

        endGame.finishGameAndResetBoard.accept(dummyGame, dummyBoard.getPit(1));
        assertEquals(PLAYER2, dummyGame.getWinner().index());
    }

    @Test
    public void testFinishGameAndResetBoardWhenPlayer2HasNoStonesLeft() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);
        dummyGame.setActivePlayerOrGameState(PLAYER2);

        Iterator<Map.Entry<Integer, Pit>> iterator = dummyBoard
                .getPits()
                .entrySet()
                .stream()
                .filter(pitEntry -> pitEntry.getValue().getPlayerNumber() == PLAYER2)
                .iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, Pit> pitEntry = iterator.next();
            pitEntry.getValue().setStones(0);
        }

        endGame.finishGameAndResetBoard.accept(dummyGame, dummyBoard.getPit(1));
        assertEquals(PLAYER1, dummyGame.getWinner().index());
    }
}