package com.example.game;

import com.example.cards.Card;
import java.util.List;

public class MoveValidator {

    /**
     * Checks if a card is legal to play according to the standard rules of Hearts.
     */
    public static boolean isValidMove(
            Card cardToPlay, 
            List<Card> playerHand, 
            Trick currentTrick, 
            boolean isFirstTrickOfRound, 
            boolean heartsBroken) {

        boolean isLeadingTrick = currentTrick.isEmpty();

        // RULE 1: The very first card of the round MUST be the 2 of Clubs.
        if (isFirstTrickOfRound && isLeadingTrick) {
            return cardToPlay.getSuit() == Card.Suit.CLUBS && cardToPlay.getRank() == Card.Rank.TWO;
        }

        // RULE 2: Leading a standard trick
        if (isLeadingTrick) {
            // You cannot lead a Heart unless hearts are broken...
            if (cardToPlay.getSuit() == Card.Suit.HEARTS && !heartsBroken) {
                // ...UNLESS you literally only have Hearts left in your hand.
                return hasOnlyHearts(playerHand);
            }
            return true; // Can legally lead any other suit.
        }

        // RULE 3: Following another player's lead
        // Note: Make sure the Trick class actually uses "getLeadSuit()"
        Card.Suit leadSuit = currentTrick.getTrickSuit();
        
        if (cardToPlay.getSuit() == leadSuit) {
            return true; // You are following suit. Always legal.
        } else {
            // RULE 4: Breaking suit. You can only play a different suit if you are void in the lead suit.
            if (hasSuitInHand(playerHand, leadSuit)) {
                return false; // Illegal! You have the lead suit but are trying to play something else.
            }
            
            // RULE 5: First Trick point restriction. 
            // You cannot play Hearts or the Queen of Spades on the first trick.
            if (isFirstTrickOfRound) {
                boolean isPenaltyCard = cardToPlay.getSuit() == Card.Suit.HEARTS || 
                                       (cardToPlay.getSuit() == Card.Suit.SPADES && cardToPlay.getRank() == Card.Rank.QUEEN);
                
                if (isPenaltyCard) {
                    // Extreme edge case: Player has 13 penalty cards (almost impossible but must be handled)
                    return hasOnlyPenaltyCards(playerHand);
                }
            }
            
            return true; // Legal off-suit play.
        }
    }

    // --- Helper Methods ---

    private static boolean hasSuitInHand(List<Card> hand, Card.Suit suitToCheck) {
        for (Card c : hand) {
            if (c.getSuit() == suitToCheck) return true;
        }
        return false;
    }

    private static boolean hasOnlyHearts(List<Card> hand) {
        for (Card c : hand) {
            if (c.getSuit() != Card.Suit.HEARTS) return false;
        }
        return true;
    }

    private static boolean hasOnlyPenaltyCards(List<Card> hand) {
        for (Card c : hand) {
            if (c.getSuit() != Card.Suit.HEARTS && !(c.getSuit() == Card.Suit.SPADES && c.getRank() == Card.Rank.QUEEN)) {
                return false;
            }
        }
        return true;
    }
}