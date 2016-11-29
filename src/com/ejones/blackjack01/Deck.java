package com.ejones.blackjack01;

//import java.util.Random;
//import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	private ArrayList<Card> cards;
	
	public Deck(){
		//Create a new deck of playing cards
		this.cards = new ArrayList<Card>();
	
	}
	
	//Add 52 cards to deck
	
	public void createFullDeck(){
		
		//Loop through suits and values, and add each card
		for(Suit cardSuit : Suit.values()){
			
			for(Value cardValue : Value.values()){
				
				this.cards.add(new Card(cardSuit,cardValue));
			}
		}
	}
	
	//Shuffle deck of cards
	public void shuffle(){
		ArrayList<Card> tempDeck = new ArrayList<Card>();
		for(Card aCard : this.cards){
			tempDeck.add(aCard);
		}
		Collections.shuffle(tempDeck);
	
		this.cards = tempDeck;
	}	
	
	//Remove a card from the deck
	public void removeCard(int i){
		this.cards.remove(i);
	}
	//Get card from deck
	public Card getCard(int i){
		return this.cards.get(i);
	}
	
	//Add card to deck
	public void addCard(Card addCard){
		this.cards.add(addCard);
	}
	
	//Draw a top card from deck
	public void draw(Deck comingFrom){
		//Add card to this deck from whatever deck its coming from
		this.cards.add(comingFrom.getCard(0));
		//Remove the card in the deck its coming from
		comingFrom.removeCard(0);
	}
	
	//Use to print out deck
	public String toString(){
		String cardListOutput = "";
		//int i = 0;
		for(Card aCard : this.cards){
			cardListOutput += "\n" + aCard.toString();
			//i++;
		}
		return cardListOutput;
	}
	
	public void moveAllToDeck(Deck moveTo){
		int thisDeckSize = this.cards.size();
		//put cards in moveTo deck
		for(int i = 0; i < thisDeckSize; i++){
			moveTo.addCard(this.getCard(i));
		}
		//empty out the deck
		for(int i = 0; i < thisDeckSize; i++){
			this.removeCard(0);
		}
	}
	
	public int deckSize(){
		return this.cards.size();
	}
	
	//Calculate the value of deck
	public int cardsValue(){
		int totalValue = 0;
		int aces = 0;
		//For every card in the deck
		for(Card aCard : this.cards){
			//Switch of possible values
			switch(aCard.getValue()){
			case DEUCE: totalValue += 2; break;
			case THREE: totalValue += 3; break;
			case FOUR: totalValue += 4; break;
			case FIVE: totalValue += 5; break;
			case SIX: totalValue += 6; break;
			case SEVEN: totalValue += 7; break;
			case EIGHT: totalValue += 8; break;
			case NINE: totalValue += 9; break;
			case TEN: totalValue += 10; break;
			case JACK: totalValue += 10; break;
			case QUEEN: totalValue += 10; break;
			case KING: totalValue += 10; break;
			case ACE: aces += 1; break;
			}			
		}
		
		//Determine the total current value with aces
		//Aces worth 11 or 1 - if 11 would go over 21 make it worth 1
		for(int i = 0; i < aces; i++){
			//If they're already at over 10 getting an ace valued at 11 would put them up to 22, so make ace worth one
			if (totalValue > 10){
				totalValue += 1;
			}
			else{
				totalValue += 11;
			}
		}
		
		//Return
		return totalValue;
	
	}
	
}
