package GAME;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

public class CanvasManager extends Thread {

   private int FRAME_DELAY = 0;

   private Canvas canvas;
   private long start;

   private BufferedImage background;
   private EnvironmentBuilder environment;

   public CanvasManager(Canvas canvas, EnvironmentBuilder environment) {
      this.canvas = canvas;
      this.environment = environment;
   }

   public void run() {
      start = System.currentTimeMillis();

      canvas.createBufferStrategy(2);
      BufferStrategy strategy = canvas.getBufferStrategy();
      Graphics g = null;
      // loop forever
      while (true) {
         g = strategy.getDrawGraphics();
         paint(g);
         strategy.show();//SHOW BACK BUFFER
         syncFramerate();
      }
   }

   private void paint(Graphics g) {
      /**************************************************************/
      // THIS IS FOR THE BACK BUFFER OF THE CANVAS
      // YOU CAN PUT HERE EVERYTHING YOU NEED TO DISPLAY IN THE CANVAS

      environment.build();
      g.drawImage(environment.getEnvironment(),0,0,700,600,null);
      g.dispose();
   }

   private void syncFramerate() {
      start = start + FRAME_DELAY;
      long difference = start - System.currentTimeMillis();
      try {
         Thread.sleep(Math.max(0, difference));
      }
      catch(InterruptedException e) {
         e.printStackTrace();
      }
   }
}
