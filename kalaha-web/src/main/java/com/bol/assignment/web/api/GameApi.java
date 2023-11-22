package com.bol.assignment.web.api;

import com.bol.assignment.data.Game;
import com.bol.assignment.web.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/kalaha")
public class GameApi {

    private final GameService gameService;

    public GameApi(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping(value = "/games/")
    public ResponseEntity<Game> initBoard(@RequestParam(name = "stone", defaultValue = "6", required = false) int numberOfStones) {
        log.debug("Initializing Game");
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.initGame(numberOfStones));
    }


    @PutMapping("/games/{gameId}/pits/{pitIndex}")
    public ResponseEntity<Game> play(@PathVariable String gameId, @PathVariable int pitIndex) {
        log.debug("Starting game");
        return ResponseEntity.ok().body(gameService.play(gameId, pitIndex));
    }
}