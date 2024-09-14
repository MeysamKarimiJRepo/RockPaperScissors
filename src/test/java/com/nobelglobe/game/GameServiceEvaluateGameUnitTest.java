package com.nobelglobe.game;

import com.nobelglobe.game.model.GameResult;
import com.nobelglobe.game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameServiceEvaluateGameUnitTest {
    private GameService gameService;

    @BeforeEach
    public void setup() {
        gameService = new GameService(null);
    }

    @Test
    public void testEvaluateGame_win() {
        GameResult result = gameService.evaluateGame("rock", "scissors");
        assertEquals("win", result.getResult());
    }

    @Test
    public void testEvaluateGame_loss() {
        GameResult result = gameService.evaluateGame("rock", "paper");
        assertEquals("loss", result.getResult());
    }

    @Test
    public void testEvaluateGame_draw() {
        GameResult result = gameService.evaluateGame("rock", "rock");
        assertEquals("draw", result.getResult());
    }

    @Test
    public void testEvaluateGame_paperBeatsRock() {
        GameResult result = gameService.evaluateGame("paper", "rock");
        assertEquals("win", result.getResult());
    }
}
