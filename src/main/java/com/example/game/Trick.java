package com.example.game;

import java.util.ArrayList;

import com.example.cards.*;
import com.example.player.Player;

public class Trick {

    private ArrayList<Play> plays = new ArrayList<>();

    public void addPlay(Play play){
        plays.add(play);
    }

    public Card.Suit getTrickSuit() {
        return plays.get(0).getCard().getSuit();
    }

    /**
     * This method iterates through all the plays in the trick and their points. 
     * @return the amount of accumlated points.
     */
    public int countTrickPoints(){
        int points = 0;
        for(Play play : plays){
            if(play.getCard().getSuit() == Card.Suit.HEARTS){
                points++;
            } else if(play.getCard().getSuit() == Card.Suit.SPADES && play.getCard().getRank() == Card.Rank.QUEEN){
                points += 13;
            }
        }
        return points;
    }


    public Player getLeader() {
    // TODO: return the player who played the highest card of the lead suit
    return null;
    }  

    public boolean isComplete(int numOfPlayers){
        return plays.size() == numOfPlayers;
    }

    public ArrayList<Play> getTrick(){
        return plays; //though i dont know about this, will the AI be able to change it later? I will have to look into it, if so
        // i will just simply again return a shallow copy - like i did in Hand.
    }

    public boolean isEmpty(){
        return plays.isEmpty();
    }
}

