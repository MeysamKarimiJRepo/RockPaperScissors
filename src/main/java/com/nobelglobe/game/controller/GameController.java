package com.nobelglobe.game.controller;

import com.nobelglobe.game.model.GameResult;
import com.nobelglobe.game.service.GameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;


    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public String startGame() {
        return gameService.createSession();
    }

    @PostMapping("/terminate")
    public String terminateGame(@RequestParam String sessionId) {
        return gameService.terminateSession(sessionId);
    }

    @PostMapping("/play")
    public GameResult playMove(@RequestParam String playerMove,@RequestParam String sessionId ) {
        return gameService.playMove(playerMove, sessionId);
    }
}
