package com.nobelglobe.game.service;

import com.nobelglobe.game.model.GameSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final RedisTemplate<String, GameSession> redisTemplate;

    public SessionService(RedisTemplate<String, GameSession> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String createSession() {
        GameSession newSession = new GameSession();
        String sessionId = generateSessionId();
        redisTemplate.opsForValue().set(sessionId, newSession);
        return sessionId;
    }

    private String generateSessionId() {
        return "session_" + System.currentTimeMillis();
    }
}
