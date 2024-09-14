package com.nobelglobe.game;

import com.nobelglobe.game.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
public class GameIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GameService gameService;

    private MockMvc mockMvc;

    public static final String SESSION_TERMINATED = "Session terminated.";

    @Test
    public void testStartAPI() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(post("/api/game/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.notNullValue()));
    }

    @Test
    public void testTerminateSession() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MvcResult createSessionResult = mockMvc.perform(post("/api/game/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String sessionId = createSessionResult.getResponse().getContentAsString();

        mockMvc.perform(post("/api/game/terminate")
                        .param("sessionId", sessionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(is(SESSION_TERMINATED)));
    }

    @Test
    public void testPlayMove() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MvcResult createSessionResult = mockMvc.perform(post("/api/game/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String sessionId = createSessionResult.getResponse().getContentAsString();

        MvcResult gameResult = mockMvc.perform(post("/api/game/play").param("playerMove","Rock").param("sessionId",sessionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        Assertions.assertNotNull(gameResult.getResponse());
    }

    @Test
    public void testCreateSession() {
        // Call the actual method from GameService
        String sessionId = gameService.createSession();

        // Perform assertions
        assertNotNull(sessionId, "Session ID should not be null");
        assertTrue(sessionId.length() > 0, "Session ID should not be empty");
    }

}
