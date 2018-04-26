package GAME;

import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
* MyFrame class will hold the main GUI interface of the game.
*/

public class MyFrame extends JFrame {
  /**
  * this canvas attribute of type Canvas css is the canvas that will listen to mouse inputs. 
  */
  Canvas canvas;
  /**
  * thismanager attribute of type CanvasManager is the one responsible for printing  the current state.
  */
  CanvasManager manager;
  /**
  * listener attribute of type CanvasListener  is the one who will do the corresponding actions based on the mouse inputs.
  */
  CanvasListener listener;
  /**
  * environment attribute of type EnvironmentBuilder is the one who will build the current state of the game.
  */
  EnvironmentBuilder environment;
  /**
  * game attribute of type Solitaire holds the data and the game itself.
  */
  Solitaire game;

   /**
   *  talon of Location type indicates the location and region of the talon pile.
   */
  Location talon;
   /**
   *  foundation of Location[] type indicates the location and region of the foundation pile.
   */
  Location[] foundation;
   /**
   *  tableau of Location type indicates the location and region of the tableau pile.
   */
  Location[] tableau;
   /**
   *  formerTab of Location[] type indicates the location and region of the movable cards in the Tableau pile.
   */
  Location[] formerTab;
   /**
   *  stock of Location type indicates the locationn and region of the stock pile.
   */
  Location stock;
   /**
   *  toggleArray of int[] type indicates if the talon card/foundation/tableau card or cards.
   */
  int[] toggleArray;
  /**
  * menubar attribute of type JMenuBar is the menubar itself andd the one who will hold the menu and menu items
  */
  JMenuBar menubar;
  /**
  * menu attribute of type JMenu is the menu for New Game, Save Game and Load Game options.
  */
  JMenu menu;
  /**
  * options attribute of type JMenu is the menu for about and exit options.
  */
  JMenu options;
  /**
  * newGame  of type JMenuItem is the menu item for the new game option.
  */
  JMenuItem newGame;
  /**
  * saveGame  of type JMenuItem is the menu item for the save game option.
  */
  JMenuItem saveGame;
  /**
  * loadGame  of type JMenuItem is the menu item for the load game option.
  */
  JMenuItem loadGame;
  /**
  * about of type JMenuItem is the menu item for about option
  */
  JMenuItem about;
  /**
  * exit  of type JMenuItem is the menu item for the exit option.
  */
  JMenuItem exit;
  /**
  * mlistener  of type MenubarListener is the listener of mouse inputs for the menu items.
  */
  MenubarListener mlistener;

  /**
  * This is constructor is needed for instantiating this class into object which gives corresponding values to the attributes in the class.
  */

  public MyFrame() {
    int i = 0;    
    game = new Solitaire();

    canvas = new Canvas();
    stock = new Location(20,50,95,140);
    talon = new Location(155,50,230,140);
    foundation = new Location[4];
    for(i = 0; i < 4; i++)
      foundation[i] = new Location(290+(i*90),50,290+(i*90)+75,140);
    tableau = new Location[7];
    formerTab = new Location[7];
    for (i = 0; i < 7; i++){
      formerTab[i] = new Location(20+(i*90),175+(i*15),20+(i*90)+75,175+(i*15)+90);
      tableau[i] = new Location(20+(i*90),175+(i*15),20+(i*90)+75,175+(i*15)+90);
    }

    toggleArray = new int[3];
    toggleArray[0] = 0;
    toggleArray[1] = 4;
    toggleArray[2] = 7;

    environment = new EnvironmentBuilder(game,stock,talon,foundation,tableau,formerTab,toggleArray);
    manager = new CanvasManager(canvas,environment);
    listener = new CanvasListener(game,stock,talon,foundation,tableau,formerTab,game.getCard(0,0),toggleArray,environment);
    mlistener = new MenubarListener(game,listener,environment);
    
    menubar = new JMenuBar();
    menubar.setSize(700, 20);
    menu = new JMenu("Menu");

    //New Game
    newGame = new JMenuItem("New Game");
    newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK));
    newGame.addActionListener(mlistener);
    menu.add(newGame);
    //Save Game
    saveGame = new JMenuItem("Save Game");
    saveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK));
    saveGame.addActionListener(mlistener);
    menu.add(saveGame);
    //Load Game
    loadGame = new JMenuItem("Load Game");
    loadGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,Event.CTRL_MASK));
    loadGame.addActionListener(mlistener);
    menu.add(loadGame);

    options = new JMenu("Other options");
    //About
    about = new JMenuItem("About");
    about.addActionListener(mlistener);
    options.add(about);
    //Exit
    exit = new JMenuItem("Exit");
    exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK));
    exit.addActionListener(mlistener);
    options.add(exit);

    menubar.add(menu);
    menubar.add(options);

    this.add(menubar);

    canvas.setSize(700,600);
    canvas.addMouseListener(listener);
    canvas.addMouseMotionListener(listener);
    this.add(canvas);
    this.pack();
    this.setVisible(true);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    manager.start();
  }
}
