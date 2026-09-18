package com.example.player;

import java.util.ArrayList;
import com.example.Deck.Card;

public class Hand {
    private ArrayList<Card> cards = new ArrayList<Card>();

    public Hand(){
        cards = new ArrayList<>();
    }

    public void addCard(Card card){
        cards.add(card);
    }
    public boolean hasCard(Card card){
        return false;
    }
    private void sortCards(){ //suit then rank

    }
}
