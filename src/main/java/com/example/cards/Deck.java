package com.example.cards;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

    private ArrayList<Card> deck = new ArrayList<>();
    private Random random = new Random();
    
    public Deck(){
        reset();
    }

    /**
     * Allows the deck to be reset each round. 
     */
    private void reset(){
        deck.clear();
    
        for(Card.Suit suit : Card.Suit.values()){
            for(Card.Rank rank : Card.Rank.values()){
                deck.add(new Card(rank, suit));
            }
        }
    }

    public int getSize(){
        return deck.size();
    }

    /**
     * Utilises the Fisher-Yates method for shuffling cards. 
     */
    public void shuffle(){

        int n = deck.size();
        
        for(int i = n-1;i > 0; i--){
            int j = random.nextInt(i+1);

            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    /**
     * Removes and returns the specified number of cards from the end of the deck.
     *
     * @param count the amount of cards we want dealt 
     * @return the list of dealt cards
     */
    public ArrayList<Card> deal(int count){
        ArrayList<Card> cards = new ArrayList<>();
        for(int i = 0; i < count; i++){
            cards.add(deck.remove(deck.size() - 1));
        }
        return cards;
    }


}
