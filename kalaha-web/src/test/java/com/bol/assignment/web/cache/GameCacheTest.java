package com.bol.assignment.web.cache;

import com.bol.assignment.data.Game;
import com.bol.assignment.test.utils.TestUtils;
import com.bol.assignment.web.exception.KalahaException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameCacheTest {
    @InjectMocks
    GameCache gameCache;

    @Test
    public void create() {
        var dummyBoard = TestUtils.createDummyBoard();
        var game = gameCache.create(dummyBoard);

        assertNotNull(game);
    }

    @Test
    public void findById() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame = gameCache.create(dummyBoard);

        var fetchedGame = gameCache.findById(dummyGame.getId());
        assertEquals(dummyGame, fetchedGame);
    }

    @Test
    public void findByIdFailure() {
        KalahaException kalahaException = assertThrows(KalahaException.class, () -> {
            gameCache.findById("dummy-id");
        });

        assertEquals("Game is not found for the id: dummy-id", kalahaException.getMessage());
    }

    @Test
    @SneakyThrows
    public void deleteOldGame() {
        var dummyBoard = TestUtils.createDummyBoard();
        var dummyGame1 = gameCache.create(dummyBoard);
        var dummyGame2 = gameCache.create(dummyBoard);

        dummyGame2.setUpdatedAt(1700530581000L);

        Class<?> mockServiceClass = Class.forName("com.bol.assignment.web.cache.GameCache");
        Field field = mockServiceClass.getDeclaredField("gameCacheMap");
        field.setAccessible(true);

        gameCache.deleteOldGame();

        Map<String, Game> gameCacheMap = (Map<String, Game>) field.get(1);
        assertEquals(1, gameCacheMap.size());
    }
}