package com.nobelglobe.game.model;

import java.io.Serializable;

public class GameSession implements Serializable {
    private int wins;
    private int losses;
    private int draws;

    private String playerName;

    public GameSession() {
    }

    public GameSession(String playerName) {
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.playerName = playerName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
