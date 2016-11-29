package com.ejones.blackjack01;

import java.util.Scanner;

public class Blackjack {
	
	public static double takeBetAgain(double playerMoney, double playerBet, Scanner userInput)
	{
		//Recursive function to take bet until player makes a valid bet
		System.out.println("Listen buddy, you cannot bet more than you have.");
		System.out.println("You have $" + playerMoney + ", how much would you like to bet?");
		playerBet = userInput.nextDouble();
		if(playerBet > playerMoney){
			//Keep asking until a valid bet is made
			takeBetAgain(playerMoney, playerBet, userInput);
		}
		return playerBet;
	}

public static void main(String[] args){
		
		System.out.println("Welcome to Blackjack!");
		
		//playingDeck will be the deck the dealer holds
		Deck playingDeck = new Deck();
		playingDeck.createFullDeck();
		playingDeck.shuffle();
		
		//playerCards will be the cards the player has in their hand
		Deck playerCards = new Deck();
		//playerMoney holds players cash - we will be lazy and use doubles instead of bigdecimals
		double playerMoney = 100.0;
		//dealerCards will be the cards the dealer has in their hand
		Deck dealerCards = new Deck();
		
		//Scanner for user input
		Scanner userInput = new Scanner(System.in);
		
//Play the game while the player has money
while(playerMoney > 0){
	//Take bet
	System.out.println("You have $" + playerMoney + ", how much would you like to bet?");
	double playerBet = userInput.nextDouble();
	boolean endRound = false;
	boolean didDouble = false;
	//Cannot bet what you do not have, so take bet again
	if(playerBet > playerMoney){
		playerBet = takeBetAgain(playerMoney, playerBet, userInput);
	}
	
	
	System.out.println("Dealing...");
	//Player gets two cards
	playerCards.draw(playingDeck);
	playerCards.draw(playingDeck);
	
	//Dealer gets two cards
	dealerCards.draw(playingDeck);
	dealerCards.draw(playingDeck);
			
			//While loop for drawing new cards
			while(true){
				//Display player cards
				System.out.println("Your Hand:" + playerCards.toString());
				
				//Display value of hand
				System.out.println("Your Hand Value: " + playerCards.cardsValue());
				
				//Display dealer cards
				System.out.println("Dealer Hand: " + dealerCards.getCard(0).toString() + " and [hidden]");
				
				//What do they want to do
				System.out.println("Would you like to (1)Hit, (2)Stand, (3)Double Down, or (4)Surrender?");
				int response = userInput.nextInt();
	
				//Hit
				if(response == 1){
					playerCards.draw(playingDeck);
					System.out.println("You draw a:" + playerCards.getCard(playerCards.deckSize()-1).toString());
					//Bust if they go over 21
					if(playerCards.cardsValue() > 21){
						System.out.println("Bust. Currently valued at: " + playerCards.cardsValue());
						playerMoney -= playerBet;
						endRound = true;
						break;
					}
				}
				
				//Stand
				if(response == 2){
					break;
				}
				//Double Down
				if(response == 3){
					//Must ensure that only one card can be drawn for doubling down 
					didDouble = true;
					playerCards.draw(playingDeck);
					System.out.println("You draw a:" + playerCards.getCard(playerCards.deckSize()-1).toString());
					//Bust if they go over 21
					if(playerCards.cardsValue() > 21){
						System.out.println("Bust. Currently valued at: " + playerCards.cardsValue());
						playerMoney -= (playerBet * 2);
						endRound = true;
						break;
					}
					System.out.println("Your Hand Value: " + playerCards.cardsValue());
					break;
				}
				
				//Surrender
				if(response == 4){
					System.out.println("Surrender.");
					playerMoney -= (playerBet / 2);
					endRound = true;
					break;
				}
				
			}
				
			//Reveal dealer cards
			System.out.println("Dealer Cards:" + dealerCards.toString());
			
			//Dealer hits at 16 stands at 17
			while((dealerCards.cardsValue() < 17) && endRound == false){
				dealerCards.draw(playingDeck);
				System.out.println("Dealer draws: " + dealerCards.getCard(dealerCards.deckSize()-1).toString());
			}
			//Display value of dealer
			System.out.println("Dealers hand value: " + dealerCards.cardsValue());
			//Determine if dealer busted
			if((dealerCards.cardsValue()>21)&& endRound == false){
				System.out.println("Dealer Busts. You win!");
				if(didDouble == true){
					playerMoney += (playerBet * 2);
				} else { playerMoney += playerBet; }
				endRound = true;
			}
			//See if dealer has more points than player
			if((dealerCards.cardsValue() > playerCards.cardsValue())&& endRound == false){
				System.out.println("Dealer beats you " + dealerCards.cardsValue() + " to " + playerCards.cardsValue());
				if(didDouble == true){
					playerMoney -= (playerBet * 2);
				} else { playerMoney -= playerBet; }
				endRound = true;
			}
			
			//Determine if push
			if((dealerCards.cardsValue() == playerCards.cardsValue()) && endRound == false){
				System.out.println("Push.");
				endRound = true;
			}
			//Determine if player wins
			if((playerCards.cardsValue() > dealerCards.cardsValue()) && endRound == false){
				System.out.println("You win the hand.");
				if(didDouble == true){
					playerMoney += (playerBet * 2);
				} else { playerMoney += playerBet; }
				endRound = true;
			}
			else if(endRound == false) //Dealer wins
			{
				System.out.println("Dealer wins.");
				if(didDouble == true){
					playerMoney -= (playerBet * 2);
				} else { playerMoney -= playerBet; }
				endRound = true;
			}

			//End of hand - put cards back in deck
			playerCards.moveAllToDeck(playingDeck);
			dealerCards.moveAllToDeck(playingDeck);
			System.out.println("End of Hand.");
			
		}
		//Game over
		System.out.println("Game over! Better luck next time, pal. :(");
		
		//Close scanner
		userInput.close();
		
	}
	

}
