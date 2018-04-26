package GAME;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.*;

/**
*  This "EnvironmentBuilder" class is responsible for painting the current status of the environment of the game.
*/

public class EnvironmentBuilder{

   /**
   *  cWidth of int type is the width of the card
   */
	
   private int cWidth = 75; 

   /**
   *  cHeigth of int type is the height of the card
   */
   private int cHeight = 90;

   /**
   *  talon of Location type indicates the location and region of the talon pile.
   */
   private Location talon;

   /**
   *  foundation of Location[] type indicates the location and region of the foundation pile.
   */
   private Location[] foundation;

   /**
   *  tableau of Location type indicates the location and region of the tableau pile.
   */
   private Location[] tableau;

   /**
   *  formerTab of Location[] type indicates the location and region of the movable cards in the Tableau pile.
   */
   private Location[] formerTab;
   /**
   *  stock of Location type indicates the locationn and region of the stock pile.
   */
   private Location stock;

   /**
   *  game of Solitaire type indicates game itself.
   */

   private Solitaire game;

   /**
   *  toggleArray of int[] type indicates if the talon card/foundation/tableau card or cards.
   */

   private int[] toggleArray;

   /**
   *  background of BufferedImage type holds background image.
   */

   private BufferedImage background;

   /**
   *  background of BufferedImage type holds background image if you won the game.
   */
   private BufferedImage backgroundWin;

   /**
   *  background of BufferedImage type holds environment image.
   */

   private BufferedImage environment;
   /**
   *  enter of BufferedImage type holds the image when the mouse is inside the program.
   */
   private BufferedImage enter;
   /**
   *  exit of BufferedImage type holds the image when the mouse is outside the program.
   */
   private BufferedImage exit;
   /**
   *  move of BufferedImage type holds the image when the mouse is moving inside the program.
   */
   private BufferedImage move;
   /**
   * current of BufferedImage type holds the image of the current action of the mouse. 
   */
   private BufferedImage current;

   /**
   *  x1 attribute of type integer just holds the x-coordinate of the of an image in the background section if you win. 
   */

   private int x1 = 0;

   /**
   *  x2 attribute of type integer just holds the x-coordinate of the of an image in the background section if you win. 
   */  
   private int x2 = 600;

   /**
   *  y1 attribute of type integer just holds the y-coordinate of the of an image in the background section if you win. 
   */
   private int y1 = 0;

   /**
   *  y2 attribute of type integer just holds the y-coordinate of the of an image in the background section if you win. 
   */
   private int y2 = 500;
   /**
   *  back attribute of type bolean just holds the state of the of an image movement in the background section if you win,. 
   */
   private boolean back = false; 

   /**
   *  g2d attribute of type Graphics2D is needed for drawing in the environment attribute of the class. 
   */
   private Graphics2D g2d;

   /**
   * This constructor is needed for instantiating this class and sets the corresponding attributes of this class with values passed in it
   *@param game of type Solitaire holds the data and the current state of the game.
   *@param stock of type Location holds the current location of the stock pile in gui.
   *@param talon of type Location holds the current location of the cards in the talon pile in gui.
   *@param foundation of type Location[] holds the current location of the foundation piles in gui.
   *@param tableau of type Location[] holds the current location of the open cards in the tableau piles.
   *@param formerTab of type Location[] holds the location of the movablee/moving card/cards in the tableau piles.
   *@param toggleArray of type int[] holds the current condition of the cards, which will tell what's on top and is moving.
   */

	public EnvironmentBuilder(Solitaire game, Location stock, Location talon, Location[] foundation, Location[] tableau, Location[] formerTab, int[] toggleArray){
		this.game = game;
		this.toggleArray = toggleArray;
		this.stock = stock;
		this.talon = talon;
		this.foundation = foundation;
		this.tableau = tableau;
		this.formerTab = formerTab;

		try{
			background = getBimage("/background/bg.jpg");
         backgroundWin = getBimage("/background/bgwin.jpg");
         enter = getBimage("/mouseMovement/enter.jpg");
         exit = getBimage("/mouseMovement/exit.jpg");
         move = getBimage("/mouseMovement/move.jpg");
         current = enter;
	  	} catch (Exception e){}
	  	environment = new BufferedImage(700,600,BufferedImage.TYPE_INT_RGB);
		g2d = environment.createGraphics();
	}

   /**
   * This method gets the buffered image specified by the path given.
   *@param path of String type is the path of the image.
   *@return this returns the buffered image specified by the path given.
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
   *loadCall method only happens if a game state is loaded and the environment will adjust to the game. 
   */
	public void loadCall(){
      int i = 0;
      int j = 0;
      for( i = 0 ; i < 7 ; i++){
         j = 0;
         while(game.getCard(4,(i*100)+j) == game.getCard(0,0)){
            j++;
         }
         tableau[i].setY(175+(j*15));
         formerTab[i].setY(175+(j*15));

         while(game.getCard(4,(i*100)+j+1) != null){
            j++;
         }
         tableau[i].setBoundY(175+(j*15)+90-(game.getCard(4,(i*100)+j) == null?15:0));
      }
	}

   /**
   * buildifWon method paints the on the enviroment attribute the graphics of the game when the game is finished.
   *@param ifwon dictates if the game is finished.
   *@return it returns true if the game is finished and false if not.
   */ 

   public boolean buildifWon(boolean ifwon){
      if(!ifwon) return false;
      g2d.drawImage(backgroundWin,100,100,500,400,null);
      if(y1 != 500 && y1 != 0 && !back) g2d.drawImage(backgroundWin,600,y1+=2,100,100,null);
      else if (y1 != 500 && y1 != 0 && back) g2d.drawImage(backgroundWin,600,y1-=2,100,100,null);
      else if (y1 == 500){
         back = true;
         y1-=2;
      }
      else if (y1 == 0){
         back = false;
         y1+=2;
      }
      if(back) g2d.drawImage(backgroundWin,0,y2+=2,100,100,null);
      else g2d.drawImage(backgroundWin,0,y2-=2,100,100,null);

      if(!back) g2d.drawImage(backgroundWin,x1+=2,0,100,100,null);
      else g2d.drawImage(backgroundWin,x1-=2,0,100,100,null);

      if(back) g2d.drawImage(backgroundWin,x2+=2,500,100,100,null);
      else g2d.drawImage(backgroundWin,x2-=2,500,100,100,null);
      return true;
   }

   /**
   * "build" method just paints on the environment attribute of the class the current status of the game.
   */

	public void build(){
		int i = 0;
		int j = 0;
		int toggleCounter = 0;
     	g2d.fillRect(0, 0, 700, 600);
      g2d.setColor(Color.BLACK);

		g2d.drawImage(background, 0,0,null);
		if(buildifWon(game.checkIfWon())) return;
      for(i = 0 ; i < 10; i++){
         g2d.drawRect(25-i,525-i,50+(1*i)+i,50+(1*i)+i);
      }
      g2d.drawImage(current,25,525,50,50,null);
      g2d.drawString("LCBT",85,575);

        for(int loop = 1 ; loop <= 2 ; loop++){
            //Stock [CHECK]
            for (i = 0 ; i < 5 ; i++){
               g2d.drawRect(stock.getX()+i,stock.getY()+i,cWidth-i-1,cHeight-i-1);
            }
            g2d.drawImage(game.getCard(1,0), stock.getX(), stock.getY(), cWidth,cHeight, null);

            //Talon laging naka FACEUP except kapag null] 
            toggleCounter = 0;
            if((toggleArray[toggleCounter] == 1 && loop == 2) || (toggleArray[toggleCounter] == 0 && loop == 1)){
               for(i = 0 ; i < 5 ; i++){
                  g2d.drawRect(125+i,50+i,cWidth+30-i-1,cHeight-i-1);
               }
               for (i = 2 ; i >= 0; i--){
                  g2d.drawImage(game.getCard(2,i), (i != 0 ? 125+((2-i)*15) : talon.getX()), (i != 0 ? 50 : talon.getY()), cWidth,cHeight, null);
               }
               //System.out.println(toggleArray[toggleCounter]);
            }
            toggleCounter++;
            //Foundation [Di kailangan ng FACEUP
            //if((toggleArray[toggleCounter] != 0 && loop == 2) || (toggleArray[toggleCounter] == 0 && loop == 1)){
            if(loop == 1){
               for (i = 0 ; i < 4; i++){
                  if(i == toggleArray[toggleCounter]) continue;
                  for (j = 0 ; j < 5 ; j++){
                     g2d.drawRect(290+(i*90)+j, 50+j,cWidth-j,cHeight-j);
                  }
                  j = 0;
                  while(game.getCard(3,(i*100)+j+1) != null){
                     g2d.drawImage(game.getCard(3,(i*100)+j), 290+(i*90),50, cWidth,cHeight, null);
                     j++;
                  }
                  g2d.drawImage(game.getCard(3,(i*100)+j), foundation[i].getX(), foundation[i].getY(), cWidth,cHeight, null);
               }
            }
            else if(loop == 2  && toggleArray[toggleCounter] != 4){
               i = toggleArray[toggleCounter];
               for (j = 0 ; j < 5 ; j++){
                  g2d.drawRect(290+(i*90)+j, 50+j,cWidth-j,cHeight-j);
               }
               j = 0;
               while(game.getCard(3,(i*100)+j+1) != null){
                  g2d.drawImage(game.getCard(3,(i*100)+j), 290+(i*90),50, cWidth,cHeight, null);
                  j++;
               }
               g2d.drawImage(game.getCard(3,(i*100)+j), foundation[i].getX(), foundation[i].getY(), cWidth,cHeight, null);
            } 

            toggleCounter++;
            //Tableau. [Multiple of 100 yung index]
            //if((toggleArray[toggleCounter] && loop == 2) || (!(toggleArray[toggleCounter]) && loop == 1)){
            if(loop == 1){
               for( i = 0 ; i < 7 ; i++){
                  if(i == toggleArray[toggleCounter]) continue;
                  for( int k = 0 ; k < 3; k++){
                     g2d.drawRect(20+(i*90)+k,175+k,cWidth-k,cHeight-k);
                  }
                  j = 0;
                  while( 175+(j*15) < formerTab[i].getY() && game.getCard(4,(i*100)+j) != null){
                     g2d.drawImage(game.getCard(4,(i*100)+j), 20+(i*90), 175+(j*15), cWidth,cHeight, null);
                     j++;
                  }
                  int k = 0;
                  while(game.getCard(4,(i*100)+j) != null){
                     g2d.drawImage(game.getCard(4,(i*100)+j), tableau[i].getX(), tableau[i].getY() + (k*15), cWidth,cHeight, null);
                     j++;
                     k++;
                  }
               }
            }
            else if(loop == 2 && toggleArray[toggleCounter] != 7){
               //System.out.println("||||||||||||||||||" + toggleArray[toggleCounter]);
               i = toggleArray[toggleCounter];
               for( int k = 0 ; k < 3; k++){
                  g2d.drawRect(20+(i*90)+k,175+k,cWidth-k,cHeight-k);
               }
               j = 0;
               while( 175+(j*15) < formerTab[i].getY() && game.getCard(4,(i*100)+j) != null){
                  g2d.drawImage(game.getCard(4,(i*100)+j), 20+(i*90), 175+(j*15), cWidth,cHeight, null);
                  j++;
               }
               int k = 0;
               while(game.getCard(4,(i*100)+j) != null){
                  g2d.drawImage(game.getCard(4,(i*100)+j), tableau[i].getX(), tableau[i].getY() + (k*15), cWidth,cHeight, null);
                  j++;
                  k++;
               }
            }
        }
   	}

      /**
      *this method gets the current status of the environment of the game.
      *@return the BufferedImage of the current status of the environment.
      */ 

   	public BufferedImage getEnvironment(){
   		return this.environment;
   	}
      /**
      *
      */
      public void mouseExit(){
         current = exit;
      }
      /**
      *
      */
      public void mouseEnter(){
         current = enter;
      }
      /**
      *
      */
      public void mouseMove(){
         current = move;
      }
}