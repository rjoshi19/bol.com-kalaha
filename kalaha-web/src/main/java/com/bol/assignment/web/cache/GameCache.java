package com.bol.assignment.web.cache;

import com.bol.assignment.data.Board;
import com.bol.assignment.data.Game;
import com.bol.assignment.web.exception.KalahaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.bol.assignment.data.Player.NONE;
import static java.lang.String.format;

/**
 * A simple cache to maintain games for one hour. For future extension of features.
 */
@Component
@Slf4j
public class GameCache {
    private static final Map<String, Game> gameCacheMap = new ConcurrentHashMap<>();

    /**
     * This method create new Game and save the object in a Map.
     *
     * @param board is the initialized board.
     * @return Game object.
     */
    public Game create(Board board){
        var id = UUID.randomUUID().toString();
        var game = new Game(id, board, null, NONE, Instant.now().toEpochMilli());
        gameCacheMap.put(id, game);
        return gameCacheMap.get(id);
    }

    /**
     * This method will return the game object by id.
     *
     * @param id is the game id.
     * @return Game
     */
    public Game findById(String id){
        var game = gameCacheMap.get(id);
        if(game == null){
            throw new KalahaException(format("Game is not found for the id: %s", id));
        }
        return game;
    }

    /**
     * After Every 5 minutes(300000 seconds) interval this method tries to find the 60 minutes old game
     * and remove them from map. So, if there is no activity on a game last 60 minutes, It will
     * destroy.
     */
    @Scheduled(fixedRate = 300000)
    public void deleteOldGame(){
        log.debug("clearing the old games");
        log.debug("size of the map {}", gameCacheMap.size());

        for(Map.Entry<String, Game> entry: gameCacheMap.entrySet()){
            var diff = (System.currentTimeMillis() - entry.getValue().getUpdatedAt());
            var diffMinutes = diff / (60 * 1000);
            if( diffMinutes > 60){
                gameCacheMap.remove(entry.getKey());
            }
        }
    }
}
