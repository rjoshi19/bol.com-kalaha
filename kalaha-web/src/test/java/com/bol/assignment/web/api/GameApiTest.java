package com.bol.assignment.web.api;

import com.bol.assignment.data.Game;
import com.bol.assignment.test.utils.TestUtils;
import com.bol.assignment.web.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameApiTest {
    @InjectMocks
    GameApi gameApi;

    @Mock
    GameService gameService;

    @Test
    public void initBoard() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        when(gameService.initGame(6)).thenReturn(dummyGame);

        ResponseEntity<Game> response = gameApi.initBoard(6);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void play() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = TestUtils.createDummyGame(dummyBoard);

        when(gameService.play(dummyGame.getId(), 1)).thenReturn(dummyGame);

        ResponseEntity<Game> response = gameApi.play(dummyGame.getId(), 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}