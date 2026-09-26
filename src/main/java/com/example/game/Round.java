package com.example.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.cards.*;
import com.example.player.*;

public class Round {

    private List<Player> players;

    private Player leader;
    private Trick currentTrick;
    private Deck deck;

    private boolean heartsBroken = false;
    private int roundNumber; 

    private PassDirection pass;
    private RoundPhase phase;
    private ArrayList<Trick> pastTricks;

    public Round(List<Player> players, int roundNumber) {
        this.players = players;
        this.roundNumber = roundNumber;
        this.deck = new Deck();
        this.pastTricks = new ArrayList<>();
    }

    /** This method starts the round */
    public void startRound(){
        phase = RoundPhase.DEALING;
        determinePassDirection();
        shuffleAndDeal();
    }

    /** This method starts the playing phase */
    public void startPlaying(){
        findFirstLeader();
        startTrick();
        phase = RoundPhase.PLAYING;
    }

    private void shuffleAndDeal(){
        
        deck.shuffle();

        int cardsPerHand = deck.getSize() / players.size();

        for(Player player : players){
            player.getHand().clear();
            player.getHand().addCards(deck.deal(cardsPerHand));
            // player.getHand().sortCards(); once added
        }
    }

    private void determinePassDirection(){
        int direction = roundNumber % 4;

        if (direction == 1) {
            pass = PassDirection.LEFT;
        } else if (direction == 2) {
            pass = PassDirection.RIGHT;
        } else if (direction == 3) {
            pass = PassDirection.ACROSS;
        } else {
            pass = PassDirection.HOLD;
        }
    }

    public void passCards(Map<Player, ArrayList<Card>> passCards){

        phase = RoundPhase.PASSING;

        if(pass == PassDirection.HOLD){
            return;
        }

        for(Player player : players){
            player.getHand().removeCards(passCards.get(player));
        }

        int receivingIndex;

        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            ArrayList<Card> cards = passCards.get(player);

            if(pass == PassDirection.LEFT){
                receivingIndex = (i+1) % players.size();
            } else if (pass == PassDirection.RIGHT){
                receivingIndex = (i - 1 + players.size()) % players.size();
            } else {
                receivingIndex = (i + players.size() / 2) % players.size();
            }

            players.get(receivingIndex).getHand().addCards(cards);
        }
    }

    public Player getLeader() {
        return leader;
    }

    private void findFirstLeader(){ //this is for finding the first leader
        for(Player player : players){
            for(Card card : player.getHand().getHand()){
                if(card.getRank() == Card.Rank.TWO && card.getSuit() == Card.Suit.CLUBS){
                    leader = player;
                    return;
                }
            }
        }
    }

    private void startTrick(){
        currentTrick = new Trick();
    }

    public void playCard(Player player, Card card){
        Play play = new Play(player, card);

        player.getHand().removeCard(card);
        currentTrick.addPlay(play);

        if (currentTrick.isComplete(players.size())) {
            finishTrick();
        }
    }

    private void finishTrick(){
        Player winner = currentTrick.getLeader();

        winner.addPoints(currentTrick.countTrickPoints());
        pastTricks.add(currentTrick);

        leader = winner;

        if(allCardsPlayed()){
            phase = RoundPhase.COMPLETE;
        } else {
            currentTrick = new Trick();
        }
    }

    private boolean allCardsPlayed(){

        for(Player player : players){
            if(!player.getHand().getHand().isEmpty()){
                return false;
            }
        }

        return true;
    }

    public Trick getCurrentTrick() {
        return currentTrick;
    }

    public boolean isHeartsBroken() {
        //TODO: Added with restrictions
        return heartsBroken;
    }

    public boolean isComplete() {
        return phase == RoundPhase.COMPLETE;
    }

    public void setHeartsBroken(boolean heartsBroken) {
        this.heartsBroken = heartsBroken;
    }

}
