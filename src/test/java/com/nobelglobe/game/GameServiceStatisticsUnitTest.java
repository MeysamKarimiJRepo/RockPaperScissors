package com.nobelglobe.game;
import com.nobelglobe.game.model.GameSession;
import com.nobelglobe.game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class GameServiceStatisticsUnitTest {

    @Mock
    private RedisTemplate<String, GameSession> redisTemplate;

    @Mock
    private ValueOperations<String, GameSession> valueOperations;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void testUpdateGameSessionStatistics_win() {
        String sessionId = "session123";
        GameSession mockSession = new GameSession("player1");
        mockSession.setWins(2);
        mockSession.setLosses(1);
        mockSession.setDraws(1);

        when(valueOperations.get(sessionId)).thenReturn(mockSession);

        gameService.updateGameSessionStatistics(sessionId, "win");

        assertEquals(3, mockSession.getWins());
        assertEquals(1, mockSession.getLosses());
        assertEquals(1, mockSession.getDraws());

        verify(valueOperations).set(sessionId, mockSession);
    }

    @Test
    public void testUpdateGameSessionStatistics_loss() {
        String sessionId = "session456";
        GameSession mockSession = new GameSession("Player1");
        mockSession.setWins(3);
        mockSession.setLosses(2);
        mockSession.setDraws(1);

        when(valueOperations.get(sessionId)).thenReturn(mockSession);

        gameService.updateGameSessionStatistics(sessionId, "loss");

        // Verify that the session's loss count is updated
        assertEquals(3, mockSession.getWins());
        assertEquals(3, mockSession.getLosses());
        assertEquals(1, mockSession.getDraws());

        verify(valueOperations).set(sessionId, mockSession);
    }

    @Test
    public void testUpdateGameSessionStatistics_invalidSession() {
        String sessionId = "invalidSession";

        when(valueOperations.get(sessionId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            gameService.updateGameSessionStatistics(sessionId, "win");
        });

        verify(valueOperations, never()).set(anyString(), any(GameSession.class));
    }
}
