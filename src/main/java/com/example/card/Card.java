package com.example.card;


public class Card {
    public boolean isPenaltyCard(Suit suit, Rank rank) {
        return suit == Suit.HEARTS || (suit == Suit.SPADES && rank == Rank.QUEEN);
    }


}
