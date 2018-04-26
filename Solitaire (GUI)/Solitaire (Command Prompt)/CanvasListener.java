package GAME; 

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

/**
*	CanvasListener is the one responsible for listening mouse/keyboard inputs and for the one who will do the corresponding actions.
*/

public class CanvasListener implements MouseInputListener{
  	/**
  	* game attribute of type Solitaire holds the data and the game itself.
  	*/
	private Solitaire game;
   	/**
   	*  talon of Location type indicates the location and region of the talon pile.
   	*/
	private Location talon;
   	/**
   	*  foundation of Location[] type indicates the location and region of the foundation pile.
   	*/
	private Location[] foundation;
   	/**
   	*  formerTab of Location[] type indicates the location and region of the movable cards in the Tableau pile.
   	*/
	private Location[] tableau;//Needs to be saved and updated;
   	/**
   	*  stock of Location type indicates the locationn and region of the stock pile.
   	*/
	private Location stock;
   /**
   *  formerTab of Location[] type indicates the location and region of the movable cards in the Tableau pile.
   */
	private Location[] formerTab;//Needs to be saved and updated;
	/**
	* point of Location type indicates the location of the cursor when clicked, pressed, dragged or released.
	*/
	private Location point;
	/**
	* card of Location type indicates the former location of the card to be moved.
	*/
	private Location card;
	/**
	* talonCards of LinkedStack type holds the card/s in the talon. 
	*/
	private LinkedStack talonCards;
	/**
	* noTalon of integer type has the current number of cards in talon .
	*/ 
	private static int noTalon = 0;// Number of talon cards
	/**
	* noStock of integer type has the current number of cards in stock .
	*/ 
	private static int noStock = 0;// Number of stock cards
	/**
	* whatStack of integer type indicates from what stack is the moving card came from (1 - Talon, 2 - Foundation, 4 - Tableau).
	*/ 
	private static int whatStack = 0;//1 - talon , 2 - Foundation , 3 - Tableau
	/**
	* diffX of integer type holds the difference in x-axis between the card from the cursor (for dragging purposes).
	*/ 
	private static int diffX = 0;//Dragging purposes
	/**
	* diffY of integer type holds the difference in y-axis between the card from the cursor (for dragging purposes).
	*/ 
	private static int diffY = 0;//Dragging purposes
	/**
	* index of integer type holds the index number oof tableau or foundation from which the card/s came from (for pressed and released purposes).
	*/ 
	private static int index = 0;//Index of tableau or foundation
	/**
	* noCard of integer type holds number of cards that are selected.
	*/ 
	private static int noCard = 0;//Number of cards moving for tableau
	/**
	* back of BufferedImage type holds the image of a back of card.
	*/ 
	private BufferedImage back;
	/**
	* fromStart of boolean type states if the card/s taken are the next stacks of cards after the last closed card in a pile.
	*/ 
	private static boolean fromStart = false;
	/**
	* toggleArray of int[] type holds the index number of the pile where the moving card came from. First index = either 0 or 1 (0 as no pile) , Second index = 0 - 4 (4 as no pile) and Third index = 0 - 7 (7 as no pile)
	*/
	private int[] toggleArray;
	/**
	* environment of EnvironmentBuilder type holds the object where the environment is located.
	*/
	private EnvironmentBuilder environment; 
	/**
	* This constructor is needed for instantiating this class into an object, sets some attribute with the passed parameter, and creates the necessary objects.
   *@param game of type Solitaire holds the data and the current state of the game.
   *@param stock of type Location holds the current location of the stock pile in gui.
   *@param talon of type Location holds the current location of the cards in the talon pile in gui.
   *@param foundation of type Location[] holds the current location of the foundation piles in gui.
   *@param tableau of type Location[] holds the current location of the open cards in the tableau piles.
   *@param formerTab of type Location[] holds the location of the movablee/moving card/cards in the tableau piles.
   *@param back of type BufferedImage  holds the object with the image of the back of card.
   *@param toggleArray of type int[] holds the current condition of the cards, which will tell what's on top and is moving.
   *@param environment of EnvironmentBuilder type holds the object where the environment is located.
	*/
	public CanvasListener(Solitaire game, Location stock, Location talon, Location[] foundation, Location[] tableau, Location[] formerTab, BufferedImage back, int[] toggleArray,EnvironmentBuilder environment){
		this.game = game;
		this.back = back;
		this.toggleArray = toggleArray;
    	this.game.deal();
    	this.noStock = 24;
    	this.noTalon = 0;
    	this.talonCards = new LinkedStack();
    	this.stock = stock;
    	this.talon = talon;
    	this.formerTab = formerTab;
      	this.foundation = foundation;
      	this.tableau = tableau;
		point = new Location();
		card = new Location();
		this.environment = environment;

	}
	/**
	* Thi constructor is needed for instantiating this class as an object.
	*/
	public CanvasListener(){
		
	}
	/**
	*	This method will do the necessary actions when deal is called.
	*/
	public void dealCall(){
		int i = 0;
	    noTalon = 0;
	    noStock = 24;
	    talonCards.clear();
	    for (i = 0; i < 7; i++){
	      formerTab[i].setRegion(20+(i*90),175+(i*15),20+(i*90)+75,175+(i*15)+90);
	      tableau[i].setRegion(20+(i*90),175+(i*15),20+(i*90)+75,175+(i*15)+90);
	    }
	    toggleArray[0] = 0;
	    toggleArray[1] = 4;
	    toggleArray[2] = 7;
	}	
	/**
	* This getter method will return the current number of cards in talon.
	*@return the value of noTalon.
	*/
	public int getNoTalon(){
		return this.noTalon;
	}
	/**
	* This getter method will return the current number of cards in stock pile.
	*@return the value of noStock.
	*/
	public int getNoStock(){
		return this.noStock;
	}
	/**
	* This getter method will return the current LinkedStack of cards in talon.
	*@return the object talonCards.
	*/
	public LinkedStack getTalonCards(){
		return this.talonCards;
	}
	/**
	* This setter method sets the value of noTalon attribute to the passed parameter.
	*@param noTalon will set the noTalon attribute's value
	*/
	public void setNoTalon(int noTalon){
		this.noTalon = noTalon;
	}
	/**
	* This setter method sets the value of noStock attribute to the passed parameter.
	*@param noStock will set the noStock attribute's value
	*/
	public void setNoStock(int noStock){
		this.noStock = noStock;
	}
	/**
	* This setter method sets the value of talonCards attribute to the passed parameter.
	*@param talonCards will set the noTalon attribute's value.
	*/
	public void setTalonCards(LinkedStack talonCards){
		this.talonCards.clear();
		this.talonCards = talonCards;
	}
	/**
	* This method is the one responsible for doing necessary action with the necessary mouse enter inputs.
	*/
	public void mouseEntered(MouseEvent e) {
		environment.mouseEnter();
	}
	/**
	* This method is the one responsible for doing necessary action with the necessary mouse exit inputs.
	*@param e of MouseEvent type is the action performed through mouse.
	*/
	public void mouseExited(MouseEvent e) {
		environment.mouseExit();
	}
	/**
	* This method is the one responsible for doing necessary action with the necessary mouse click inputs.
	*@param e of MouseEvent type is the action performed through mouse.
	*/
	public void mouseClicked(MouseEvent e) {
		point.setLocation(e.getX(),e.getY());
		int i = 0;

		//DRAWING CARDS
		if(stock.isInside(point)){
			if(noStock == 0 ){
				noStock = noTalon;
				noTalon = 0;
				talonCards.clear();
			}
			for (i = 0 ; i < 3 ;i++){
				if(noStock == 0) break;
				talonCards.push(game.draw());
				noStock--;
				noTalon++;
			}
		}//MOVING CARD TO FOUNDATION
		else if (e.getClickCount() == 2){
			for(i = 0 ; i < 7 ; i++){
				//TABLEAU to FOUNDATION
				if(tableau[i].isInside(point)){		
					/*****************
					PUT CAPTION FOR INPUT VALI0DATION
					*****************/
					if (game.moveToFoundation((Card) game.getFromTableau(i+1,1).peek())){
						tableau[i].setLocation(card.getX(),card.getY()-(card.getY() != 175 && fromStart ? 15 : 0));
						tableau[i].setBoundY(tableau[i].getBoundY()-15);
						formerTab[i].setY(tableau[i].getY());
					}
					fromStart = false;
					toggleArray[2] = 7;
					return;
				}
			}
			//TALON to FOUNDATION
			if(talon.isInside(point)){
				if(talonCards.getSize() == 0) {
					toggleArray[0] = 0;
					return;
				}
				/*****************
				PUT CAPTION FOR INPUT VALIDATION
				*****************/
				if(game.moveToFoundation((Card) talonCards.peek()) && talonCards.peek() != null){
					noTalon--;
					talonCards.pop();
				}
				toggleArray[0] = 0;
			}
			else{
				toggleArray[0] = 0;
				return;
			}
		}
		else return;
	}
	/**
	* This method is the one responsible for doing necessary action with the necessary mouse press inputs.
	*@param e of MouseEvent type is the action performed through mouse.
	*/
	public void mousePressed(MouseEvent e) {
		point.setLocation(e.getX(),e.getY());
		int i = 0;
		int j = 0;

		//For dragging purposes
		//Talon
		if(talon.isInside(point)){
			if(talonCards.getSize() == 0) return;
			card.setLocation(talon.getX(),talon.getY());
			whatStack = 1;
			diffX = e.getX() - talon.getX();
			diffY = e.getY() - talon.getY();
			toggleArray[0] = 1;
		}
		else{
			//Foundation
			for(i = 0 ; i < 4 ; i++){
				if(foundation[i].isInside(point)){
					switch(i){
						case 0: if(game.getFromFoundation(Suit.DIAMONDS) == null) return;
								break;
						case 1: if(game.getFromFoundation(Suit.HEARTS) == null) return;
								break;
						case 2: if(game.getFromFoundation(Suit.SPADES) == null) return;
								break;
						case 3: if(game.getFromFoundation(Suit.CLUBS) == null) return;
								break;
					}
					whatStack = 2;
					index = i;
					card.setLocation(foundation[i].getX(), foundation[i].getY());
					diffX = e.getX() - foundation[i].getX();
					diffY = e.getY() - foundation[i].getY();
					toggleArray[1] = index;
					return;
				}
			}
			//Tableau
			fromStart = false;

			for(i = 0 ; i < 7 ; i++){
				if(tableau[i].isInside(point)) {
					if(game.getFromTableau(i+1,1) == null) return;
					card.setLocation(tableau[i].getX(),tableau[i].getY()); //If successful - 15 sa y kapag hindi hindi
					whatStack = 3;
					index = i;
					j = 0;
					while(175+(j*15) != tableau[i].getBoundY()-90){
						if(175+(j*15) <= point.getY() && 175+((j+1)*15) > point.getY()) break;
						j++;
					}
					if(game.getCard(4,(i*100)+j-1) == back || game.getCard(4,(i*100)+j-1) == null) fromStart = true;
					//Set yung stack na gagalaw
					for(int k = j ; 175+(k*15) <= tableau[i].getBoundY()-90; k++)
						noCard++;
					tableau[i].setY(175+(j*15));
					formerTab[i].setY(175+(j*15));
					diffX = e.getX() - tableau[i].getX();
					diffY = e.getY() - tableau[i].getY();//what if stack ang nailagay sa foundation
					toggleArray[2] = index;
					return;
				}
			}
		}
		return;
	}
	/**
	* This method is the one responsible for doing necessary action with the necessary mouse release inputs.
	*@param e of MouseEvent type is the action performed through mouse.
	*/
	public void mouseReleased(MouseEvent e) {
		point.setLocation(e.getX(),e.getY());
		toggleArray[0] = 0;
		toggleArray[1] = 4;
		toggleArray[2] = 7;
		int i = 0;
		if(whatStack == 1){
			whatStack = 0;
			//Talon to Foundation
			for(i = 0 ; i < 4 ; i++){
				if(foundation[i].isInside(point)){
					if(game.moveToFoundation((Card) talonCards.peek())  && talonCards.peek() != null) {
						talonCards.pop();
						noTalon--;
						talon.setLocation(card.getX(),card.getY());
						toggleArray[0] = 0;
						return;
					}
				}
			}
			//Talon to Tableau
			for(i = 0 ; i < 7 ; i++){
				//System.out.println(i + " - " + tableau[i] + " up to X is " + tableau[i].getBoundX() + " and Y is" +tableau[i].getBoundY());
				if(tableau[i].isInside(point)){
					if(game.moveToTableau((Card) talonCards.peek(),i+1)) {
						talonCards.pop();
						noTalon--;
						talon.setLocation(card.getX(),card.getY());
						tableau[i].setBoundY(tableau[i].getBoundY()+15);
						toggleArray[0] = 0;
						return;
					}
				}
			}
			toggleArray[0] = 0;
			talon.setLocation(card.getX(),card.getY());
			return;
		}
		else if(whatStack == 2){
			whatStack = 0;
			foundation[index].setLocation(card.getX(),card.getY());
			//Foundation to Tableau
			for(i = 0 ; i < 7 ; i++){
				if(tableau[i].isInside(point)){
					switch(index){
						case 0 : if(game.moveToTableau(game.getFromFoundation(Suit.DIAMONDS),i+1)) tableau[i].setBoundY(tableau[i].getBoundY()+15);
								 break;
						case 1 : if(game.moveToTableau(game.getFromFoundation(Suit.HEARTS),i+1)) tableau[i].setBoundY(tableau[i].getBoundY()+15);
								 break;
						case 2 : if(game.moveToTableau(game.getFromFoundation(Suit.SPADES),i+1)) tableau[i].setBoundY(tableau[i].getBoundY()+15);
								 break;
						case 3 : if(game.moveToTableau(game.getFromFoundation(Suit.CLUBS),i+1)) tableau[i].setBoundY(tableau[i].getBoundY()+15);
								 break;
					}
					//tableau[i].setBoundY(tableau[i].getBoundY()+15);
				}
			}
			toggleArray[1] = 4;
			index = 0;
			return;

		}
		else if(whatStack == 3){
			whatStack = 0;
			//Tableau to foundation [-15 na yung no stack kapag successful]
			for(i = 0 ; i < 4 ; i++){
				if(foundation[i].isInside(point)){
					if(noCard != 1){
						tableau[index].setLocation(card.getX(),card.getY());
						toggleArray[2] = 7;
						index = 0;
						noCard = 0;
						return;
					}
					if (game.moveToFoundation((Card) game.getFromTableau(index+1,1).peek())){
						tableau[index].setLocation(card.getX(),card.getY()-(fromStart && card.getY() != 175 ?15:0));
						formerTab[index].setLocation(card.getX(),card.getY()-(fromStart && card.getY() != 175?15:0));
						tableau[index].setBoundY(tableau[index].getBoundY()-(card.getY() != 175?15:0));
						fromStart = false;
						toggleArray[2] = 7;
						noCard = 0;
						return;
					}
				}
			}
			//Tableau to Tableau
			for(i = 0 ; i < 7 ; i++){
				//System.out.println("Tableau : " + tableau[i].getX() + " and " + tableau[i].getY() + "\n" + "Point : " + point.getX() + " and " + point.getY());
				///System.out.println(tableau[i]);
				if(tableau[i].isInside(point)){
					if(index == i) break;
					//System.out.println("Successful");
					if(game.checkMoveToTableau((Card)game.getFromTableau(index+1,noCard).peek(),i)){
						if(game.moveToTableau((Card)game.getFromTableau(index+1,noCard).peek(),i+1)){
							tableau[index].setLocation(card.getX(),card.getY()-(fromStart && card.getY() != 175 ? 15:0));
							formerTab[index].setY(card.getY()-(fromStart && card.getY() != 175 ?15:0));
							tableau[index].setBoundY(tableau[index].getBoundY()-(15*noCard));	
							tableau[i].setBoundY(tableau[i].getBoundY()+(noCard*15));
							fromStart = false;
							noCard = 0;
							toggleArray[2] = 7;
							return;
						}
					}
				}
			}
			//formerTab[index].setY(card.getY());
			//fromStart = false;
			noCard = 0;
			toggleArray[2] = 7;
			formerTab[index].setY(card.getY());
			tableau[index].setLocation(card.getX(),card.getY());
			return;
		}
		else{
			return;
		} 
	}
	/**
	* This method is the one responsible for doing necessary action with the necessary mouse drag inputs.
	*@param e of MouseEvent type is the action performed through mouse.
	*/
	public void mouseDragged(MouseEvent e){
		//Talon
		if(whatStack == 1){
			talon.setLocation(e.getX()-diffX,e.getY()-diffY);
			toggleArray[1] = 4;
			toggleArray[2] = 7;
		}
		else if (whatStack == 2){
			foundation[index].setLocation(e.getX()-diffX,e.getY()-diffY);
			toggleArray[0] = 0;
		}
		else if (whatStack == 3){
			tableau[index].setLocation(e.getX()-diffX,e.getY()-diffY);
			toggleArray[0] = 0;
			toggleArray[1] = 4;
		}
		else return;
	}
	/**
	* This method is the one responsible for doing necessary action with the necessary mouse move inputs.
	*@param e of MouseEvent type is the action performed through mouse.
	*/
	public void mouseMoved(MouseEvent e){
		environment.mouseMove();
	}
}