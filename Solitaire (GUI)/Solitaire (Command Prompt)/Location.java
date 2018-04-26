package GAME;

/**
*	This "Location" class is used for locating the coordinates of and region bounded by the card/s in the enviroonment. 
*/

public class Location{
	/**
	*	x of type integer holds the x-coordinate of the starting point of the card in the environment.
	*/
	private int x = 0;
	/**
	*	y of type integer holds the y-coordinate of the starting point of the card in the environment.
	*/
	private int y = 0;
	/**
	* 	boundX of type integer holds the x-coordinate of the end point of the card in the environment.
	*/
	private int boundX = 0;
	/**
	* 	boundY of type integer holds the y-coordinate of the end point of the card in the environment.
	*/
	private int boundY = 0;

	/**
	* This costructor sets location of and the region bounded by the card.
	*@param x of type integer holds the x-coordinate of the starting point of the card in the environment.
	*@param y of type integer holds the y-coordinate of the starting point of the card in the environment.
	*@param boundX of type integer holds the x-coordinate of the end point of the card in the environment.
	*@param boundY of type integer holds the y-coordinate of the end point of the card in the environment.
	*/
	public Location(int x, int y, int boundX, int boundY){
		this.x = x;
		this.y = y;
		this.boundX = boundX;
		this.boundY = boundY; 
	}
	/**
	* 	This constructor is used in instantiating the Location class without setting values for the start and end point of the card.
	*/
	public Location(){

	}
	/**
	*	This setter method sets the value of the x attribute of this class to the passed integer. 
	*@param x is the integer that will set the value of the x attribute of this class.
	*/
	public void setX(int x){
		this.x = x;
	}
	/**
	*	This setter method sets the value of the y attribute of this class to the passed integer. 
	*@param y is the integer that will set the value of the y attribute of this class.
	*/
	public void setY(int y){
		this.y = y;
	}
	/**
	*	This setter method sets the value of the boundX attribute of this class to the passed integer. 
	*@param x is the integer that will set the value of the boundX attribute of this class.
	*/
	public void setBoundX(int x){
		this.boundX = x;
	}
	/**
	*	This setter method sets the value of the boundY attribute of this class to the passed integer. 
	*@param y is the integer that will set the value of the boundY attribute of this class.
	*/
	public void setBoundY(int y){
		this.boundY = y;
	}
	/**
	*	This setter method sets the value of x and y attribute of this class to the passed integers. 
	*@param x is the integer that will set the value of the x attribute of this class.
	*@param y is the integer that will set the value of the y attribute of this class.
	*/
	public void setLocation(int x,int y){
		this.x = x;
		this.y = y;
	}

	/**
	*	This setter method sets the value of the bound X and boundY attribute of this class to the passed integers. 
	*@param x is the integer that will set the value of the boundX attribute of this class.
	*@param y is the integer that will set the value of the boundY attribute of this class.
	*/
	public void setBound(int x, int y){
		this.boundX = x;
		this.boundY = y;
	}


	/**
	*	This setter method sets the value of the x, y, boundX and boundY attribute of this class to the passed integers.
	*@param x is the integer that will set the value of the x attribute of this class.
	*@param y is the integer that will set the value of the y attribute of this class.
	*@param boundX is the integer that will set the value of the boundX attribute of this class.
	*@param boundY is the integer that will set the value of the boundY attribute of this class. 
	*/

	public void setRegion(int x, int y, int boundX, int boundY){
		this.x = x;
		this.y = y;
		this.boundX = boundX;
		this.boundY = boundY; 
	}

	/**
	*	This getter method passes the value of the x attribute of this class. 
	*@return  the passed integer returns the current value of the x attribute of this class.
	*/

	public int getX(){
		return this.x;
	}
	/**
	*	This getter method passes the value of the y attribute of this class. 
	*@return  the passed integer returns the current value of the y attribute of this class.
	*/

	public int getY(){
		return this.y;
	}
	/**
	*	This getter method passes the value of the boundX attribute of this class. 
	*@return  the passed integer returns the current value of the boundX attribute of this class.
	*/

	public int getBoundX(){
		return this.boundX;
	}
	/**
	*	This getter method passes the value of the boundY attribute of this class. 
	*@return  the passed integer returns the current value of the boundY attribute of this class.
	*/

	public int getBoundY(){
		return this.boundY;
	}
	/**
	*	isInside method check if the passed location object's starting point is inside the region bounded by this location object.
	*@param a is the location object to be checked if it is inside the region bounded by thie location object.
	*@return  true if the passed Location object is inside the region bounded by this Location object.
	*/
	public boolean isInside(Location a){
		if(boundX >= a.getX() && this.x <= a.getX() && boundY >= a.getY() && this.y <= a.getY()) return true;
		else return false;
	}

	/**
	*	toString method returns "X is (x attribute) and Y is (y attribute)";
	*@return return the string "X is (x attribute) and Y is (y attribute)";
	*/
	public String toString(){
		return "X is " + this.x + " and Y is " + this.y;
	}
}