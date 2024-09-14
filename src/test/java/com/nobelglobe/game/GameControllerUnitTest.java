package com.nobelglobe.game;

import com.nobelglobe.game.model.GameSession;
import com.nobelglobe.game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class GameControllerUnitTest {
    public static final String SESSION_TERMINATED = "Session terminated.";
    public static final String API_GAME_TERMINATE = "/api/game/terminate";
    public static final String SESSION_ID_XXX = "session_id_xxx";
    public static final String API_GAME_START = "/api/game/start";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RedisTemplate<String, GameSession> redisTemplate;
    @Autowired
    private GameService gameService;

    @MockBean
    private GameService gameServiceMock;

    private String mockSessionId;

    @BeforeEach
    public void setUp() {

        mockSessionId = "session_id_xxx";
    }


    @Test
    public void testStartAPI() throws Exception {
        when(gameServiceMock.createSession(playerName)).thenReturn(mockSessionId);

        mockMvc.perform(post(API_GAME_START)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(SESSION_ID_XXX));

        verify(gameServiceMock, times(1)).createSession(playerName);
    }

    @Test
    public void testTerminateAPI() throws Exception {
        when(gameServiceMock.terminateSession(SESSION_ID_XXX)).thenReturn(SESSION_TERMINATED);

        mockMvc.perform(post(API_GAME_TERMINATE).param("sessionId", SESSION_ID_XXX)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(SESSION_TERMINATED));

        verify(gameServiceMock, times(1)).terminateSession(SESSION_ID_XXX);
    }

}
