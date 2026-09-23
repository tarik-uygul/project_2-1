package com.example.card;

public class Rank {
    public static final Rank TWO = new Rank("Two");
    public static final Rank THREE = new Rank("Three");
    public static final Rank FOUR = new Rank("Four");
    public static final Rank FIVE = new Rank("Five");
    public static final Rank SIX = new Rank("Six");
    public static final Rank SEVEN = new Rank("Seven");
    public static final Rank EIGHT = new Rank("Eight");
    public static final Rank NINE = new Rank("Nine");
    public static final Rank TEN = new Rank("Ten");
    public static final Rank JACK = new Rank("Jack");
    public static final Rank QUEEN = new Rank("Queen");
    public static final Rank KING = new Rank("King");
    public static final Rank ACE = new Rank("Ace");

    private final String name;

    private Rank(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    

}
