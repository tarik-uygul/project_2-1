package com.example.game;

import java.util.List;

import com.example.player.Player;

public class Game {
    
    private static final int FINAL_POINTS = 100;
    private final List<Player> players;

    private Round currentRound;
    private int roundNumber;

    public Game(List<Player> players) {
        this.players = players;
        this.roundNumber = 1;
    }

    public void startRound(){
        currentRound = new Round(players, roundNumber);
        currentRound.startRound();
    }

    public boolean isGameOver(){
        for(Player player : players){
            if(player.getPoints() >= FINAL_POINTS){
                return true;
            }
        }

        return false;
    }

    public Player getWinner(){
        //TODO
        return null;
    }

    public void nextRound(){
        if(!currentRound.isComplete()){
            return;
        }

        if(isGameOver()){
            return;
        }

        roundNumber++;
        startRound();
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

}
