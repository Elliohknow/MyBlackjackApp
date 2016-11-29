package com.ejones.blackjack01;

public class Card {

	private Suit suit;
	private Value value;
	
	public Card(Suit suit, Value value){
		this.suit = suit;
		this.value = value;
	}
	
	public String toString(){
		return this.value.toString() + " of " + this.suit.toString();
	}
	
	public Value getValue(){
		return this.value;
	}

}
