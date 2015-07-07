/*
 * AbstractPanel.java
 * TCSS 305 - Spring 2013
 */

package view;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Board;

/** 
 * Abstract panel class that holds a reference to the tetris board.
 * @author Erica Putsche
 * @version 06/07/2013
 */
@SuppressWarnings("serial")
public abstract class AbstractPanel extends JPanel implements Observer {

  
  /** The board. */
  private Board my_board;
  
 /** 
  * A constructor for abstract panel. Meant to be used by children.
  * @param the_board The board.
  */
  protected AbstractPanel(final Board the_board) {
    super(); 
    my_board = the_board;
    this.setOpaque(false);
    this.setBorder(BorderFactory.createLineBorder(Color.black));
  }
  
  /** 
   * Gets the board.
   * @return The board.
   */
  public Board getBoard() {
    return my_board;
  }
  
  /**
   * Sets the board.
   * @param the_board The board.
   */
  public void setBoard(final Board the_board) {
    my_board = the_board;
  }
  
  @Override
  public abstract void update(final Observable the_arg0, final Object the_arg1);
  
}
