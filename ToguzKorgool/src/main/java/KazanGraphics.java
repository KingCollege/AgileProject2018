/*
  This is a JPanel subclass that dynamically changes Graphics
  depending on the number of korgools a player has captured

  Author: Mandu, David, Marta, Tao
  Version: 11.2018
*/
import javax.swing.*;
import java.awt.*;

public class KazanGraphics extends JPanel {
  private int korgools;
  private int korgoolSize;
  private Color c;
  public KazanGraphics(int korgools){
      this.korgools = korgools;
      korgoolSize = 20;
      c = Color.black;
  }

  //Default constructor
  public KazanGraphics(){
    this(0);
  }

  //Set korgools captured
  public void setKorgools(int nKorgools){
      korgools = nKorgools;
  }

  //Return the number of korgools captured
  public int getKorgools(){
      return korgools;
  }

  //Change color
  public void setKorgoolColor(Color c){
    this.c = c;
  }

  //Java Swing overriden method that paints this JPanel
  @Override
  public void paintComponent(Graphics g){
      super.paintComponent(g);
      g.setColor(c);
      double koorgoolsPerRow =  getParent().getWidth() / korgoolSize;
      double rows = korgools / koorgoolsPerRow;
      double currentKorgools =0;
      rows = Math.ceil(rows);
      for(int y =0; y < rows; y++){
        for(int x = 0; x < koorgoolsPerRow; x++){
          if(currentKorgools < korgools){
            g.fillOval(x * korgoolSize , (y + 1)*korgoolSize, korgoolSize, korgoolSize);
            currentKorgools++;
          }
        }//
      }//
  }

}//end of KazanGraphics class
