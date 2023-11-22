package com.bol.assignment.web.service.impl;

import com.bol.assignment.data.Board;
import com.bol.assignment.data.Game;
import com.bol.assignment.data.House;
import com.bol.assignment.data.Player;
import com.bol.assignment.web.cache.GameCache;
import com.bol.assignment.web.service.GameEngine;
import com.bol.assignment.web.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bol.assignment.data.Pit.P1_HOUSE_INDEX;
import static com.bol.assignment.data.Pit.P2_HOUSE_INDEX;
import static com.bol.assignment.data.Player.PLAYER1;
import static com.bol.assignment.data.Player.PLAYER2;

@Service
@Slf4j
public class GameServiceImpl implements GameService {
    private final GameCache gameCache;
    private final GameEngine gameEngineImpl;

    public GameServiceImpl(GameCache gameCache, GameEngine gameEngineImpl) {
        this.gameCache = gameCache;
        this.gameEngineImpl = gameEngineImpl;
    }

    private Board initializeBoard(int stones) {
        var houseForPlayer1 = new House(P1_HOUSE_INDEX, PLAYER1, 0);
        var player1 = new Player(PLAYER1, "player-1", houseForPlayer1);

        var houseForPlayer2 = new House(P2_HOUSE_INDEX, PLAYER2, 0);
        var player2 = new Player(PLAYER2, "player-2", houseForPlayer2);

        return new Board(player1, player2, stones);
    }

    @Override
    public Game initGame(int stones) {
        var board = initializeBoard(stones);
        return gameCache.create(board);
    }

    @Override
    public Game play(String gameId, int pitIndex) {
        log.debug("gameId {}, pitIndex {}", gameId, pitIndex);

        var game = gameCache.findById(gameId);

        var pit = game.getBoard().getPit(pitIndex);
        gameEngineImpl.play(game, pit);

        return game;
    }
}
