package com.example.game;

import java.util.ArrayList;

import com.example.Deck.Card;

public class Trick {
    private ArrayList<Card> trick = new ArrayList<>();
    public Trick(ArrayList<Card> trick){
        this.trick = trick;
    }
    
    public void addCard(Card card){
        trick.add(card);
    }
    public Card.Suit getTrickSuit(){
        return trick.get(0).getSuit();
    }
    public int countTrickPoints(){//Hearts 1 pt Queen of Spades 13 pts
        return 0; //should we even count in Trick class?
    }
    public ArrayList<Card> getTrick(){
        return trick;
    }
}
