package com.bol.assignment.web.service.rule.impl;

import com.bol.assignment.test.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bol.assignment.data.Player.PLAYER1;
import static com.bol.assignment.data.Player.PLAYER2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DistributeStonesTest {
    @InjectMocks
    DistributeStones distributeStones;

    @Test
    public void testSimpleDistribution() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        var startingPit = dummyBoard.getPit(1);
        startingPit.setStones(2);

        distributeStones.distribute.accept(dummyGame, startingPit);
        assertEquals(0, dummyBoard.getPit(1).getStones());
        assertEquals(7, dummyBoard.getPit(2).getStones());
        assertEquals(7, dummyBoard.getPit(3).getStones());
    }

    @Test
    public void testStoneEndingInHouse() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        dummyGame.setActivePlayerOrGameState(PLAYER1);
        var startingPit = dummyBoard.getPit(1);

        distributeStones.distribute.accept(dummyGame, startingPit);
        assertEquals(0, dummyBoard.getPit(1).getStones());
        assertEquals(7, dummyBoard.getPit(2).getStones());
        assertEquals(7, dummyBoard.getPit(3).getStones());
        assertEquals(1, dummyBoard.getPlayer1().house().getStones());
    }

    @Test
    public void testStoneEndingInPLayer1EmptyPitToCollectStonesFromOppositePit() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        dummyGame.setActivePlayerOrGameState(PLAYER1);
        var startingPit = dummyBoard.getPit(1);

        startingPit.setStones(2);
        var endingPit = dummyBoard.getPit(3);
        endingPit.setStones(0);

        distributeStones.distribute.accept(dummyGame, startingPit);
        assertEquals(0, dummyBoard.getPit(3).getStones());
        assertEquals(7, dummyBoard.getPlayer1().house().getStones());
    }

    @Test
    public void testStoneEndingInPLayer2EmptyPitToCollectStonesFromOppositePit() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        dummyGame.setActivePlayerOrGameState(PLAYER2);
        var startingPit = dummyBoard.getPit(10);

        startingPit.setStones(2);
        var endingPit = dummyBoard.getPit(12);
        endingPit.setStones(0);

        distributeStones.distribute.accept(dummyGame, startingPit);
        assertEquals(0, dummyBoard.getPit(12).getStones());
        assertEquals(0, dummyBoard.getPit(2).getStones());
        assertEquals(7, dummyBoard.getPlayer2().house().getStones());
    }

    @Test
    public void testSkipOtherPlayersHouse() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        dummyGame.setActivePlayerOrGameState(PLAYER2);
        var startingPit = dummyBoard.getPit(13);

        startingPit.setStones(9);

        distributeStones.distribute.accept(dummyGame, startingPit);

        //proves player-2's pit received stones after skipping player-1's house
        assertEquals(7, dummyBoard.getPit(8).getStones());

        //proves player-2's house received stones
        assertEquals(1, dummyBoard.getPlayer2().house().getStones());

        //proves player-1's house is skipped
        assertEquals(0, dummyBoard.getPlayer1().house().getStones());
    }
}