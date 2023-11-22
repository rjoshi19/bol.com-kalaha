package com.bol.assignment.test.utils;

import com.bol.assignment.data.Board;
import com.bol.assignment.data.Game;
import com.bol.assignment.data.House;
import com.bol.assignment.data.Player;

import java.time.Instant;

import static com.bol.assignment.data.Pit.P1_HOUSE_INDEX;
import static com.bol.assignment.data.Pit.P2_HOUSE_INDEX;
import static com.bol.assignment.data.Player.*;

public class TestUtils {
    public static Game createDummyGame(Board board) {
        var id = "test-dummy-1";
        return new Game(id, board, null, NONE, Instant.now().toEpochMilli());
    }

    public static Board createDummyBoard() {
        var houseForPlayer1 = new House(P1_HOUSE_INDEX, PLAYER1, 0);
        var player1 = new Player(PLAYER1, "player-1", houseForPlayer1);

        var houseForPlayer2 = new House(P2_HOUSE_INDEX, PLAYER2, 0);
        var player2 = new Player(PLAYER2, "player-2", houseForPlayer2);

        return new Board(player1, player2, 6);
    }
}
