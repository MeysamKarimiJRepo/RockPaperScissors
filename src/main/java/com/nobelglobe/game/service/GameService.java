package com.nobelglobe.game.service;

import com.nobelglobe.game.model.GameResult;
import com.nobelglobe.game.model.GameSession;
import com.nobelglobe.game.model.GameStatistics;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameService {

    public static final String SCISSORS = "scissors";
    public static final String PAPER = "paper";
    public static final String ROCK = "rock";
    public static final String DRAW = "draw";
    public static final String WIN = "win";
    public static final String LOSS = "loss";
    private final RedisTemplate<String, GameSession> redisTemplate;

    public GameService(RedisTemplate<String, GameSession> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String createSession(String playerName) {
        GameSession newSession = new GameSession(playerName);
        String sessionId = generateSessionId();
        redisTemplate.opsForValue().set(sessionId, newSession);
        return sessionId;
    }

    private String generateSessionId() {
        return "session_" + System.currentTimeMillis();
    }

    public String terminateSession(String sessionId) {
        redisTemplate.delete(sessionId);
        return "Session terminated.";
    }

    public GameResult playMove(String playerMove, String sessionId) {
        String aiMove = generateMove();
        GameResult gameResult = evaluateGame(playerMove, aiMove);
        updateGameSessionStatistics(sessionId, gameResult.getResult());
        return gameResult;
    }

    public void updateGameSessionStatistics(String sessionId, String result) {
        GameSession gameSession = getGameSession(sessionId);

        switch (result) {
            case WIN -> gameSession.setWins(gameSession.getWins() + 1);
            case LOSS -> gameSession.setLosses(gameSession.getLosses() + 1);
            case DRAW -> gameSession.setDraws(gameSession.getDraws() + 1);
            default -> throw new IllegalArgumentException("Invalid result: " + result);
        }

        redisTemplate.opsForValue().set(sessionId, gameSession);
    }

    private GameSession getGameSession(String sessionId) {
        GameSession gameSession = redisTemplate.opsForValue().get(sessionId);
        if (gameSession == null) {
            throw new IllegalArgumentException("Invalid session ID: " + sessionId);
        }
        return gameSession;
    }

    public GameResult evaluateGame(String playerMove, String aiMove) {
        String result;

        if (playerMove.equals(aiMove)) {
            result = DRAW;
        } else if ((playerMove.equals(ROCK) && aiMove.equals(SCISSORS)) ||
                (playerMove.equals(SCISSORS) && aiMove.equals(PAPER)) ||
                (playerMove.equals(PAPER) && aiMove.equals(ROCK))) {
            result = WIN;
        } else {
            result = LOSS;
        }

        return new GameResult(playerMove, aiMove, result);
    }

    private String generateMove() {
        String[] moves = {"Rock", "Paper", "Scissors"};
        Random random = new Random();
        return moves[random.nextInt(3)];
    }

    public GameStatistics getStatistics(String sessionId) {
        GameSession gameSession = getGameSession(sessionId);
        return new GameStatistics(gameSession.getWins(), gameSession.getLosses(), gameSession.getDraws());
    }
}
