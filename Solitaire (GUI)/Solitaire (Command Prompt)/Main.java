package GAME;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.net.URL;
import javax.imageio.*;


/**
* This "Main" class is the class that holds the static main method of the program. The program will be executed with this class. The program will play the game of Solitaire with 10 choices.
*/

public class Main{
	public static void main (String[] args) throws Exception{
		int entermode = 0;			
		Scanner scanner = new Scanner(System.in);
		String getter = "";

		if(args.length == 0) entermode = 1;
		else if(args[0].equals("-no-gui")) entermode = 2;
		else entermode = 3;

		while(true){
			if(entermode == 1){
	    		MyFrame m = null;
	  	 		SwingUtilities.invokeLater(new Runnable() {
	      			public void run() {
	        		MyFrame m = new MyFrame();
	      			}
	    		});
	    		break;
			}
			else if(entermode == 2){
				Solitaire game = new Solitaire();
				Object discard = null;
				LinkedStack moveStockCards = new LinkedStack();
				LinkedStack moveTableauCards = new  LinkedStack();
				int noStock = 0;
				int noTalon = 0;
				int pNum = 0;
				int numOfCards = 0;
				int pileToPut = 0;
				int choice = 0;
				int suitnum = 0;
				boolean cont = true;
				String fName = "";
				String[] fileSlot = new String[5];

				System.out.println("------------------------CONDITION / MOVE------------------------\n- Opens the program.");
				System.out.println("----------------------------SOLITAIRE---------------------------\n");
				do{
					System.out.println("----------------------------Your Move?--------------------------");
					System.out.println("1 - Deal/Redeal\n2 - Draw from talon\n3 - Move a card from talon to foundation\n4 - Move a card from tableau to foundation\n5 - Move a card/cards within the tableau\n6 - Move a card from talon to tableau\n7 - Move a card from foundation to tableau\n8 - Save game state\n9 - Load game state\n10 - Exit");
					//Input Validation
					do{	
						getter = scanner.nextLine();
						while(getter.length() == 0){
							System.out.println("Please input data.");
							getter = scanner.nextLine();
						}
						if(getter.length() == 1){
							if(Character.isDigit(getter.charAt(0)) && getter.charAt(0) != '0'){
								choice = (int) getter.charAt(0);
								choice -= 48; //ascii
								break;
							}
						}
						else if(getter.length() == 2){
							if(getter.charAt(0) == '1' && getter.charAt(1) == '0'){
								choice = 10;
								break;
							}
						}
						System.out.println("WRONG INPUT. Please input the appriopriate data.");
					}while(true);

					if(choice == 1){
						//DEAL/REDEAL
						for (int i = 0 ; i < 10 ; i++)
							System.out.println("\n");
						System.out.print("------------------------CONDITION / MOVE------------------------\n");
						if(noStock == 0 && noTalon == 0) System.out.println("- Deals the cards.");
						else System.out.println("- Redeals the cards.");
						game.deal();
						noStock = 24;
						noTalon = 0;
						moveStockCards.clear();
						System.out.println("Stock cards = " + noStock);
						System.out.println("Talon cards = " + noTalon);
					}
					else if (choice == 2){
						//DRAW FROM STOCK
						for (int i = 0 ; i < 10 ; i++)
							System.out.println("\n");
						System.out.print("------------------------CONDITION / MOVE------------------------\n");
						if(noStock == 0){
							noStock = noTalon;
							noTalon = 0;
							moveStockCards.clear();
						}
						for (int i = 0 ; i < 3 ; i++){
							if (noStock == 0) break;
							moveStockCards.push((Card) game.draw());
							noStock--;
							noTalon++;
						}
						System.out.println("- Draws cards from the stock pile.");
						System.out.println("Stock cards = " + noStock);
						System.out.println("Talon cards = " + noTalon);
					}
					else if (choice == 3){
						//MOVE A CARD FROM STOCK TO FOUNDATION
						for (int i = 0 ; i < 10 ; i++)
							System.out.println("\n");
						System.out.print("------------------------CONDITION / MOVE------------------------\n");
						if(game.moveToFoundation((Card) moveStockCards.peek()) && moveStockCards.peek() != null) {
							discard = moveStockCards.pop();
							noTalon--;
							System.out.println("- You moved " + ((Card) discard) + " from the stock pile to the foundation.");
						}
						else System.out.println("Invalid move.");
						System.out.println("Stock cards = " + noStock);
						System.out.println("Talon cards = " + noTalon);
						 
					}
					else if (choice == 4){
						//MOVE A CARD FROM TABLEAU TO FOUNDATION
						System.out.println("At what column does the card you want to move located : ");
						//Input Validation
						do{	
							getter = scanner.nextLine();
							while(getter.length() == 0){
								System.out.println("Please input data.");
								getter = scanner.nextLine();
							}
							if(getter.length() == 1){
								if(Character.isDigit(getter.charAt(0))){
									pNum = (int) getter.charAt(0);
									pNum -= 48; //ascii
									break;
								}
							}
							System.out.println("WRONG INPUT. Please input the appriopriate data.");
						}while(true);
						if(pNum >= 8 || pNum <= 0){
							System.out.println("Wrong input. There are only 7 columns.");;
						}
						else{
							moveTableauCards = game.getFromTableau(pNum,1);
							for (int i = 0 ; i < 10 ; i++)
								System.out.println("\n");
							System.out.print("------------------------CONDITION / MOVE------------------------\n");
							if(moveTableauCards != null ){
								if (game.moveToFoundation((Card) moveTableauCards.peek())) {
									discard = moveTableauCards.pop();
									System.out.println("- You moved " + ((Card)discard) + " from the tableau to foundation.");
								}
								else System.out.println("Invalid move");
							}
							else System.out.println("Invalid move");
						}
						System.out.println("Stock cards = " + noStock);
						System.out.println("Talon cards = " + noTalon);
						
					}
					else if (choice == 5){
						//MOVE A CARD/CARDS IN THE TABLEAU
						System.out.println("At what column does/do the card/s you want to move located : ");
						//Input Validation
						do{	
							getter = scanner.nextLine();
							while(getter.length() == 0){
								System.out.println("Please input data.");
								getter = scanner.nextLine();
							}
							if(getter.length() == 1){
								if(Character.isDigit(getter.charAt(0))){
									pNum = (int) getter.charAt(0);
									pNum -= 48; //ascii
									break;
								}
							}
							System.out.println("WRONG INPUT. Please input the appriopriate data.");
						}while(true);
						System.out.println("From the open bottom card, how many cards do you want to move: ");
						//Input Validation
						do{	
							getter = scanner.nextLine();
							while(getter.length() == 0){
								System.out.println("Please input data.");
								getter = scanner.nextLine();
							}
							if(getter.length() == 1){
								if(Character.isDigit(getter.charAt(0))){
									numOfCards = (int) getter.charAt(0);
									numOfCards -= 48; //ascii
									break;
								}
							}
							System.out.println("WRONG INPUT. Please input the appriopriate data.");
						}while(true);
						System.out.println("In what pile do you want to move the cards : ");
						//Input Validation
						do{	
							getter = scanner.nextLine();
							while(getter.length() == 0){
								System.out.println("Please input data.");
								getter = scanner.nextLine();
							}
							if(getter.length() == 1){
								if(Character.isDigit(getter.charAt(0))){
									pileToPut = (int) getter.charAt(0);
									pileToPut -= 48; //ascii
									break;
								}
							}
							System.out.println("WRONG INPUT. Please input the appriopriate data.");
						}while(true);

						for (int i = 0 ; i < 10 ; i++)
							System.out.println("\n");
						System.out.print("------------------------CONDITION / MOVE------------------------\n");
						if(pNum >= 8 || pNum <= 0 || numOfCards >= 14 || numOfCards < 0 || pileToPut >= 8 || pileToPut <= 0){
							System.out.println("Wrong input.");
						}
						else{
							moveTableauCards = game.getFromTableau(pNum,numOfCards);

							if(moveTableauCards != null && game.checkMoveToTableau((Card) moveTableauCards.peek(),pileToPut-1)){
								if(game.moveToTableau((Card) moveTableauCards.peek(),pileToPut)) System.out.println("- You moved " + ((moveTableauCards.getSize() == 1) ? "a card" : (moveTableauCards.getSize() +" cards")) + " within the tableau");
								else System.out.println("Invalid move.");
							}
							else System.out.println("- Invalid move.");
							if (moveTableauCards != null) moveTableauCards.clear();
						}
						System.out.println("Stock cards = " + noStock);
						System.out.println("Talon cards = " + noTalon);
					}
					else if (choice == 6){
						//MOVE A CARD FROM TALON TO TABLEAU
						System.out.println("In what pile do you want to move the card : ");
						//Input Validation
						do{	
							getter = scanner.nextLine();
							while(getter.length() == 0){
								System.out.println("Please input data.");
								getter = scanner.nextLine();
							}
							if(getter.length() == 1){
								if(Character.isDigit(getter.charAt(0))){
									pileToPut = (int) getter.charAt(0);
									pileToPut -= 48; //ascii
									break;
								}
							}
							System.out.println("WRONG INPUT. Please input the appriopriate data.");
						}while(true);
						for (int i = 0 ; i < 10 ; i++)
							System.out.println("\n");
						System.out.print("------------------------CONDITION / MOVE------------------------\n");
						if(game.moveToTableau((Card) moveStockCards.peek(),pileToPut)) {
							discard = moveStockCards.pop();
							noTalon--;
							System.out.println("- You moved " + ((Card)discard) + " from the talon pile to the tableau.");
						}
						else System.out.println("Invalid move.");
						System.out.println("Stock cards = " + noStock);
						System.out.println("Talon cards = " + noTalon);
					}
					else if (choice == 7){
						//MOVE A CARD FROM FOUNDATION TO TABLEAU
						System.out.println("What suit?\n1 - Diamonds\n2 - Hearts\n3 - Spades\n4 - Clubs");
						//Input Validation
						do{	
							getter = scanner.nextLine();
							while(getter.length() == 0){
								System.out.println("Please input data.");
								getter = scanner.nextLine();
							}
							if(getter.length() == 1){
								if(Character.isDigit(getter.charAt(0))){
									suitnum = (int) getter.charAt(0);
									suitnum -= 48; //ascii
									break;
								}
							}
							System.out.println("WRONG INPUT. Please input the appriopriate data.");
						}while(true);
						System.out.println("In what pile do you want to move the card : ");
						//Input Validation
						do{	
							getter = scanner.nextLine();
							while(getter.length() == 0){
								System.out.println("Please input data.");
								getter = scanner.nextLine();
							}
							if(getter.length() == 1){
								if(Character.isDigit(getter.charAt(0))){
									pileToPut = (int) getter.charAt(0);
									pileToPut -= 48; //ascii
									break;
								}
							}
							System.out.println("WRONG INPUT. Please input the appriopriate data.");
						}while(true);
						for (int i = 0 ; i < 10 ; i++)
							System.out.println("\n");
						System.out.print("------------------------CONDITION / MOVE------------------------\n");
						switch (suitnum) {
							case 1 : if (!(game.moveToTableau(game.getFromFoundation(Suit.DIAMONDS),pileToPut))) System.out.println("Sorry, invalid move.");
									 else System.out.println("- You moved a card from foundation to tableau.");
									 break;
							case 2 : if (!(game.moveToTableau(game.getFromFoundation(Suit.HEARTS),pileToPut))) System.out.println("Sorry, invalid move.");
									 else System.out.println("- You moved a card from foundation to tableau.");
									 break;
							case 3 : if (!(game.moveToTableau(game.getFromFoundation(Suit.SPADES),pileToPut))) System.out.println("Sorry, invalid move.");
									 else System.out.println("- You moved a card from foundation to tableau.");
									 break;
							case 4 : if (!(game.moveToTableau(game.getFromFoundation(Suit.CLUBS),pileToPut))) System.out.println("Sorry, invalid move.");
									 else System.out.println("- You moved a card from foundation to tableau.");
									 break;
							default : System.out.println("-Sorry, wrong input.");
						}
						System.out.println("Stock cards = " + noStock);
						System.out.println("Talon cards = " + noTalon);
						
					}
					else if (choice == 8){
						//SAVE GAME STATE
				        System.out.println("In what filename will the game state be saved?");
				        fName = scanner.nextLine();
						for (int i = 0 ; i < 10 ; i++)
							System.out.println("\n");
						System.out.print("------------------------CONDITION / MOVE------------------------\n");

				        if(game.saveGame(fName,moveStockCards,noStock,noTalon)) System.out.println("Saved successfully.");
				        else System.out.println("Sorry, the filename is not found in the save slots.");
						System.out.println("Stock cards = " + noStock);
						System.out.println("Talon cards = " + noTalon);
					}
					else if (choice == 9){
						//LOAD GAME  STATE
				        System.out.println("What filename of the game state that will be loaded? ");
				        fName = scanner.nextLine();
						for (int  i = 0 ; i < 10 ; i++)
							System.out.println("\n");
						System.out.print("------------------------CONDITION / MOVE------------------------\n");

				        if(game.loadGame(fName)){
				        	//CLEARING
				        	moveStockCards.clear();

				        	BufferedReader update = new BufferedReader(new FileReader(fName + ".txt"));
				        	String suitGetter = "";

				        	while(!(getter.equals("=="))){
				        		getter = update.readLine();
				        	}
				        	getter = update.readLine();
				        	while(!(getter.equals("--"))){
				        		suitGetter = update.readLine();
				        		Card tmp = new Card();
				        		switch(suitGetter.charAt(0)){
					        		case 'D' : tmp.setValues(Integer.parseInt(getter),Suit.DIAMONDS,true);
					        				 break;
					        		case 'H' : tmp.setValues(Integer.parseInt(getter),Suit.HEARTS,true);
					        				 break; 
					        		case 'S' : tmp.setValues(Integer.parseInt(getter),Suit.SPADES,true);
					        				 break; 
					        		case 'C' : tmp.setValues(Integer.parseInt(getter),Suit.CLUBS,true);
					        				 break;  
				        		}
				        		moveStockCards.push((Object) tmp);
				        		getter = update.readLine();
				        	}

				        	noStock = Integer.parseInt(update.readLine());
				        	noTalon = Integer.parseInt(update.readLine());
				        	System.out.println("Loaded Successfully.");
				        	update.close();
				        }
				        else System.out.println("Filename not found.");
						System.out.println("Stock cards = " + noStock);
						System.out.println("Talon cards = " + noTalon);
					}
					else if (choice == 10) cont = false;
					else {
						for (int i = 0 ; i < 10 ; i++)
							System.out.println("\n");
						System.out.println("Wrong input. Try again.");
					}
					if( choice != 10){
						System.out.println("----------------------------SOLITAIRE---------------------------\n");
						game.printGameState();
						if(game.checkIfWon()) {
							System.out.println("			YOU WON!!!!!!\n			CONGRATULATIONS!!!");
							for (int i = 0 ; i < 5 ; i++)
								System.out.println("\n");
							System.out.println("			Play another game?\n		Input 1 (for Yes) / 0 (for No)");
							System.out.println("			");
							//Input Validation
							do{	
								getter = scanner.nextLine();
								while(getter.length() == 0){
									System.out.println("Please input data.");
									getter = scanner.nextLine();
								}
								if(getter.length() == 1){
									if(getter.charAt(0) == '1'){
										choice = 1;
										break;
									}
									if(getter.charAt(0) == '0'){
										choice = 0;
									}
								}
								System.out.println("WRONG INPUT. Please input the appriopriate data.");
							}while(true);
							if(choice == 0) cont = false;
						}
					}
				}while(cont); 
				System.out.println("---------------------Thank you for playing----------------------");
				break;
			}
			else{
				System.out.println("Wrong input. Do you wish to play in terminal? 1 - N/ 2 - Y");
				String answer = "";
				//Input Validation
				do{	
					getter = scanner.nextLine();
					while(getter.length() == 0){
						System.out.println("Please input data.");
						getter = scanner.nextLine();
					}
					if(getter.length() == 1){
						if(getter.charAt(0) == '1'){
							entermode = 1;
							break;
						}
						if(getter.charAt(0) == '2'){
							entermode = 2;
							break;
						}
					}
					System.out.println("WRONG INPUT. Please input the appriopriate data.");
				}while(true); 
			}
		}
	}
}