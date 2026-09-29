package com.example.player;

public class Player {

    private Hand hand;
    private int points;
    private final String name;
    
    public Player(Hand hand, String name){
        this.hand = hand;
        this.name = name;
    }

    public void addPoints(int points){
        this.points += points;
    }

    public String getName(){
        return name;
    }

    public Hand getHand(){
        return hand;
    }
    
    public int getPoints(){
        return points;
    }
}
