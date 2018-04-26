package GAME;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.net.*;
/**
* This "Card" class acts as the object that contains the card's suit, rank and its condition (whether it is faced up or not). Additionally it also has the image attribute of BufferedImage which will be used in the GUI mode.
*/
public class Card{

	/**
	* "suit" attribute of type Suit (an enum that restricts its value with only Diamonds, Hearts, Spades and Clubs) that holds the card's suit. Private access.
	*/

	private Suit suit;
	
	/**
	* "rank" attribute of type Integer holds the rank of the card. Private access.
	*/

	private int rank = 0; 
	
	/**
	* "faceUp" attribute of type boolean holds the condition of the card, true if the card must be faced up or false if not. Private access.
	*/

	private boolean faceUp = false;

	/**
	* "image" attribute of type BufferedImage holds the corresponding image of the card that is going to used in GUI mode.
	*/

	private BufferedImage image;

	/**
	* This constructor sets the values of this particular object with the passed parameters and also value for "image" attribute .
	*@param suit (paramater) of enum Suit is the value of this particular object's suit attribute.
	*@param rank (parameter) of type integer is the value of this particular object's rank attribute.
	*/

	public Card(Suit suit, int rank){
		this.suit = suit;
		this.rank = rank;
		int temp = 0;
		if(suit == Suit.DIAMONDS) temp = 100;
		else if(suit == Suit.HEARTS) temp = 126;
		else if(suit == Suit.SPADES) temp = 139;
		else temp = 113;
		image = getBimage("/cmagedeck/"+String.valueOf(temp+rank)+".png");
	}

	/**
	*	This method gets the indicatedd Buffered Image by the path.
	*@param path of String type is the relative path of the image.
	*@return the BufferedImaage of the the image indicated by the path.   
	*/	

	public BufferedImage getBimage(String path){
		try {
        	URL url = this.getClass().getResource(path);
        	return ImageIO.read(url);
      	} catch (Exception e) {
        	e.printStackTrace();
        	return null;
		}
	}

	/**
	* This constructor is called when instantiating the Card class.
	*/

	public Card(){

	}

	/**
	* "setValues" method sets the three attributes of this class namely - rank, suit and faceUp, with the passed parameter. Additionally it sets the corresponding value of image attribute with respecto to the card's other attributes
	*@param rank (parameter) of type integer is the value of this particular object's rank attribute.
	*@param suit (parameter) of enum Suit is the value of this particular object's suit attribute.
	*@param faceUp (parameter) of type boolean is the value of this particular object's faceUp attribute.
	*/

	public void setValues(int rank, Suit suit, boolean faceUp){
		this.rank = rank;
		this.suit = suit;
		this.faceUp = faceUp;
		int temp = 0;
		if(suit == Suit.DIAMONDS) temp = 100;
		else if(suit == Suit.HEARTS) temp = 126;
		else if(suit == Suit.SPADES) temp = 139;
		else temp = 113;
		try{
			image = getBimage("/cmagedeck/"+String.valueOf(temp+rank)+".png");
		} catch(Exception e ){}
	}

	/**
	* "getSuit" method returns the suit of the card. Public access.
	*@return this method returns the suit of the Card of type Suit.
	*/

	public Suit getSuit(){
		return this.suit;
	}

	/**
	* "getRank" method returns the rank of the card. Public access.
	*@return this method returns the rank of the Card of type Integer.
	*/

	public int getRank(){
		return this.rank;
	}

	/**
	* "isUp" method returns the condition of the card if it is faced up or not. Public access
	*@return this method returns the faceUp attributes' value of type boolean. 
	*/

	public boolean isUp(){
		return this.faceUp;
	}

	/**
	* "setFaceUp" method sets the faceUp attribute of this Card class to the value of "up" attribute. Public access
	*@param up (parameter) of type boolean sets the faceUp attribute of this Card class to it.
	*/

	public void setFaceUp(boolean up){
		this.faceUp = up;
		return;
	}

	/**
	* "getImage" method gets the image attribute of the card which will be used in GUI interface.
	*@return this method returns the image attribute of type BufferedImage
	*/
	public BufferedImage getImage(){
		return this.image;
	}

	/**
	* "toString" method returns the rank and the suit depending on the faceUp condition. Public access.
	*@return this method returns the rank and suit inside in a "[   ]" as a string depending on the faceUp condition.
	*/

	public String toString(){
		String stringg = "";
		if(suit == Suit.DIAMONDS) stringg = "D";
		else if(suit == Suit.HEARTS) stringg = "H";
		else if(suit == Suit.SPADES) stringg = "S";
		else stringg = "C";
		if(faceUp == false) return "[===]";
		else{
			if(rank > 10){
				if (rank == 11) return "[J " + stringg + "]";
				else if (rank == 12) return "[Q " + stringg + "]";
				else return "[K " + stringg + "]"; 
			}
			else if (rank == 10) return "[10" + stringg + "]";
			else return "[" + rank + " " + stringg + "]";
		}
	}	
}