package com.example.game;
import com.example.card.Card;
import com.example.card.Rank;
import com.example.card.Suit;

import java.util.List;



public class MoveValidator {
//Checks if a card is legal to play according to the standard rules of Hearts.
     
/**
 * 
 * @param cardToPlay card that the player wants to play
 * @param playerHand the cards in the player's hand
 * @param currentTrick the cards that have been played in the current trick
 * @param isFirstTrickOfRound true if this is the first trick of the round, false otherwise
 * @param heartsBroken true if hearts have been broken, false otherwise
 * @return true if the card is a legal play, false otherwise
 */    

public static boolean isValidMove(
            Card cardToPlay,
            List<Card> playerHand,
            Trick currentTrick,
            boolean isFirstTrickOfRound,
            boolean heartsBroken) {

        boolean isLeadingTrick = currentTrick.isEmpty(); // If the current trick is empty, the player is leading the trick.

        // HEARTS RULE 1: The very first card of the round MUST be the 2 of Clubs.
        if (isFirstTrickOfRound && isLeadingTrick) {
            return cardToPlay.getSuit() == Suit.CLUBS && cardToPlay.getRank() == Rank.TWO;
        }

        // HEARTS RULE 2: Leading a standard trick
        if (isLeadingTrick) {
            // You cannot lead a Heart unless hearts are broken...
            if (cardToPlay.getSuit() == Suit.HEARTS && !heartsBroken) {
                // UNLESS you literally only have Hearts left in your hand.
                return hasOnlyHearts(playerHand);
            }
            return true; // Can legally lead any other suit.
        }

        // HEARTS RULE 3: Following another player's lead
        Suit leadSuit = currentTrick.getLeadSuit();

        if (cardToPlay.getSuit() == leadSuit) {
            return true; // You are following suit. Always legal.
        } else {
            //  HEARTS RULE 4: Breaking suit. You can only play a different suit if you do not have any cards of the lead suit.
            if (hasSuitInHand(playerHand, leadSuit)) {
                return false; // Illegal! You have the lead suit but are trying to play something else.
            }

            // HEARTS RULE 5: First Trick point restriction.
            // You cannot play Hearts or the Queen of Spades on the first trick.
            if (isFirstTrickOfRound) {
                boolean isPenaltyCard = cardToPlay.getSuit() == Suit.HEARTS ||
                        (cardToPlay.getSuit() == Suit.SPADES && cardToPlay.getRank() == Rank.QUEEN);

                if (isPenaltyCard) {
                    // Extreme edge case: Player has 13 penalty cards and is forced to play one of them on the first trick.
                    return hasOnlyPenaltyCards(playerHand);
                }
            }

            return true; // Legal off-suit play.
        }
    }

    // --- Helper Methods --- 

    private static boolean hasSuitInHand(List<Card> hand, Suit suitToCheck) {
        for (Card c : hand) {
            if (c.getSuit() == suitToCheck) return true;
        }
        return false;
    }

    private static boolean hasOnlyHearts(List<Card> hand) {
        for (Card c : hand) {
            if (c.getSuit() != Suit.HEARTS) return false;
        }
        return true;
    }

    private static boolean hasOnlyPenaltyCards(List<Card> hand) {
        for (Card c : hand) {
            if (c.getSuit() != Suit.HEARTS && !(c.getSuit() == Suit.SPADES && c.getRank() == Rank.QUEEN)) {
                return false;
            }
        }
        return true;
    }
}
