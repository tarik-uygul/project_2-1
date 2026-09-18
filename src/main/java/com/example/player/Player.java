package com.example.player;

public class Player {
    private Hand hand;
    private int points;
    
    public Player(Hand hand){
        this.hand = hand;
    }
    public void addPoints(int points){
        this.points += points;
    }
    public Hand getHand(){
        return hand;
    }
    public int getPoints(){
        return points;
    }
}
