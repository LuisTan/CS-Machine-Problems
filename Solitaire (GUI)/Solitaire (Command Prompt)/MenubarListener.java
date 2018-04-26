package GAME;

import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import javax.imageio.*;
import javax.swing.event.*;
import java.io.*;

/**
* This class is one responsible for capturing inputs from the keyboard/mouse and doing the corresponding actions needed upon the performance of the action.
*/
public class MenubarListener implements ActionListener{
  	/**
  	* game attribute of type Solitaire holds the data and the game itself.
  	*/
	private Solitaire game;
  	/**
  	* listener attribute of type CanvasListener  is the one who will do the corresponding actions based on the mouse inputs.
  	*/
	private CanvasListener listener;
  	/**
  	* environment attribute of type EnvironmentBuilder is the one who will build the current state of the game.
  	*/
	private EnvironmentBuilder environment;

	/**
	* This constructor is needed for instantiating the class MenubarListener into an object and also sets the game oof Solitaire type, listener of CanvasListener type, and environment of EnvironmentBuilder type with the passed parameter.
	*@param game of Solitaire type sets the value for the game attribute of this class.
	*@param listener of CanvasListener type sets the value for the listener attribute of this class.
	*@param environment of EnvironmentBuilder type sets the value for the environment attribute of this class.
	*/

	public MenubarListener(Solitaire game, CanvasListener listener, EnvironmentBuilder environment){
		this.game = game;
		this.listener = listener;
		this.environment = environment;
	}
	/**
	* This method will do the necessary process for the corresponding actions in the menubar such as New Game, Save Game etc.
	*@param e of ActionEvent type will tell what action is done.
	*/
 
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand() == "Exit"){
			System.exit(0);
		}
		else if (e.getActionCommand() == "New Game"){
			game.deal();
			listener.dealCall();
			JOptionPane.showMessageDialog(null,"The cards has been redealed.");
		}
		else if (e.getActionCommand() == "Save Game"){
			try{
				String input = JOptionPane.showInputDialog("In what filename will the game state be saved?\n", "");
				if(input == null) return;
				if(game.saveGame(input,listener.getTalonCards(),listener.getNoStock(),listener.getNoTalon())) JOptionPane.showMessageDialog(null,"Saved successfully.");
				else JOptionPane.showMessageDialog(null,"Sorry, the filename is not found in the save slots.");
		    }catch(Exception ex){};
		}
		else if (e.getActionCommand() == "Load Game"){
			try{
				String getter = "";
				String fName = JOptionPane.showInputDialog("What game state will be loaded", "");
				if(fName == null) return; 
				
				if(game.loadGame(fName)){

		        	BufferedReader update = new BufferedReader(new FileReader(fName + ".txt"));
		        	String suitGetter = "";
		        	getter = update.readLine();
		        	LinkedStack toPush = new LinkedStack();

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
		        		toPush.push((Object) tmp);
		        		getter = update.readLine();
		        	}
		        	listener.setTalonCards(toPush);
		        	listener.setNoStock(Integer.parseInt(update.readLine()));//**
		        	listener.setNoTalon(Integer.parseInt(update.readLine()));//**
		        	JOptionPane.showMessageDialog(null,"Loaded Succesfully");
		        	update.close();
		        	environment.loadCall();
		        }
		        else JOptionPane.showMessageDialog(null,"Sorry, the filename was not found in the savefiles folder.");
			} catch(Exception ex){}
		}
		else if(e.getActionCommand() == "About"){
			JOptionPane.showMessageDialog(null,"Solitaire version 1.0\nCreated by : Luis Carlos Tan\nMachine Problem passed to Prof. Kristofer Delas Penas");
		}
		else return;
	}
}