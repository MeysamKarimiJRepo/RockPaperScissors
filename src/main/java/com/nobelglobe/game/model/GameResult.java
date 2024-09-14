package com.nobelglobe.game.model;

public class GameResult {
    private String playerMove;
    private String aiMove;
    private String result; // win, lose, draw

    public GameResult() {}

    public GameResult(String playerMove, String aiMove, String result) {
        this.playerMove = playerMove;
        this.aiMove = aiMove;
        this.result = result;
    }

    public String getPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(String playerMove) {
        this.playerMove = playerMove;
    }

    public String getAiMove() {
        return aiMove;
    }

    public void setAiMove(String aiMove) {
        this.aiMove = aiMove;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
