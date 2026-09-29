package com.example.cards;

public class Card {

    public enum Suit {
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS
    }
    
    public enum Rank {
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank(){
        return this.rank;
    }
    
    public Suit getSuit(){
        return this.suit;
    }

    @Override
    public String toString(){
        return rank + " of " + suit;
    }
}
