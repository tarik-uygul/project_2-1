package com.example.player;

import java.util.ArrayList;

import com.example.cards.*;

public class Hand {

    private ArrayList<Card> hand = new ArrayList<Card>();

    public ArrayList<Card> getHand(){ 
        return new ArrayList<>(hand);
    }

    public void addCards(ArrayList<Card> cards){
        for(int i = 0; i < cards.size(); i++){
            hand.add(cards.get(i));
        }
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void removeCards(ArrayList<Card> cards){
        for(int i = 0; i < cards.size(); i++){
            hand.remove(cards.get(i));
        }
    }

    public void removeCard(Card card){
        hand.remove(card);
    }

    public boolean hasCard(Card card){
        return false;
    }

    public void clear(){
        hand.clear();
    }

    private void sortCards(){ //suit then rank

    }

}
