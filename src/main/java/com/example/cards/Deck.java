package com.example.cards;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck;
    public Deck(){
        deck = new ArrayList<>();
        for(Card.Suit suit : Card.Suit.values()){
            for(Card.Rank rank : Card.Rank.values()){
                deck.add(new Card(rank, suit));
            }
        }
    }
    public Deck shuffle(){
        return null;
    }
}
