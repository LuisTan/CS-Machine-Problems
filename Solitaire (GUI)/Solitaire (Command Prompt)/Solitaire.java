package GAME;

import java.util.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.image.*;
import javax.imageio.*;
import java.net.*;
/**
* This "Solitaire" class holds the deck of cards and the actions needed in the game SOolitaire. Public access.
*/

public class Solitaire{

	/**
	* "deck" attribute is an array of Card class which is the container of the 52 Cards. Private access.
	*/

	private Card[] deck = new Card[52];

	/**
	* "talon" attribute of LinkedStack class is the linked list that holds the cards of the talon in the game of Solitaire. Private ACcess.
	*/

	private LinkedStack talon = new LinkedStack();

	/**
	* "foundations" attribute is an array of LinkedStack class which represents the 4 foundations in the game of Solitaire. Private access.
	*/

	private LinkedStack[] foundations = new LinkedStack[4];

	/**
	* "stock" attribute of LinkedStack class is the linked list that holds the cards of the stock in the game of Solitaire. Private access.
	*/

	private LinkedStack stock = new LinkedStack();

	/**
	* "tableau" attribute is an array of LinkedStack class which represents the 7 columns of the tableau in the game of Solitaire. Private access.
	*/

	private LinkedStack[] tableau = new LinkedStack[7];

	/**
	* "back" attribute of BufferedImage type holds the image of the back of a card which will be used in GUI mode.
	*/

	private BufferedImage back;
	/**
	*	this constructor creates the 52 unique cards of the deck.
	*/

	public Solitaire(){
		int i = 0 ;
		int j = 0 ;
		try{
			back = ImageIO.read(this.getClass().getResource("/cmagedeck/155.png"));
		}catch(Exception e){
			back = null;
		}
		for (i = 0 ; i < 4 ; i++){
			for (j = 0 ; j < 13 ; j++){
				switch (i){
					case 0 : deck[(i*12)+i+j] = new Card(Suit.DIAMONDS,(j+1));
							 break;
					case 1 : deck[(i*12)+i+j] = new Card(Suit.HEARTS,(j+1));
							 break;
					case 2 : deck[(i*12)+i+j] = new Card(Suit.SPADES,(j+1));
							 break;
					case 3 : deck[(i*12)+i+j] = new Card(Suit.CLUBS,(j+1));
							 break;
				}
			}
		}

		for ( i = 0 ; i < 4 ; i++)
			foundations[i] = new LinkedStack();
		for ( i = 0 ; i < 7 ; i++)
			tableau[i] = new LinkedStack();
	}

	/**
	* "deal" method deals the shuffled deck to the talon, 4 foundations, stock and 7 columns of tableau.
	* It also redeals the shuffled deck if ever called. Public access. 
	*/

	public void deal(){
		int cardNum = 0;
		int i = 0;
		int j = 0;

		shuffle();

		/*Foundations*/
		for ( i = 0 ; i < 4 ; i++)
			foundations[i].clear();
		/*Tableau*/
		Object card = new Object();
		for (i = 0 ; i < 7; i++){
			tableau[i].clear();
			for ( j = 0 ; j <= i ; j++){
				if (j == i) deck[cardNum].setFaceUp(true);
				else deck[cardNum].setFaceUp(false);
				card = (Object) deck[cardNum];
				tableau[i].push(card);
				cardNum++;
			}
		}
		/*Stock*/
		stock.clear();
		for (i = cardNum ; i < 52; i++,cardNum++){
			deck[cardNum].setFaceUp(false);
			stock.push((Object) deck[cardNum]);
		}
		/*Talon*/
		talon.clear();
		return;
	}

	/**
	* "shuffle" method randomizes the positions of the cards oof the deck of 52 cards. Public access.
	*/

	public void shuffle(){ 
		Random rand = new Random();
		int i = 0;
		int cardNum = 0;
		Card temp = null;
		for(i = 0 ; i < 52 ; i++){
			cardNum = rand.nextInt(52);
			temp = deck[i];
			deck[i] = deck[cardNum];
			deck[cardNum] = temp;
		}

		for (i = 0 ; i < 52 ; i++){
			cardNum = (rand.nextInt(1000)%51) + 1;
			temp = deck[i];
			deck[i] = deck[cardNum];
			deck[cardNum] = temp;
		}

		return;
	}

	/**
	* "printGameState" method prints the current game state. Public access.
	*/

	public void printGameState(){
		int i = 0;
		int count = 0;
		int cNull = 0;
		int index = 0;
		System.out.print("STOCK   " + stock + "  "); 
		System.out.print( "     FOUNDATION  " + foundations[0] + "   " + foundations[1] + "   " + foundations[2] + "   " + foundations[3]+ "\n\nTALON   ");
		for ( i = 3 ; i > 0 ; i--){
			if(talon.iTableau(talon.getSize()-i) != null) System.out.print(talon.iTableau(talon.getSize()-i));
			else System.out.print("[   ]");
		}
		System.out.println(" (TOP)\n\n\nTABLEAU |-1-|   |-2-|   |-3-|   |-4-|   |-5-|   |-6-|   |-7-| \n");
		while (count < 7){
			System.out.print("        ");
			for ( i = 0 ; i < 7 ; i++){
				if (index == 0 && tableau[i].isEmpty()) {
					System.out.print(tableau[i] + "   ");
					cNull++;				
				}
				else if (tableau[i].iTableau(index) == null) {
					System.out.print("        ");
					cNull++;
				}
				else System.out.print(tableau[i].iTableau(index) + "   ");
			}	
			count = cNull;
			cNull = 0;	
			System.out.print("\n");
			index++;
		}
		System.out.println("\n");
		return;
	}

	/**
	* "draw" method gets the top card from the stock. Public access.
	*@return this method returns the Card object from the stock.
	*/

	public Card draw(){
		int i = 0 ;
		Card temp = null;
		if(stock.isEmpty() && talon.isEmpty()) return null;
		/*From talon, all cards back to stock*/
		if (stock.isEmpty()) {
			while(talon.peek() != null){
				temp = (Card) talon.pop();
				temp.setFaceUp(false);
				stock.push((Object) temp);
			}
		}
		/*One card, stock to talon*/
		temp = (Card) stock.pop();
		temp.setFaceUp(true);
		talon.push((Object) temp);

		return (Card) talon.peek();
	}

	/**
	* "movoToFoundation" method moves the card (parameter). Public access.
	*@param card (parameter) of Card class is the card that will be moved to the foundation (if the certain requirements were met)
	*@return this method return true if the card was successfully moved to the foundaation and false if not.
	*/

	public boolean moveToFoundation(Card card){
		int i = 0;
		int j = 0;
		Card temp = null;
		boolean checkTalon = false;
		boolean checkTableau = false;
		boolean former = false;
		if(card == null) return false;
		if (card == (Card) talon.peek()){
			card = (Card) talon.pop();
			checkTalon = true;
		}
		else{
			for ( i = 0 ; i < 7 ; i++){
				if(card == (Card) tableau[i].peek()){
					card = (Card) tableau[i].pop();
					temp = (Card) tableau[i].pop();
					if (temp != null){
						former = temp.isUp();
						temp.setFaceUp(true);
						tableau[i].push((Object) temp);
					}
					checkTableau = true;
					break;
				}
				if(checkTableau) break;
			}
		}

		if (checkTableau || checkTalon){ 
			if (card.getSuit() == Suit.DIAMONDS){
				temp = (Card) foundations[0].peek();
				if(temp == null){
					if(card.getRank() == 1){
						foundations[0].push((Object) card);
						return true;
					}
				}
				else if(temp.getRank() + 1 == card.getRank()){
					foundations[0].push((Object) card);
					return true;
				}
			}
			else if (card.getSuit() == Suit.HEARTS){
				temp = (Card) foundations[1].peek();
				if(temp == null){
					if(card.getRank() == 1){
						foundations[1].push((Object) card);
						return true;
					}
				}
				else if(temp.getRank() + 1 == card.getRank()){
					foundations[1].push((Object) card);
					return true;
				}
			}
			else if (card.getSuit() == Suit.SPADES){
				temp = (Card) foundations[2].peek();
				if(temp == null){
					if(card.getRank() == 1){
						foundations[2].push((Object) card);
						return true;
					}
				}
				else if(temp.getRank() + 1 == card.getRank()){
					foundations[2].push((Object) card);
					return true;
				}
			}
			else if (card.getSuit() == Suit.CLUBS){
				temp = (Card) foundations[3].peek();
				if(temp == null){
					if(card.getRank() == 1){
						foundations[3].push((Object) card);
						return true;
					}
				}
				else if(temp.getRank() + 1 == card.getRank()){
					foundations[3].push((Object) card);
					return true;
				}
			}
			if(checkTableau){
				if(tableau[i].peek() != null){
					temp = (Card) tableau[i].pop();
					temp.setFaceUp(former);
					tableau[i].push((Object) temp);
				}
				tableau[i].push((Object) card);
			}
			else talon.push((Object) card);
		}
		return false;
	}

	/**
	* "checkMoveToTableau" method checks if the card (parameter) is of the opposite color of suit and is 1 less than the top card of pile pPut (parameter) or not null.
	*@param card (parameter) of class Card is the card to be put.
	*@param pPut (parameter) of integer type is the pile number in which the card (parameter) is going to be moved. 
	*@return this method returns true if the card (parameter) is of the opposite color of the suit and is 1 less than the top card of the pile pPut (parameter) and false if not.
	*/

	public boolean checkMoveToTableau(Card card, int pPut){
		Card temp = (Card) tableau[pPut].peek();
		if(card == null ||(pPut > 7)) return false;
		if(temp == null){
			if(card.getRank() != 13) return false;
			else return true;
		}
		if (card.getSuit() == Suit.DIAMONDS || card.getSuit() == Suit.HEARTS){
			if((temp.getSuit() == Suit.SPADES || temp.getSuit() == Suit.CLUBS) && temp.getRank() == card.getRank()+1) {
				return true;
			}
		}
		else{ 
			if((temp.getSuit() == Suit.DIAMONDS || temp.getSuit() == Suit.HEARTS) && temp.getRank() == card.getRank()+1) {
				return true; 
			}
		}
		return false;
	}

	/**
	* "moveToTableau" method moves the card to the column pile (parameter).
	* The card's position in the game will be searched and once found, the card will be popped from its position
	* and will be checked if is is valid to move the card/top card (with the other cards) to the column pile (parameter). Public access.
	*@param card (parameter) of type Card is the card/top card to be moved.
	*@param pile (parameter) of type integer is the column number in which the card/top card to be put.
	*@return this method returns true if the card/top card (with the other cards) was moved successfully and false if not.
	*/

	public boolean moveToTableau(Card card, int pile){
		int i = 0;
		int j = 0;
		int index = 1;
		int pNum = pile-1;
		Card temp = null;
		boolean checkTalon = false;
		boolean checkTableau = false;
		boolean checkFoundations = false;
		boolean former = false;
		LinkedStack toMove = new LinkedStack();
		if(card == null) return false; 
		if (card.toString().equals(talon.peek() != null ? ((Card) talon.peek()).toString(): "null")){
			toMove.push(talon.pop());
			checkTalon = true;
		}
		else if (card == (Card) foundations[0].peek()){
			toMove.push(foundations[0].pop());
			checkFoundations = true;
		}
		else if (card == (Card) foundations[1].peek()){
			toMove.push(foundations[1].pop());
			checkFoundations = true;
		}
		else if (card == (Card) foundations[2].peek()){
			toMove.push(foundations[2].pop());
			checkFoundations = true;
		}
		else if (card == (Card) foundations[3].peek()){
			toMove.push(foundations[3].pop());
			checkFoundations = true;
		}
		else{
			for ( i = 0 ; i < 7 ; i++){
				index = 0;
				while ( tableau[i].iTableau(tableau[i].getSize()-1-index) != null){
					temp = tableau[i].iTableau(tableau[i].getSize()-1-index);
					if(!(temp.isUp())) break; 
					if(card == temp) {
						checkTableau = true;
						break;
					}
					index++;
				}
				if(checkTableau) {
					break;
				}
			}

			if(checkTableau){
				for ( j = 0 ; j <= index ; j++)
					toMove.push(tableau[i].pop());
				temp = (Card) tableau[i].peek();
			}
		}

		if (checkTableau || checkTalon || checkFoundations){
			if (tableau[pNum].peek() == null){
				if (card.getRank() != 13) return false;
				else {
					tableau[pNum].push( toMove.pop());
					while(toMove.peek() != null){
						tableau[pNum].push(toMove.pop());
					}
					return true;
				}
			}
			temp = (Card) tableau[pNum].peek();
			if (card.getSuit() == Suit.DIAMONDS || card.getSuit() == Suit.HEARTS){
				if((temp.getSuit() == Suit.SPADES || temp.getSuit() == Suit.CLUBS) && temp.getRank() == card.getRank()+1){
					tableau[pNum].push(toMove.pop());
					while(toMove.peek() != null){
						tableau[pNum].push(toMove.pop());
					}
					return true;
				}
			}
			else{
				if((temp.getSuit() == Suit.DIAMONDS || temp.getSuit() == Suit.HEARTS) && temp.getRank() == card.getRank()+1){
					tableau[pNum].push(toMove.pop());
					while(toMove.peek() != null){
						tableau[pNum].push(toMove.pop());
					}
					return true;
				}
			}
			if(checkTalon) talon.push(toMove.pop());
			else{
				if(card.getSuit() == Suit.DIAMONDS) foundations[0].push(toMove.pop());
				else if (card.getSuit() == Suit.HEARTS) foundations[1].push(toMove.pop());
				else if (card.getSuit() == Suit.SPADES) foundations[2].push(toMove.pop());
				else foundations[3].push(toMove.pop());
			}
		}
		return false;
	}

	/**
	* "getFromFoundation" method gets the top card of the foundation of Suit (parameter). Public access
	*@param suit (parameter) of enum Suit indicates suit of the top card of a foundation. 
	*@return this method returns the top card of Card class of the foundation os suit suit(parameter), null if the foundation is empty.
	*/

	public Card getFromFoundation(Suit suit){
		Card temp = null;
		for (int i = 0 ; i < 4; i++){
			temp = (Card) foundations[i].peek();
			if(temp == null) continue;
			if(temp.getSuit() == suit) return temp;
		}
		return null;
	}

	/**
	* "getFromTableau" method gets a number of cards in a pile. Public access.
	*@param pile (parameter) of type Integer is the pile number where the card/s that is/are going to be moved located.
	*@param numberOfCards (paramter) of type integer is the number of how many cards the program will get. 
	*@return this method returns the linked list of cards of type LinkedStack that was got from the tableau, null if the number of cards is bigger than the available open cards. 
	*/

	public LinkedStack getFromTableau(int pile, int numberOfCards){
		int pNum = pile-1;
		LinkedStack toReturn = new LinkedStack();
		Card temp = null;
		for (int i = 0 ; i < numberOfCards ; i++){
			temp = (Card) tableau[pNum].iTableau(tableau[pNum].getSize()-1-i);
			if(temp != null && temp.isUp())toReturn.push(temp);
			else{
				System.out.println("Sorry, Invalid Move.");
				return null;
			}
		}
		return toReturn;
	}

	/**
	* "checkIfWon" method checks if all the foundations are full. Public access.
	*@return this method returns true the foundations are full and false if not.
	*/

	public boolean checkIfWon(){
		for (int i = 0 ; i < 4 ; i++)
			if(!(foundations[i].foundationFull())) return false;
		return true;
	}

	/**
	* "loadGame" method loads a game state with the data from a file with the name filename (parameter). Public access.
	*@param filename (parameter) of type String is the name of the file to be accessed where the data for the game state will be gathered.
	*@return this method returns true if the game state was succcessfully loaded, false if not. (null == filename or filename is not found)
	*@throws java.lang.Exception it throws mainly FileNotFoundException
	*/

	public boolean loadGame(String filename) throws Exception{
        int i = 0;
        int j = 0;
        int k = 0;
        String getter = "";

    	try{
    		BufferedReader update = new BufferedReader(new FileReader( filename + ".txt"));
	    	String suitGetter = null;
	    	Suit suit = null;
	    	boolean status = false;
	    	String stat = null;
			int cardNum = 0;
	    	//Clearing the cards.
	    	//Foundations
	    	for (i = 0 ; i < 4 ; i++)
	    		foundations[i].clear();
	    	//Tableau
	    	for (i = 0 ; i < 7 ; i++)
	    		tableau[i].clear();
	    	//Talon
	    	talon.clear();
	    	//Stock
	    	stock.clear();

	    	//Filling up the game
	    	for (i = 0 ; i < 4 ; i++){
	    		getter = update.readLine();	
	        	while(!(getter.equals("--"))){
	        		switch(i){
		        		case 0 : deck[cardNum].setValues(Integer.parseInt(getter),Suit.DIAMONDS,true);
		        				 break;
		        		case 1 : deck[cardNum].setValues(Integer.parseInt(getter),Suit.HEARTS,true);
		        				 break; 
		        		case 2 : deck[cardNum].setValues(Integer.parseInt(getter),Suit.SPADES,true);
		        				 break; 
		        		case 3 : deck[cardNum].setValues(Integer.parseInt(getter),Suit.CLUBS,true);
		        				 break;  
	        		}
	        		foundations[i].push((Object) deck[cardNum]);
	        		cardNum++;
	    			getter = update.readLine();
	        	}
	    	}
	    	//Tableau
	    	for (i = 0 ; i < 7 ; i++){
	    		getter = update.readLine();	
	        	while(!(getter.equals("--"))){
	    			//System.out.println("tableau " + i + ": " + getter);
	        		suitGetter = update.readLine();
	        		stat = update.readLine();
	        		if(stat.equals("1")) status = true;
	        		else status = false;
	        		switch(suitGetter.charAt(0)){
		        		case 'D' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.DIAMONDS,status);
		        				 	break;
		        		case 'H' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.HEARTS,status);
		        					break; 
		        		case 'S' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.SPADES,status);
		        					break; 
		        		case 'C' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.CLUBS,status);
		        					break;  
	        		}
	        		tableau[i].push((Object) deck[cardNum]);
	        		cardNum++;
	    			getter = update.readLine();
	        	}
	    	}
	    	//Talon
	    	getter = update.readLine();
	    	while(!(getter.equals("--"))){
	    		suitGetter = update.readLine();
	    		switch(suitGetter.charAt(0)){
	        		case 'D' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.DIAMONDS,true);
	        				 break;
	        		case 'H' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.HEARTS,true);
	        				 break; 
	        		case 'S' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.SPADES,true);
	        				 break; 
	        		case 'C' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.CLUBS,true);
	        				 break;  
	    		}
	    		talon.push((Object) deck[cardNum]);
	    		cardNum++;
	    		getter = update.readLine();
	    	}	
	    	//Stock
	    	getter = update.readLine();
	    	while(!(getter.equals("--"))){
	    		suitGetter = update.readLine();
	    		switch(suitGetter.charAt(0)){
	        		case 'D' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.DIAMONDS,false);
	        				 break;
	        		case 'H' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.HEARTS,false);
	        				 break; 
	        		case 'S' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.SPADES,false);
	        				 break; 
	        		case 'C' : deck[cardNum].setValues(Integer.parseInt(getter),Suit.CLUBS,false);
	        				 break;  
	    		}
	    		stock.push((Object) deck[cardNum]);
	    		cardNum++;
	    		getter = update.readLine();
	    	}	
			return true;
    	}catch(Exception e){
    		return false;
    	}
	}

	/**
	* "saveGame" method saves the current game state into a file with the name filename (parameter) . Public access.
	*@param filename (parameter) of type String is the filename of the text file where the game states will be saved.
	*@param moveStockCards (parameter) of type LinkedStack is a linked list of cards of the talon pile from main that is needed for saving.
	*@param noStock (parameter) of type integer is the number that indicates the number of cards in the stock pile currently, used only in main. Needed for saving.
	*@param noTalon (parameter) of type integer is the number that indicates the number of cards in the talon pile currently, used only in main, Needed for saving.
	*@return this method returns true if the game state was successfully saved and false if not. (the filename is new and there are 5 slots already).
	*@throws java.lang.Exception it throws mainly FileNotFoundException
	*/

	public boolean saveGame(String filename, LinkedStack moveStockCards, int noStock, int noTalon) throws Exception{ //False kapag yung filename is hindi found sa 5 filenames
        int i = 0;
        int j = 0;
        int k = 0;
        String getter = "";
  	    PrintWriter save = new PrintWriter(filename + ".txt");

    	//Foundation
    	for (i = 0 ; i < 4 ; i++){
    		j = 0;
    		while(foundations[i].iTableau(j) != null){
    			save.print(foundations[i].iTableau(j).getRank() + "\n");
    			j++;
    		}
    		save.print("--\n");
    	}
    	//Tableau
    	for (i = 0 ; i < 7 ; i++){
    		j = 0 ;
    		while(tableau[i].iTableau(j) != null){
    			save.print(tableau[i].iTableau(j).getRank() + "\n");
    			if(tableau[i].iTableau(j).getSuit() == Suit.DIAMONDS) save.print("D\n");
    			else if(tableau[i].iTableau(j).getSuit() == Suit.HEARTS) save.print("H\n");
    			else if(tableau[i].iTableau(j).getSuit() == Suit.SPADES) save.print("S\n");
    			else save.print("C\n");
    			if(tableau[i].iTableau(j).isUp()) save.print("1\n");
    			else save.print("0\n");
    			j++;
    		}
    		save.print("--\n");
    	}
    	//Talon
    	j = 0;
    	while(talon.iTableau(j) != null){
    		save.print(talon.iTableau(j).getRank() + "\n");
			if(talon.iTableau(j).getSuit() == Suit.DIAMONDS) save.print("D\n");
			else if(talon.iTableau(j).getSuit() == Suit.HEARTS) save.print("H\n");
			else if(talon.iTableau(j).getSuit() == Suit.SPADES) save.print("S\n");
			else save.print("C\n");
    		j++;
    	}
    	save.print("--\n");
    	//Stock
    	j = 0;
    	while(stock.iTableau(j) != null){
    		save.print(stock.iTableau(j).getRank() + "\n");
			if(stock.iTableau(j).getSuit() == Suit.DIAMONDS) save.print("D\n");
			else if(stock.iTableau(j).getSuit() == Suit.HEARTS) save.print("H\n");
			else if(stock.iTableau(j).getSuit() == Suit.SPADES) save.print("S\n");
			else save.print("C\n"); 
    		j++; 
    	}
    	save.print("--\n==\n");      	
		for (i = 0 ; moveStockCards.iTableau(i) != null ; i++){
			save.print(moveStockCards.iTableau(i).getRank() + "\n");
			if(moveStockCards.iTableau(i).getSuit() == Suit.DIAMONDS) save.print("D\n");
			else if(moveStockCards.iTableau(i).getSuit() == Suit.HEARTS) save.print("H\n");
			else if(moveStockCards.iTableau(i).getSuit() == Suit.SPADES) save.print("S\n");
			else save.print("C\n"); 
		}
		save.print("--\n");
		save.print(noStock + "\n" + noTalon + "\n==\n");
		save.close();
		return true;
	}

	/**
	* "getCard" method gets the BufferedImage card of the Card object in the corresponding index and stack.
	*@param stack of int type tells what stack is to be accessed, 0 - null, 1 - stock, 2 - talon, 3 - foundations, 4 - tableau.
	*@param index of int type tells what index in stack is to be accesed. In cases of accesing in foundation and tableau stack, increments of 100 is for the representation of a specific pile in the stacks and increments of 1 for the card object itself.
	*@return a bufferedimage is returned that corresponds to the card object accessed.
	*/

	public BufferedImage getCard(int stack, int index){
		switch(stack){
			case 0 : return back;
			case 1 : return (stock.isEmpty() ? null : back);
			case 2 : return (talon.iTableau(talon.getSize()-1-index) != null ? (talon.iTableau(talon.getSize()-1-index).isUp() ? talon.iTableau(talon.getSize()-1-index).getImage() : back) : null);
			case 3 : return (foundations[index/100].iTableau(index%100) != null ? (foundations[index/100].iTableau(index%100).isUp() ? foundations[index/100].iTableau(index%100).getImage() : back) : null);
			case 4 : return (tableau[index/100].iTableau(index%100) != null ? (tableau[index/100].iTableau(index%100).isUp() ? tableau[index/100].iTableau(index%100).getImage() : back) : null);
		}
		return null;
	}
}