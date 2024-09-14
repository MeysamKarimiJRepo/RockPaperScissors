package com.nobelglobe.game.controller;

import com.nobelglobe.game.service.SessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final SessionService sessionService;


    public GameController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/start")
    public String startGame() {
        return sessionService.createSession();
    }
}
