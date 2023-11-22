package com.bol.assignment.web.service.impl;

import com.bol.assignment.data.Board;
import com.bol.assignment.test.utils.TestUtils;
import com.bol.assignment.web.cache.GameCache;
import com.bol.assignment.web.service.GameEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
    @InjectMocks
    GameServiceImpl gameService;

    @Mock
    GameCache gameCache;

    @Mock
    GameEngine gameEngineImpl;

    @Test
    void testInitGame() {
        var dummyBoard = TestUtils.createDummyBoard();

        var dummyGame = TestUtils.createDummyGame(dummyBoard);
        when(gameCache.create(any(Board.class))).thenReturn(dummyGame);

        var game = gameService.initGame(6);
        assertNotNull(game);
        assertEquals(game, dummyGame);
    }

    @Test
    void testPlay() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        lenient().when(gameCache.findById(dummyGame.getId())).thenReturn(dummyGame);

        var game = gameService.play("test-dummy-1", 1);
        assertNotNull(game);
    }
}