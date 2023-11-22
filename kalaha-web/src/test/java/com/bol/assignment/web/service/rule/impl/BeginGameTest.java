package com.bol.assignment.web.service.rule.impl;

import com.bol.assignment.test.utils.TestUtils;
import com.bol.assignment.web.exception.KalahaIllegalMoveException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bol.assignment.data.Player.PLAYER1;
import static com.bol.assignment.data.Player.PLAYER2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BeginGameTest {
    @InjectMocks
    BeginGame beginGame;

    @Test
    public void testCheckPlayer1Turn() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        //check player-1's turn
        var pit = dummyBoard.getPit(1);

        beginGame.checkPlayerTurn.accept(dummyGame, pit);
        assertEquals(PLAYER1, dummyGame.getActivePlayerOrGameState());
    }

    @Test
    public void testCheckPlayer2Turn() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        //check player-2's turn
        var pit = dummyBoard.getPit(11);

        beginGame.checkPlayerTurn.accept(dummyGame, pit);
        assertEquals(PLAYER2, dummyGame.getActivePlayerOrGameState());
    }

    @Test
    public void testCheckIncorrectPlay() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        //check player-1's turn
        final var pit1 = dummyBoard.getPit(1);

        beginGame.checkPlayerTurn.accept(dummyGame, pit1);
        assertEquals(PLAYER1, dummyGame.getActivePlayerOrGameState());

        //check player-2's turn
        final var pit2 = dummyBoard.getPit(11);

        KalahaIllegalMoveException kalahaIllegalMoveException = assertThrows(KalahaIllegalMoveException.class,
                () -> beginGame.checkPlayerTurn.accept(dummyGame, pit2));

        assertEquals(kalahaIllegalMoveException.getMessage(), "Its not your turn, let the other player play. It is player-1's turn.");
    }

    @Test
    public void testCheckEmptyStartingPit() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        final var pit = dummyBoard.getPit(1);
        pit.setStones(0);

        KalahaIllegalMoveException kalahaIllegalMoveException = assertThrows(KalahaIllegalMoveException.class,
                () -> beginGame.checkEmptyStartingPit.accept(dummyGame, pit));

        assertEquals(kalahaIllegalMoveException.getMessage(), "Can not start from empty pit");
    }
}